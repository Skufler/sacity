package com.skufler.sacity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class SensorListAdapter extends ArrayAdapter<SACitySensor> {
    private int resource;
    private LayoutInflater inflater;

    public SensorListAdapter(Context context, int resourceId, List<SACitySensor> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
        //context = ctx;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(resource, null);


        notifyDataSetChanged();
        return convertView;
    }
}
