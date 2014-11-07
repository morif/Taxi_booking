package ch.crut.taxi.actionbar;


import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import ch.crut.taxi.R;

public class UIActionBar {

    public ActionBar getActionBar() {
        return actionBar;
    }

    private final ActionBar actionBar;

    public UIActionBar(ActionBar actionBar) {
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
        actionBar.setCustomView(R.layout.action_bar_ui);
    }

}
