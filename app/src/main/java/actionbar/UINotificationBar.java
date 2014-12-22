package actionbar;


import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import map.vp.ua.taxiconnectorlib.R;


public class UINotificationBar {

    public ActionBar getActionBar() {
        return actionBar;
    }

    private final ActionBar actionBar;

    public UINotificationBar(ActionBar actionBar) {
        if (actionBar == null) {
            throw new RuntimeException("UIActionBar constructor NuLL");
        }

        this.actionBar = actionBar;

        prepare();
        setView();
    }

    private void prepare() {
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    private void setView() {
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
        actionBar.setCustomView(R.layout.nb_ui);
    }

}