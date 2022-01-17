package API;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UseAsTestRailId {
    int testRailId() default 0;
    String[] tags() default "";
}
