package automenta.wignerfx.RenderingInteraction;

import automenta.wignerfx.Modules.Library.Module;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Module as displayed by the GUI
 */
public class StatefulModule {
    public Group group;

    // TODO< construction crap from Library >
    public StatefulModule() {
        Rectangle rect = new Rectangle(0,0,250,250);
        rect.setFill(Color.BLUE);
        //group.getChildren().add(rect);


        // TODO< create visual connectors from connectors >
        Circle c1 = new Circle(0, 0, 5);
        c1.setFill(Color.WHITE);
        //group.getChildren().add(c1);


        Circle c2 = new Circle(0, 20, 5);
        c1.setFill(Color.WHITE);
        //group.getChildren().add(c1);

        group = new Group(rect, c1, c2);
    }

    public static StatefulModule createAfterModule(Module moduleForDummyNeuron) {
        // TODO
        return new StatefulModule();
    }
}
