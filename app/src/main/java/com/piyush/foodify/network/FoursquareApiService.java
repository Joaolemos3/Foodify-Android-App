package com.piyush.foodify.network;

import com.piyush.foodify.data.model.FoursquareResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface FoursquareApiService {
    
    @GET("places/search")
    Call<FoursquareResponse> searchRestaurants(
        @Header("Authorization") String apiKey,
        @Query("query") String query,
        @Query("ll") String latLng,
        @Query("categories") String categories,
        @Query("limit") int limit
    );
    
    @GET("places/nearby")
    Call<FoursquareResponse> getNearbyRestaurants(
        @Header("Authorization") String apiKey,
        @Query("ll") String latLng,
        @Query("categories") String categories,
        @Query("limit") int limit
    );
}