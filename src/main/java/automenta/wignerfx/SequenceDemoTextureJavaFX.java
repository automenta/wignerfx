package automenta.wignerfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.stage.Stage;

import java.util.ArrayDeque;

// Display a rotating 3D box with a video projected onto its surface.
public class SequenceDemoTextureJavaFX extends Application {

    int vectorSize = 16;
    int inputs, outputs;

    final int dataWindow = 200;
    int dataWidth;
    double[][] data = null;
    double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;


    public final ArrayDeque<Runnable> queue = new ArrayDeque<>(dataWindow * 2);
    private WritableImage textureImage;


    public void cycle() {

        try {
            if (queue.isEmpty())
            {

            }
            else {
                Runnable r = queue.pollFirst();
                r.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    int margin = 1;

    public void commit() {
        PixelWriter wr = textureImage.getPixelWriter();

        int m = margin;
        for (int i = 0; i < dataWindow; i++) {
            for (int j = 0; j < dataWidth; j++) {
                double v = data[j][i];
                Color c = Color.hsb(90 * v, 0.85f, 0.85f * v);
                //Color c = Color.gray(v);
                wr.setColor(i + m, j + m, c);

            }
        }


    }

    @Override
    public void init() {



        inputs = 29;
        outputs = 20;

        dataWidth = inputs + 1 + outputs + 1 + outputs;

        data = new double[dataWidth][dataWindow];


        textureImage = new WritableImage(dataWindow + margin * 2, dataWidth + margin * 2);

        textureImage.getPixelWriter().setColor(0, 0, Color.BLUE);
        textureImage.getPixelWriter().setColor(1, 1, Color.BLUE);
        textureImage.getPixelWriter().setColor(2, 2, Color.BLUE);
        textureImage.getPixelWriter().setColor(3, 3, Color.BLUE);
        textureImage.getPixelWriter().setColor(4, 4, Color.BLUE);

    }



    public void pushLast(double[] p, int index) {

        for (double x : p) {
            double[] d = data[index++];
            System.arraycopy(d, 0, d, 1, d.length-1);
            d[0] = x;
        }

    }


    private static final int SCENE_W = 800;
    private static final int SCENE_H = 600;


    private static final double MEDIA_W = 540 * 2 / 3;
    private static final double MEDIA_H = 209 * 2 / 3;

    private static final Color INDIA_INK = Color.rgb(35, 39, 50);

    @Override
    public void start(Stage stage) {

        init();

        float scale = 16f;
        // create a 3D box shape on which to project the video.
        Shape3D box = new Box(dataWindow * scale, dataWidth * scale, 1);
        box.setTranslateX(SCENE_W / 2);
        box.setTranslateY(SCENE_H / 2);

        // project the video on to the 3D box.
        PhongMaterial material = new PhongMaterial();
        box.setMaterial(material);
        //box.setCullFace(CullFace.BACK);
        material.setDiffuseMap(textureImage);


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                cycle();

            }
        };
        timer.start();


        // create a point light source a fair way away so lighting is reasonably even.
        PointLight pointLight = new PointLight(
                Color.WHITE
        );
        pointLight.setTranslateX(SCENE_W / 2);
        pointLight.setTranslateY(SCENE_H / 2);
        pointLight.setTranslateZ(-SCENE_W * 5);

        // add a bit of ambient light to make the lighting more natural.
        AmbientLight ambientLight = new AmbientLight(
                Color.rgb(15, 15, 15)
        );

        // place the shape and associated lights in a group.
        Group group = new Group(
                box,
                pointLight,
                ambientLight
        );

        // create a 3D scene with a default perspective camera.
        Scene scene = new Scene(
                group,
                SCENE_W, SCENE_H, true, SceneAntialiasing.BALANCED
        );
        scene.setFill(INDIA_INK);
        PerspectiveCamera camera = new PerspectiveCamera();

        scene.setCamera(camera);

        stage.setScene(scene);

        // start playing the media, showing the scene once the media is ready to play.
        //player.setOnReady(stage::show);
        //player.setOnError(Platform::exit);
        //player.play();


        //First person shooter keyboard movement
        scene.setOnKeyPressed(event -> {
            double change = 10.0;
            //Add shift modifier to simulate "Running Speed"
            if (event.isShiftDown()) {
                change = 50.0;
            }
            //What key did the user press?
            KeyCode keycode = event.getCode();
            //Step 2c: Add Zoom controls
            if (keycode == KeyCode.W) {
                camera.setTranslateZ(camera.getTranslateZ() + change);
            }
            if (keycode == KeyCode.S) {
                camera.setTranslateZ(camera.getTranslateZ() - change);
            }
            //Step 2d:  Add Strafe controls
            if (keycode == KeyCode.A) {
                camera.setTranslateX(camera.getTranslateX() - change);
            }
            if (keycode == KeyCode.D) {
                camera.setTranslateX(camera.getTranslateX() + change);
            }
        });

        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            double modifier = 10.0;
            double modifierFactor = 0.5;

            if (me.isControlDown()) {
                modifier = 0.1;
            }
            if (me.isShiftDown()) {
                modifier = 50.0;
            }
            if (me.isMiddleButtonDown()) {
                double r = camera.getRotate();
                r = (((r + mouseDeltaX * modifierFactor * modifier * 2.0) % 360 + 540) % 360 - 180);  // +
                camera.setRotate(r);
                //cameraTransform.rx.setAngle(((cameraTransform.rx.getAngle() - mouseDeltaY * modifierFactor * modifier * 2.0) % 360 + 540) % 360 - 180);  // -
            } else if (me.isSecondaryButtonDown()) {
                double z = camera.getTranslateZ();
                double newZ = z + mouseDeltaX * modifierFactor * modifier;
                camera.setTranslateZ(newZ);
            } else if (me.isPrimaryButtonDown()) {
                camera.setTranslateX(camera.getTranslateX() + mouseDeltaX * modifierFactor * modifier * -1);
                camera.setTranslateY(camera.getTranslateY() + mouseDeltaY * modifierFactor * modifier * -1);
                //cameraTransform.t.setX(cameraTransform.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
                //cameraTransform.t.setY(cameraTransform.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
            }
        });

        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}