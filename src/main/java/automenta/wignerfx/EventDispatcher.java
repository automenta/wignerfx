package automenta.wignerfx;

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

/**
 *
 */
public class EventDispatcher {
    private double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;

    private Scene scene;
    private Camera camera;

    private MouseDragedHandler dragedHandler;

    private static class MousePressedHandler implements EventHandler<MouseEvent> {
        private final EventDispatcher eventDispatcher;

        public MousePressedHandler(EventDispatcher eventDispatcher) {
            this.eventDispatcher = eventDispatcher;
        }

        @Override
        public void handle(MouseEvent event) {
            eventDispatcher.mousePosX = event.getSceneX();
            eventDispatcher.mousePosY = event.getSceneY();
            eventDispatcher.mouseOldX = event.getSceneX();
            eventDispatcher.mouseOldY = event.getSceneY();
        }
    }

    private static class MouseDragedHandler implements EventHandler<MouseEvent> {
        private final EventDispatcher eventDispatcher;

        public MouseDragedHandler(EventDispatcher eventDispatcher) {
            this.eventDispatcher = eventDispatcher;
        }

        @Override
        public void handle(MouseEvent event) {
            eventDispatcher.mouseOldX = eventDispatcher.mousePosX;
            eventDispatcher.mouseOldY = eventDispatcher.mousePosY;
            eventDispatcher.mousePosX = event.getSceneX();
            eventDispatcher.mousePosY = event.getSceneY();
            eventDispatcher.mouseDeltaX = (eventDispatcher.mousePosX - eventDispatcher.mouseOldX);
            eventDispatcher.mouseDeltaY = (eventDispatcher.mousePosY - eventDispatcher.mouseOldY);

            double modifier = 10.0;
            double modifierFactor = 0.5;

            if (event.isControlDown()) {
                modifier = 0.1;
            }
            if (event.isShiftDown()) {
                modifier = 50.0;
            }
            if (event.isMiddleButtonDown()) {
                double r = eventDispatcher.camera.getRotate();
                r = (((r + eventDispatcher.mouseDeltaX * modifierFactor * modifier * 2.0) % 360 + 540) % 360 - 180);  // +
                eventDispatcher.camera.setRotate(r);
                //cameraTransform.rx.setAngle(((cameraTransform.rx.getAngle() - mouseDeltaY * modifierFactor * modifier * 2.0) % 360 + 540) % 360 - 180);  // -
            } else if (event.isSecondaryButtonDown()) {
                double z = eventDispatcher.camera.getTranslateZ();
                double newZ = z + eventDispatcher.mouseDeltaX * modifierFactor * modifier;
                eventDispatcher.camera.setTranslateZ(newZ);
            } else if (event.isPrimaryButtonDown()) {
                eventDispatcher.camera.setTranslateX(eventDispatcher.camera.getTranslateX() + eventDispatcher.mouseDeltaX * modifierFactor * modifier * -1);
                eventDispatcher.camera.setTranslateY(eventDispatcher.camera.getTranslateY() + eventDispatcher.mouseDeltaY * modifierFactor * modifier * -1);
                //cameraTransform.t.setX(cameraTransform.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
                //cameraTransform.t.setY(cameraTransform.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
            }
        }
    }

    public void setupHandlers(Scene scene, Camera camera) {
        this.scene = scene;
        this.camera = camera;

        this.dragedHandler = new MouseDragedHandler(this);

        //scene.setOnMouseDragged(dragedHandler);
        enableMouseDrag();
        scene.setOnMousePressed(new MousePressedHandler(this));
    }

    public void disableMouseDrag() {
        scene.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragedHandler);
    }

    public void enableMouseDrag() {
        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragedHandler);
    }
}
