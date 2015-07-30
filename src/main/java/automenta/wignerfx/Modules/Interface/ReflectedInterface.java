package automenta.wignerfx.Modules.Interface;

import automenta.wignerfx.Modules.Interface.Annotations.Io;
import com.gs.collections.impl.list.mutable.FastList;

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

    public List<Connector> getConnectors(List<Connector.EnumDirection> connectorCompatibilityList) {
        List<Connector> resultList = new FastList<>();

        Class classOfEncapsulatedObject = encapsulatedObject.getClass();

        for( final Method iterationMethod : classOfEncapsulatedObject.getDeclaredMethods() ) {
            Annotation ioAnnotation = iterationMethod.getAnnotation(Io.class);

            if(!(ioAnnotation instanceof Io)){
                continue;
            }
            Io annotation = (Io) ioAnnotation;
            System.out.println("name: " + annotation.name());
            System.out.println("value: " + annotation.slot());
            System.out.println("value: " + annotation.directions());

            if( !directionsContainsCheckForAll(annotation.directions(), connectorCompatibilityList)) {
                continue;
            }

            resultList.add(new Connector(annotation.directions(), annotation.name(), annotation.slot()));
        }

        return resultList;
    }

    private static boolean directionsContainsCheckForAll(Connector.EnumDirection[] array, List<Connector.EnumDirection> checkFor) {
        for( final Connector.EnumDirection iterationElement : array ) {
            if( !checkFor.contains(iterationElement) ) {
                return false;
            }
        }

        return true;
    }
}
