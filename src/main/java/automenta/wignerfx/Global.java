package automenta.wignerfx;

import javafx.scene.shape.Rectangle;

/**
 *
 */
public class Global {
    public Rectangle rect = new Rectangle(1.0, 1.0);

    public void x(double[] a, double[] b) {
        double angle = Math.atan2(b[1] - a[1], b[0] - a[0]);

        double diffX = b[0] - a[0];
        double diffY = b[1] - a[1];
        double distance = Math.sqrt(diffX*diffX + diffY*diffY);

        rect.setRotate(angle * (360.0 / (2.0 * Math.PI)));
        rect.setX((a[0] + b[0]) * 0.5);
        rect.setY((a[1] + b[1]) * 0.5);
        rect.setScaleX(distance);
    }
}
