package com.piyush.foodify.network;

import com.piyush.foodify.data.model.PositionStackResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PositionStackApiService {
    
    @GET("forward")
    Call<PositionStackResponse> geocodeAddress(
        @Query("access_key") String apiKey,
        @Query("query") String address,
        @Query("limit") int limit
    );
    
    @GET("reverse")
    Call<PositionStackResponse> reverseGeocode(
        @Query("access_key") String apiKey,
        @Query("query") String latLng,
        @Query("limit") int limit
    );
}