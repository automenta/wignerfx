package automenta.wignerfx.Modules.Interface;

import java.util.List;

/**
 *
 */
public interface IModuleInterface {
    public enum EnumConnectorCompatibility {
        INPUT,
        OUTPUT
    }

    public Connector[] getConnectors(List<EnumConnectorCompatibility> connectorCompatibilityList);
}
