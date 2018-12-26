package com.skufler.sacity;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private SensorListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private ArrayList<SACitySensor> sensors = new ArrayList<>();

    static Handler updateHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeLayout = findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(refreshListener);

        adapter = new SensorListAdapter(sensors, MainActivity.this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);

        RecyclerView recyclerView = findViewById(R.id.sensors_data);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Won't show sensor value if it equals to null
        recyclerView.setAdapter(adapter);

        // Run 1st time when you launch app
        fetchSensorsData.run();
    }

    private Runnable fetchSensorsData = new Runnable() {
        @Override
        public void run() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.serviceURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            SACityService cityService = retrofit.create(SACityService.class);

            cityService.listSensors().enqueue(new Callback<List<SACitySensor>>() {
                @Override
                public void onResponse(@NonNull Call<List<SACitySensor>> call, @NonNull Response<List<SACitySensor>> response) {
                    if (response.body() != null) {
                        sensors.clear();
                        sensors.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<SACitySensor>> call, @NonNull Throwable throwable) {
                    Toast.makeText(MainActivity.this, "An error occurred during server request", Toast.LENGTH_SHORT).show();
                    Log.d("Enqueue/Error", throwable.getMessage());
                }
            });

            // Each 5 secs
            // ToDo: create settings activity to enable auto refresh
            // updateHandler.postDelayed(this, 5000);
        }
    };

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            // Starting from 1st second
            updateHandler.postDelayed(fetchSensorsData, 1000);
            swipeLayout.setRefreshing(false);
        }
    };

    protected void onDestroy(){
        super.onDestroy();

        updateHandler.removeCallbacksAndMessages(null);
    }
}
