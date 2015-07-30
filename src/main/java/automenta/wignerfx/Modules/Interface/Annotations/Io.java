package automenta.wignerfx.Modules.Interface.Annotations;

import java.lang.annotation.Annotation;

/**
 * Annotation class for input/output and other types for for example a composited handling of Modules.
 *
 * For example if n modules work in a coherent fashion and use other Modules for some specific tasks.
 */
public class Io implements Annotation {
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
