package automenta.wignerfx.Modules.Interface.Annotations;

import automenta.wignerfx.Modules.Interface.Connector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation class for input/output and other types for for example a composited handling of Modules.
 *
 * For example if n modules work in a coherent fashion and use other Modules for some specific tasks.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface Io {
    String name() default "";
    int slot() default -1;
    Connector.EnumDirection[] directions();
}
