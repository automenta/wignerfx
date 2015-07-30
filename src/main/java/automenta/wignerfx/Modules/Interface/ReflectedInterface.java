package automenta.wignerfx.Modules.Interface;

import automenta.wignerfx.Modules.Interface.Annotations.Io;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Retrives the connector information from the annotated class
 */
public class ReflectedInterface implements IModuleInterface {
    private final Object encapsulatedObject;

    public ReflectedInterface(Object encapuslatedObject) {
        this.encapsulatedObject = encapuslatedObject;
    }

    public Connector[] getConnectors(List<EnumConnectorCompatibility> connectorCompatibilityList) {
        Class classOfEncapsulatedObject = encapsulatedObject.getClass();

        for( final Method iterationMethod : classOfEncapsulatedObject.getDeclaredMethods() ) {
            Annotation annotation = iterationMethod.getAnnotation(Io.class);

            if(!(annotation instanceof Io)){
                continue;
            }
            Io myAnnotation = (Io) annotation;
            System.out.println("name: " + myAnnotation.name());
            System.out.println("value: " + myAnnotation.value());
        }

        // TODO
        return null;
    }
}
