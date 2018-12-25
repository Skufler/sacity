package com.skufler.sacity;

class SACitySensor {
    private int id;
    private String data;
    private String name;

    public SACitySensor(int id, String data, String name) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}