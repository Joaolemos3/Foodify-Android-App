package com.piyush.foodify.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.piyush.foodify.data.model.Category;
import com.piyush.foodify.data.model.CategoryResponse;
import com.piyush.foodify.data.model.Meal;
import com.piyush.foodify.data.model.MealResponse;
import com.piyush.foodify.data.repository.FoodRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {
    private FoodRepository repository;
    private MutableLiveData<List<Category>> categoriesLiveData;
    private MutableLiveData<List<Meal>> mealsLiveData;
    private MutableLiveData<Boolean> loadingLiveData;
    private MutableLiveData<String> errorLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);
        categoriesLiveData = new MutableLiveData<>();
        mealsLiveData = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public LiveData<List<Meal>> getMealsLiveData() {
        return mealsLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void loadCategories() {
        loadingLiveData.setValue(true);
        repository.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categories = response.body().getCategories();
                    // Filter out beef category
                    categories.removeIf(category -> "Beef".equalsIgnoreCase(category.getStrCategory()));
                    categoriesLiveData.setValue(categories);
                } else {
                    errorLiveData.setValue("Failed to load categories");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Network error: " + t.getMessage());
            }
        });
    }

    public void loadMealsByCategory(String category) {
        // Filter out beef category
        if ("Beef".equalsIgnoreCase(category)) {
            category = "Chicken"; // Replace with chicken
        }
        
        loadingLiveData.setValue(true);
        repository.getMealsByCategory(category).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    mealsLiveData.setValue(response.body().getMeals());
                } else {
                    errorLiveData.setValue("Failed to load meals");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Network error: " + t.getMessage());
            }
        });
    }

    public void searchMeals(String query) {
        loadingLiveData.setValue(true);
        repository.searchMeals(query).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    mealsLiveData.setValue(response.body().getMeals());
                } else {
                    errorLiveData.setValue("No meals found");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Network error: " + t.getMessage());
            }
        });
    }
}