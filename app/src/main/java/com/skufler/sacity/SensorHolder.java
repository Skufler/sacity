package com.skufler.sacity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class SensorHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView data;
    public TextView name;

    SensorHolder(View view) {
        super(view);

        image = view.findViewById(R.id.sensor_image);
        name = view.findViewById(R.id.sensor_name);
        data = view.findViewById(R.id.sensor_data);
    }
}
