package com.skufler.sacity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A fragment representing a single Sensor detail screen.
 * This fragment is either contained in a {@link SensorListActivity}
 * in two-pane mode (on tablets) or a {@link SensorDetailActivity}
 * on handsets.
 */
public class SensorDetailFragment extends Fragment {
    private Bundle extras;

    public SensorDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        if (activity != null) {
            extras = activity.getIntent().getExtras();


            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);

            if (appBarLayout != null) {
                assert extras != null;
                appBarLayout.setTitle(extras.getString("ITEM_NAME"));
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sensor_detail, container, false);

        ((TextView) rootView.findViewById(R.id.sensor_detail)).setText(extras.getString("ITEM_DATA"));

        return rootView;
    }
}
