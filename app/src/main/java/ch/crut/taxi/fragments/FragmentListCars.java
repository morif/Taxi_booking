package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.adapter.ListCarsAdapter;
import ch.crut.taxi.utils.request.Entities;

@EFragment(R.layout.fragment_list_cars)
public class FragmentListCars extends Fragment {
    private static final String LOG_TAG = "FragmentListCars";
    private ActivityMain activityMain;

    public static FragmentListCars newInstance() {
        FragmentListCars fragmentListCars = new FragmentListCars_();
        Bundle bundle = new Bundle();
        fragmentListCars.setArguments(bundle);
        return fragmentListCars;
    }

    @ViewById(R.id.fragmentListCarListView)
    protected ListView listViewCars;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityMain = (ActivityMain) activity;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "333");
        ArrayList<Entities.SearchTaxi> searchTaxis = new ArrayList<Entities.SearchTaxi>();
        for(int i = 0; i < 10; i ++) {
            Entities.SearchTaxi searchTaxi = new Entities.SearchTaxi();
            searchTaxi.distance = "123";
            searchTaxi.modelCar = "audi 6";
            searchTaxis.add(searchTaxi);
        }


        ListCarsAdapter listCarsAdapter = new ListCarsAdapter(activityMain, searchTaxis);
        Log.d(LOG_TAG, "1");
        listViewCars.setAdapter(listCarsAdapter);


    }


}
