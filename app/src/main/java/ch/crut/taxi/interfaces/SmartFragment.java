package ch.crut.taxi.interfaces;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.crut.taxi.R;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SmartFragment {

    int[] items() default {};

    int title() default R.string.no_text;
}
