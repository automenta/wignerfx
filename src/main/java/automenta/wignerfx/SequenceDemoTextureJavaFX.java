package automenta.wignerfx;

import automenta.wignerfx.Modules.Interface.Connector;
import automenta.wignerfx.Modules.Interface.ReflectedInterface;
import automenta.wignerfx.Modules.Library.Library;
import automenta.wignerfx.Modules.Library.Module;
import automenta.wignerfx.Modules.Standard.DummyNeuron;
import automenta.wignerfx.RenderingInteraction.StatefulModule;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;

public class SequenceDemoTextureJavaFX extends Application {
    private static final int SCENE_W = 800;
    private static final int SCENE_H = 600;

    private static final Color INDIA_INK = Color.rgb(35, 39, 50);

    @Override
    public void start(Stage stage) {
        EventDispatcher eventDispatcher = new EventDispatcher();

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
                //box,
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


        // here for testing
        Library library = new Library();

        DummyNeuron dummyNeuron = new DummyNeuron();

        ReflectedInterface reflectedInterface = new ReflectedInterface(dummyNeuron);

        Module moduleForDummyNeuron = new Module();
        moduleForDummyNeuron.inputCompatibleConnectors = reflectedInterface.getConnectors(Arrays.asList(Connector.EnumDirection.IN));
        moduleForDummyNeuron.outputCompatibleConnectors = reflectedInterface.getConnectors(Arrays.asList(Connector.EnumDirection.OUT));

        library.modules.put(DummyNeuron.class, moduleForDummyNeuron);



        StatefulModule statefulModule = StatefulModule.createAfterModule(eventDispatcher, moduleForDummyNeuron, new double[]{0.0, 0.0});

        group.getChildren().add(statefulModule.group);


        statefulModule = StatefulModule.createAfterModule(eventDispatcher, moduleForDummyNeuron, new double[]{500.0, 0.0});

        group.getChildren().add(statefulModule.group);


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

        eventDispatcher.setupHandlers(scene, camera);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}