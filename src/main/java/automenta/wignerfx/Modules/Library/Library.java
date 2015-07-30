package automenta.wignerfx.Modules.Library;

import java.util.HashMap;
import java.util.Map;

/**
 * Cashes all module descriptions of a class
 */
public class Library {
    // TODO< is a hashmap good here ? >
    public Map<Class, Module> modules = new HashMap<>();
}
