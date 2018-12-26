package com.skufler.sacity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.skufler.sacity.MainActivity.updateHandler;

public class SensorListAdapter extends RecyclerView.Adapter<SensorHolder> {

    private Context context;
    private ArrayList<SACitySensor> sensors;

    SensorListAdapter(ArrayList<SACitySensor> sensors, Context context) {
        this.sensors = sensors;
        this.context = context;
    }

    @NonNull
    @Override
    public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new SensorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SensorHolder holder, final int position) {
        SACitySensor sensor = sensors.get(position);

        // https://processing.org/tutorials/pixels/imgs/tint1.jpg
        // http://i.imgur.com/DvpvklR.png
        Glide.with(context).load("https://processing.org/tutorials/pixels/imgs/tint1.jpg").into(holder.image);
        holder.name.setText(sensor.getName());
        holder.data.setText(sensor.getData());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SACitySensor selectedSensor = sensors.get(holder.getAdapterPosition());

                updateHandler.removeCallbacksAndMessages(null);

                Intent detailActivity = new Intent(v.getContext(), SensorDetailActivity.class);

                if (selectedSensor != null) {
                    detailActivity.putExtra("ITEM_ID", selectedSensor.getId());
                    detailActivity.putExtra("ITEM_DATA", selectedSensor.getData());
                    detailActivity.putExtra("ITEM_NAME", selectedSensor.getName());
                }

                v.getContext().startActivity(detailActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }
}