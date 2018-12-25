package com.skufler.sacity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private SensorListAdapter adapter;
    private ListView listView;
    private Handler updateHandler = new Handler();
    private ArrayList<SACitySensor> sensors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.sensors_data);

        Runnable fetchSensorsData = new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.serviceURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                SACityService cityService = retrofit.create(SACityService.class);
                cityService.listSensors().enqueue(new Callback<List<SACitySensor>>() {
                    @Override
                    public void onResponse(Call<List<SACitySensor>> call, retrofit2.Response<List<SACitySensor>> response) {
                        if (response.body() != null) {
                            sensors.clear();
                            sensors.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SACitySensor>> call, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                        Log.d("Enqueue/Error", throwable.getMessage());
                    }
                });

                // Each 10 secs
                updateHandler.postDelayed(this, 5000);
            }
        };
        // Starting from 1st second
        updateHandler.postDelayed(fetchSensorsData, 1000);


        // Won't show value if it equals to null
        adapter = new SensorListAdapter(this, sensors);
        listView.setAdapter(adapter);
    }

    protected void onDestroy(){
        super.onDestroy();

        updateHandler.removeCallbacksAndMessages(null);
    }
}
