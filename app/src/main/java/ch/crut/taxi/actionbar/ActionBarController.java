package ch.crut.taxi.actionbar;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ch.crut.taxi.R;
import ch.crut.taxi.interfaces.ActionBarActions;
import ch.crut.taxi.interfaces.ActionBarClickListener;

public class ActionBarController implements ActionBarActions {

    public void setActionBarClickListener(ActionBarClickListener actionBarClickListener) {
        this.actionBarClickListener = actionBarClickListener;
    }

    private ActionBarClickListener actionBarClickListener;

    private TextView title;
    private Button leftButton;
    private Button rightButton;

    public ActionBarController(UIActionBar uiActionBar) {
        View view = uiActionBar.getActionBar().getCustomView();

        title = (TextView) view.findViewById(R.id.actionBarUITitle);
        leftButton = (Button) view.findViewById(R.id.actionBarUILeftButton);
        rightButton = (Button) view.findViewById(R.id.actionBarUIRightButton);
    }

    @Override
    public void settingEnabled() {
        rightButton.setBackgroundResource(R.drawable.icon_settings);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionBarClickListener != null) {
                    actionBarClickListener.clickSettings(view);
                }
            }
        });
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void hideTitle() {

    }

    @Override
    public void cancelEnabled() {

    }

    @Override
    public void backEnabled() {
        rightButton.setBackgroundResource(R.drawable.ic_launcher);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionBarClickListener != null) {
                    actionBarClickListener.clickBack(view);
                }
            }
        });
    }

    @Override
    public void doneEnabled() {

    }

    @Override
    public void hideLeft() {
        leftButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideRight() {
        rightButton.setVisibility(View.INVISIBLE);
    }

}
