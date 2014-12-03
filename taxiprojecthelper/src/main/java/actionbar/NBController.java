package actionbar;


import android.view.View;
import android.widget.TextView;

public class NBController extends NBAbstractController {

    private NBItemSelector NBItemSelector;

    private final UINotificationBar uiNotificationBar;


    public NBController(UINotificationBar uiNotificationBar) {
        this.uiNotificationBar = uiNotificationBar;
        this.prepare();
    }


    @Override
    public void set(int[] items) {
        hide();
        for (Integer key : items) {
            NBViews.get(key).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hide() {
        for (Integer key : NBViews.keySet()) {

            // don't hide title textView
            if (key != NBItems.TITLE) {
                NBViews.get(key).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void prepare() {
        for (Integer key : NBItems.obtain()) {
            NBViews.put(key, uiNotificationBar.getActionBar().getCustomView().findViewById(key));
            NBViews.get(key).setOnClickListener(barItemClick);
        }
    }

    @Override
    public void hide(Integer key) {
        NBViews.get(key).setVisibility(View.INVISIBLE);
    }

    @Override
    public void hide(int[] items) {
        for (Integer key : items) {
            this.hide(key);
        }
    }

    @Override
    public void show(int key) {
        NBViews.get(key).setVisibility(View.VISIBLE);
    }

    public void clickListener(NBItemSelector listener) {
        this.NBItemSelector = listener;
    }

    private View.OnClickListener barItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NBItemSelector.NBItemSelected(view.getId());
        }
    };

    public void title(String title) {
        ((TextView) NBViews.get(NBItems.TITLE)).setText(title);
    }
}
