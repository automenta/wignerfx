package automenta.wignerfx.Modules.Interface;

import java.util.List;

/**
 *
 */
public interface IModuleInterface {
    List<Connector> getConnectors(List<Connector.EnumDirection> connectorCompatibilityList);
}
