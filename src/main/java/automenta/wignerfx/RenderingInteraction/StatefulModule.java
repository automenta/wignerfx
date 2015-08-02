package automenta.wignerfx.RenderingInteraction;

import automenta.wignerfx.EventDispatcher;
import automenta.wignerfx.Modules.Interface.Connector;
import automenta.wignerfx.Modules.Library.Module;
import com.gs.collections.impl.list.mutable.FastList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.List;


/**
 * Module as displayed by the GUI
 */
public class StatefulModule {
    public Group group;

    // TODO< construction crap from Library >
    public StatefulModule(EventDispatcher eventDispatcher, int numberOfInputs, int numberOfOutputs, final double[] position) {
        Rectangle rect = new Rectangle(position[0],position[1],250,250);
        rect.setFill(Color.BLUE);

        List<Node> connectorShapes = new FastList<>();

        for( int inputI = 0; inputI < numberOfInputs; inputI++ ) {
            StatefulConnector statefulConnector = new StatefulConnector();
            statefulConnector.direction = Connector.EnumDirection.IN;

            statefulConnector.shape = new Circle(position[0] + 0.0, position[1] + inputI * 30, 5.0);
            statefulConnector.shape.addEventHandler(MouseEvent.MOUSE_RELEASED,
                    new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {
                            int x = 0;
                            System.out.println("released");
                            eventDispatcher.enableMouseDrag();
                        }
                    });

            connectorShapes.add(statefulConnector.shape);

            inputConnectors.add(statefulConnector);
        }

        for( int outputI = 0; outputI < numberOfOutputs; outputI++ ) {
            StatefulConnector statefulConnector = new StatefulConnector();
            statefulConnector.direction = Connector.EnumDirection.OUT;

            statefulConnector.shape = new Circle(position[0] + 250.0, position[1] + outputI * 30, 5.0);
            statefulConnector.shape.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {
                            int x = 0;
                            eventDispatcher.disableMouseDrag();
                        }
                    });

            connectorShapes.add(statefulConnector.shape);

            outputConnectors.add(statefulConnector);
        }

        connectorShapes.add(rect);
        group = new Group(connectorShapes);

    }

    public static StatefulModule createAfterModule(EventDispatcher eventDispatcher, Module moduleForDummyNeuron, double[] position) {
        return new StatefulModule(eventDispatcher, moduleForDummyNeuron.inputCompatibleConnectors.size(), moduleForDummyNeuron.outputCompatibleConnectors.size(), position);
    }

    private List<StatefulConnector> inputConnectors = new FastList<>();
    private List<StatefulConnector> outputConnectors = new FastList<>();
}
