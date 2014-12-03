package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ListView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;

import ch.crut.taxi.adapters.UserOrderHistoryAdapter;
import ch.crut.taxi.utils.UserOrderHistory;


@EFragment(R.layout.fragment_user_order_history)
public class FragmentUserOrderHistory extends Fragment {
    private static final String LOG_TAG = "FragmentUserOrderHistory";
    private ActivityMain activityMain;

    public static FragmentUserOrderHistory newInstance() {
        FragmentUserOrderHistory fragmentUserOrderHistory = new FragmentUserOrderHistory_();
        Bundle bundle = new Bundle();
        fragmentUserOrderHistory.setArguments(bundle);
        return fragmentUserOrderHistory;
    }

    @ViewById(R.id.fragmentUserOrderListView)
    protected ListView orderListView;

    @ViewById(R.id.fragmentUserOrderModeButton)
    protected Button modeButton;

    @Click(R.id.fragmentUserOrderModeButton)
    protected void clickToButton() {


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
    }

    @Override
    public void onStart() {
        super.onStart();

        ArrayList<UserOrderHistory> userOrderHistoriesList = new ArrayList<UserOrderHistory>();
        UserOrderHistory userOrderHistory1 = new UserOrderHistory("24", "uborevicha", "magnitogorska", "4.5", "50");
        UserOrderHistory userOrderHistory2 = new UserOrderHistory("15", "chornobilska", "prospect peremogy", "2.1", "35");
        UserOrderHistory userOrderHistory3 = new UserOrderHistory("24", "uborevicha", "magnitogorska", "4.5", "50");
        UserOrderHistory userOrderHistory4 = new UserOrderHistory("15", "chornobilska", "prospect peremogy", "2.1", "35");
        UserOrderHistory userOrderHistory5 = new UserOrderHistory("24", "uborevicha", "magnitogorska", "4.5", "50");
        UserOrderHistory userOrderHistory6 = new UserOrderHistory("15", "chornobilska", "prospect peremogy", "2.1", "35");
        UserOrderHistory userOrderHistory7 = new UserOrderHistory("24", "uborevicha", "magnitogorska", "4.5", "50");
        UserOrderHistory userOrderHistory8 = new UserOrderHistory("15", "chornobilska", "prospect peremogy", "2.1", "35");
        UserOrderHistory userOrderHistory9 = new UserOrderHistory("24", "uborevicha", "magnitogorska", "4.5", "50");
        UserOrderHistory userOrderHistory10 = new UserOrderHistory("15", "chornobilska", "prospect peremogy", "2.1", "35");
        UserOrderHistory userOrderHistory11 = new UserOrderHistory("24", "uborevicha", "magnitogorska", "4.5", "50");
        UserOrderHistory userOrderHistory12 = new UserOrderHistory("15", "chornobilska", "prospect peremogy", "2.1", "35");
        userOrderHistoriesList.add(userOrderHistory1);
        userOrderHistoriesList.add(userOrderHistory2);
        userOrderHistoriesList.add(userOrderHistory3);
        userOrderHistoriesList.add(userOrderHistory4);
        userOrderHistoriesList.add(userOrderHistory5);
        userOrderHistoriesList.add(userOrderHistory6);
        userOrderHistoriesList.add(userOrderHistory7);
        userOrderHistoriesList.add(userOrderHistory8);
        userOrderHistoriesList.add(userOrderHistory9);
        userOrderHistoriesList.add(userOrderHistory10);
        userOrderHistoriesList.add(userOrderHistory11);
        userOrderHistoriesList.add(userOrderHistory12);



        UserOrderHistoryAdapter userOrderHistoryAdapter = new UserOrderHistoryAdapter(activityMain, userOrderHistoriesList);
        orderListView.setAdapter(userOrderHistoryAdapter);


    }


}
