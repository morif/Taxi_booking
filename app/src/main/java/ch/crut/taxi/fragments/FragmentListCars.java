package ch.crut.taxi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import ch.crut.taxi.ActivityMain;
import ch.crut.taxi.R;
import ch.crut.taxi.adapters.ListCarsAdapter;
import ch.crut.taxi.fragmenthelper.FragmentHelper;
import ch.crut.taxi.interfaces.SmartFragment;
import ch.crut.taxi.utils.actionbar.NBItemSelector;
import ch.crut.taxi.utils.actionbar.NBItems;
import ch.crut.taxi.utils.request.Entities;

@EFragment(R.layout.fragment_list_cars)
@SmartFragment(title = R.string.auto_list, items = {NBItems.BACK})
public class FragmentListCars extends NBFragment implements NBItemSelector {


    private static final String SEARCH_TAXI_ITEMS = "SEARCH_TAXI_ITEMS";

    private ActivityMain activityMain;

    public static FragmentListCars newInstance(ArrayList<Entities.SearchTaxi> items) {
        FragmentListCars fragmentListCars = new FragmentListCars_();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SEARCH_TAXI_ITEMS, items);
        fragmentListCars.setArguments(bundle);
        return fragmentListCars;
    }

    @ViewById(R.id.fragmentListCarListView)
    protected ListView listViewCars;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity, FragmentListCars.class);
        activityMain = (ActivityMain) activity;


    }


    @Override
    public void onStart() {
        super.onStart();

        ArrayList<Entities.SearchTaxi> drivers = (ArrayList<Entities.SearchTaxi>) getArguments().getSerializable(SEARCH_TAXI_ITEMS);
        ListCarsAdapter listCarsAdapter = new ListCarsAdapter(activityMain, drivers);

        listViewCars.setAdapter(listCarsAdapter);
    }

    @Override
    public void NBItemSelected(int id) {
        switch (id) {
            case NBItems.BACK:
                FragmentHelper.pop(getFragmentManager());
                break;
        }
    }
}