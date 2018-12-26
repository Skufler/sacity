package com.skufler.sacity;

import android.support.annotation.NonNull;

class SACitySensor {

    private int id;
    @NonNull
    private String data;
    @NonNull
    private String name;

    public SACitySensor(int id, @NonNull String data, @NonNull String name) {
        this.id = id;
        this.data = data;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}