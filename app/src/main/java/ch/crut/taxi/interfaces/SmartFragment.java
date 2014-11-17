package ch.crut.taxi.interfaces;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SmartFragment {

    int[] items() default {};

//    int left() default NBItems.EMPTY;
//
//    int right() default NBItems.EMPTY;

    String title() default "";

}
