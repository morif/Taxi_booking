package ch.crut.taxi.utils.actionbar;


import android.view.View;

import java.util.HashMap;
import java.util.Map;

public abstract class NBAbstractController {

    protected Map<Integer, View> NBViews = new HashMap<>();

    public abstract void set(int[] items);

    public abstract void hide();

    public abstract void prepare();

    public abstract void hide(Integer key);

    public abstract void hide(int[] items);

    public abstract void show(int item);

}
