package actionbar;


import com.example.taxiprojecthelper.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SmartFragment {

    int[] items() default {};

    int title() default R.string.no_text;
}
