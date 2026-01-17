package com.piyush.foodify.network;

import com.piyush.foodify.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static Retrofit retrofit = null;
    private static Retrofit foursquareRetrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
    
    public static FoursquareApiService getFoursquareApiService() {
        if (foursquareRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();
            
            foursquareRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.FOURSQUARE_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return foursquareRetrofit.create(FoursquareApiService.class);
    }
    
    public static PositionStackApiService getPositionStackApiService() {
        return new Retrofit.Builder()
                .baseUrl(Constants.POSITIONSTACK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PositionStackApiService.class);
    }
}