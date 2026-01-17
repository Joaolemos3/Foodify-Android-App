package com.piyush.foodify.network;

import com.piyush.foodify.data.model.CategoryResponse;
import com.piyush.foodify.data.model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    
    @GET("categories.php")
    Call<CategoryResponse> getCategories();
    
    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);
    
    @GET("lookup.php")
    Call<MealResponse> getMealById(@Query("i") String mealId);
    
    @GET("search.php")
    Call<MealResponse> searchMeals(@Query("s") String searchQuery);
}