package com.piyush.foodify.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.piyush.foodify.data.model.CartItem;
import com.piyush.foodify.data.model.CategoryResponse;
import com.piyush.foodify.data.model.MealResponse;
import com.piyush.foodify.database.AppDatabase;
import com.piyush.foodify.database.CartDao;
import com.piyush.foodify.network.RetrofitClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;

public class FoodRepository {
    private CartDao cartDao;
    private ExecutorService executor;

    public FoodRepository(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        cartDao = database.cartDao();
        executor = Executors.newFixedThreadPool(4);
    }

    // API calls
    public Call<CategoryResponse> getCategories() {
        return RetrofitClient.getApiService().getCategories();
    }

    public Call<MealResponse> getMealsByCategory(String category) {
        return RetrofitClient.getApiService().getMealsByCategory(category);
    }

    public Call<MealResponse> getMealById(String mealId) {
        return RetrofitClient.getApiService().getMealById(mealId);
    }

    public Call<MealResponse> searchMeals(String query) {
        return RetrofitClient.getApiService().searchMeals(query);
    }

    // Cart operations
    public void addToCart(CartItem cartItem) {
        executor.execute(() -> {
            CartItem existingItem = cartDao.getCartItemById(cartItem.getMealId());
            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
                cartDao.updateCartItem(existingItem);
            } else {
                cartDao.insertCartItem(cartItem);
            }
        });
    }

    public void updateCartItem(CartItem cartItem) {
        executor.execute(() -> cartDao.updateCartItem(cartItem));
    }

    public void removeFromCart(CartItem cartItem) {
        executor.execute(() -> cartDao.deleteCartItem(cartItem));
    }

    public LiveData<List<CartItem>> getAllCartItems() {
        return cartDao.getAllCartItems();
    }

    public LiveData<Integer> getCartItemCount() {
        return cartDao.getCartItemCount();
    }

    public void clearCart() {
        executor.execute(() -> cartDao.clearCart());
    }
}