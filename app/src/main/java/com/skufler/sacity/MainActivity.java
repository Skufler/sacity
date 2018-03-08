package com.skufler.sacity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private Handler updateHandler = new Handler();
    private ArrayList<HashMap<String, String>> sensorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.sensors_data);

        updateHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new GetSensorsData().execute();
                sensorList = new ArrayList<>();
                updateHandler.postDelayed(this, 5000);
            }
        }, 1000);

    }


    @SuppressLint("StaticFieldLeak")
    private class GetSensorsData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://192.168.56.1/get/all";
            String responseJSON = httpHandler.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + responseJSON);
            if (responseJSON != null) {
                try {
                    /*
                    * { object : [ { field : values, field : values }, { ... } ... ] }
                    *
                    * */

                    JSONObject jsonObject = new JSONObject(responseJSON);
                    JSONArray jsonArray = jsonObject.getJSONArray("sensors");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject node = jsonArray.getJSONObject(i);

                        HashMap<String, String> sensor = new HashMap<>();

                        switch (node.getString("name")) {
                            case "Tmr":
                                sensor.put("name", "Temperature");
                                break;
                            case "Wat":
                                sensor.put("name", "Water level");
                                break;
                            case "Lum":
                                sensor.put("name", "Luminosity");
                                break;
                            case "Brg":
                                sensor.put("name", "Is bridge opened?");
                                break;
                            case "Prk":
                                sensor.put("name", "Parking seats engaged");
                                break;
                            case "Wet":
                                sensor.put("name", "Wetness");
                                break;
                            case "Vib":
                                sensor.put("name", "Earthquake level:");
                                break;
                            default:
                                sensor.put("name", node.getString("name"));
                                break;
                        }
                        sensor.put("data", node.getString("data"));

                        sensorList.add(sensor);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    sensorList,
                    R.layout.list_item,
                    new String[]{"name", "data"},
                    new int[]{R.id.sensor_name, R.id.sensor_data}
            );

            listView.setAdapter(adapter);
        }
    }
}
