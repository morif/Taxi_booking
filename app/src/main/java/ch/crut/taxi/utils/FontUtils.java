package ch.crut.taxi.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;

public class FontUtils {

    private static Map<String, Typeface> TYPEFACE = new HashMap<>();

    public static Typeface getFonts(Context context, String name) {
        Typeface typeface = TYPEFACE.get(name);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/"
                    + name);
            TYPEFACE.put(name, typeface);
        }
        return typeface;
    }
}