package ch.crut.taxi.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {

    public CustomTextView(Context ctx) {
        super(ctx);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        // Explicitly call the this() constructor; shared logic
        // will be added there soon
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}