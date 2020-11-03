package ObjModelAnalysis.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface OneToMany {
    String name() default "";
}
