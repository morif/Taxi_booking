package ch.crut.taxi.utils;


import android.view.animation.AnimationUtils;
import android.widget.EditText;

public class TextUtils {

    public static boolean empty(String... strings) {
        for (String string : strings) {
            if (empty(string)) {
                return true;
            }
        }
        return false;
    }

    public static boolean empty(String string) {
        return string.isEmpty();
    }


    public static boolean emptyAnimate(EditText... editTexts) {
        boolean result = false;
        ShakeAnimator animator = new ShakeAnimator();

        for (EditText editText : editTexts) {
            if (empty(editText)) {
                animator.animate(editText);
                result = true;
            }
        }
        return result;
    }

    public static boolean empty(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (empty(editText)) {
                return true;
            }
        }
        return false;
    }

    public static boolean empty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    public static String get(EditText editText) {
        return editText.getText().toString();
    }
}
