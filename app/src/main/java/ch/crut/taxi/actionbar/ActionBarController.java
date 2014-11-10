package ch.crut.taxi.actionbar;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ch.crut.taxi.R;
import ch.crut.taxi.interfaces.ActionBarClickListener;

public class ActionBarController {

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


    public void settingEnabled(boolean enabled) {
        if (enabled)
            showSettings();
        else
            hideSettings();
    }

    private void hideSettings() {
        rightButton.setText("");
        rightButton.setBackgroundDrawable(null);
        rightButton.setOnClickListener(null);
    }

    private void showSettings() {
        rightButton.setText("");
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


    public void title(String title) {
        this.title.setText(title);
    }


    public void hideTitle() {

    }


    public void cancelEnabled(boolean enable) {
        if (enable) {
            leftButton.setBackgroundResource(R.drawable.selector_action_bar_blue);
            leftButton.setText(R.string.cancel);
            leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (actionBarClickListener != null) {
                        actionBarClickListener.clickCancel(view);
                    }
                }
            });
        } else {
            leftButton.setBackgroundDrawable(null);
            leftButton.setText("");
            leftButton.setOnClickListener(null);
        }
    }


    public void backEnabled() {
        rightButton.setText(R.string.back);
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


    public void doneEnabled(boolean enable) {
        if (enable) {
            rightButton.setBackgroundResource(R.drawable.selector_action_bar_blue);
            rightButton.setText(R.string.done);
            rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (actionBarClickListener != null) {
                        actionBarClickListener.clickDone(view);
                    }
                }
            });
        } else {
            rightButton.setBackgroundDrawable(null);
            rightButton.setText("");
            rightButton.setOnClickListener(null);
        }
    }


    public void hideLeft() {
        leftButton.setVisibility(View.INVISIBLE);
    }


    public void hideRight() {
        rightButton.setVisibility(View.INVISIBLE);
    }

}
