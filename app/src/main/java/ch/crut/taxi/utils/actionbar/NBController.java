package ch.crut.taxi.utils.actionbar;


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

    public void set(NBItemSelector listener) {
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

//    public void setActionBarClickListener(ActionBarClickListener actionBarClickListener) {
//        this.actionBarClickListener = actionBarClickListener;
//    }
//
//    private ActionBarClickListener actionBarClickListener;
//
//    private TextView title;
//    private Button leftButton;
//    private Button rightButton;
//    private LinearLayout.LayoutParams lpSettings;
//    private LinearLayout.LayoutParams lpDefault;
////    private LinearLayout.LayoutParams lpTextButton;
//
//    public NotificationBarController(UINotificationBar uiNotificationBar) {
//        View view = uiNotificationBar.getActionBar().getCustomView();
//
//        title = (TextView) view.findViewById(R.id.actionBarUITitle);
//        leftButton = (Button) view.findViewById(R.id.actionBarUILeftButton);
//        rightButton = (Button) view.findViewById(R.id.actionBarUIRightButton);
//
//        final DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
//
//        final int _35dpInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35,
//                displayMetrics);
//        final int _30dpInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics);
//
//        lpSettings = new LinearLayout.LayoutParams(_35dpInPixels, _35dpInPixels);
//
//        lpDefault = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                _35dpInPixels);
//
////        lpTextButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
////                _30dpInPixels);
//    }
//
//
//    public void settingEnabled(boolean enabled) {
//        if (enabled)
//            showSettings();
//        else
//            hideSettings();
//    }
//
//    private void hideSettings() {
//        rightButton.setText("");
//        rightButton.setBackgroundDrawable(null);
//        rightButton.setOnClickListener(null);
//        rightButton.setLayoutParams(lpDefault);
//    }
//
//    private void showSettings() {
//        rightButton.setText("");
//        rightButton.setBackgroundResource(R.drawable.icon_settings);
//        rightButton.setLayoutParams(lpSettings);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (actionBarClickListener != null) {
//                    actionBarClickListener.clickSettings(view);
//                }
//            }
//        });
//    }
//
//
//
//    public void hideTitle() {
//
//    }
//
//
//    public void cancelEnabled(boolean enable) {
//        if (enable) {
//            leftButton.setBackgroundResource(R.drawable.selector_action_bar_blue);
//            leftButton.setText(R.string.cancel);
////            leftButton.setLayoutParams(lpTextButton);
//            leftButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (actionBarClickListener != null) {
//                        actionBarClickListener.clickCancel(view);
//                    }
//                }
//            });
//        } else {
//            leftButton.setBackgroundDrawable(null);
//            leftButton.setText("");
//            leftButton.setOnClickListener(null);
////            leftButton.setLayoutParams(lpDefault);
//        }
//    }
//
//
//    public void backEnabled() {
//        rightButton.setText(R.string.back);
//        rightButton.setBackgroundResource(R.drawable.ic_launcher);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (actionBarClickListener != null) {
//                    actionBarClickListener.clickBack(view);
//                }
//            }
//        });
//    }
//
//
//    public void doneEnabled(boolean enable) {
//        if (enable) {
//            rightButton.setBackgroundResource(R.drawable.selector_action_bar_blue);
//            rightButton.setText(R.string.done);
//            rightButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (actionBarClickListener != null) {
//                        actionBarClickListener.clickDone(view);
//                    }
//                }
//            });
////            rightButton.setLayoutParams(lpTextButton);
//        } else {
//            rightButton.setBackgroundDrawable(null);
//            rightButton.setText("");
//            rightButton.setOnClickListener(null);
////            rightButton.setLayoutParams(lpDefault);
//        }
//    }
//
//
//    public void hideLeft() {
//        leftButton.setVisibility(View.INVISIBLE);
//    }
//
//
//    public void hideRight() {
//        rightButton.setVisibility(View.INVISIBLE);
//    }
//
//    public void setLeft(int actionBarUtilValue) {
//        switch (actionBarUtilValue) {
//            case ActionBarUtils.ICON_CANCEL:
//                doneEnabled(true);
//                break;
//            case ActionBarUtils.ICON_SETTINGS:
//                showSettings();
//                break;
//        }
//    }
}
