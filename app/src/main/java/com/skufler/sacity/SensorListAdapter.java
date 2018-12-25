package com.skufler.sacity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SensorListAdapter extends ArrayAdapter<SACitySensor> {
    private Context context;
    private List<SACitySensor> sensors = new ArrayList<>();

    SensorListAdapter(Context context, ArrayList<SACitySensor> objects) {
        super(context, 0, objects);
        this.context = context;
        this.sensors = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item, parent,false);

        SACitySensor sensor = sensors.get(position);

        TextView data = listItem.findViewById(R.id.sensor_data);
        data.setText(sensor.getData());

        TextView name = listItem.findViewById(R.id.sensor_name);
        name.setText(sensor.getName());

        notifyDataSetChanged();
        return listItem;
    }
}
