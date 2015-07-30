package automenta.wignerfx.Modules.Interface;

/**
 * Connector of an Module, can act as a Input/Output or some mixed (signaling) connector
 */
public class Connector {
    public enum EnumDirection {
        IN,
        OUT,
        SIGNALING
    }

    public Connector(EnumDirection[] directions, String name, int slot) {
        this.directions = directions;
        this.name = name;
        this.slot = slot;
    }

    public final EnumDirection[] directions;
    public final String name;
    public final int slot;
}
