package com.skufler.sacity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SACityService {
    @GET("get/list")
    Call<List<SACitySensor>> listSensors();

    @GET("get/{sensor_id}")
    Call<SACitySensor> getSensorById(int id);
}
