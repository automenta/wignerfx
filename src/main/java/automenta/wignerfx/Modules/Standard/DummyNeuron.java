package automenta.wignerfx.Modules.Standard;

import automenta.wignerfx.Modules.Interface.Annotations.Io;
import automenta.wignerfx.Modules.Interface.Connector;

/**
 *
 */
public class DummyNeuron {
    @Io(name = "value", directions = Connector.EnumDirection.OUT)
    public float getValue() {
        return 0.0f;
    }
}
