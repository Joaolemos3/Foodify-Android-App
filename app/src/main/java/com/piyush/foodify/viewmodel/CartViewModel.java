package com.piyush.foodify.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.piyush.foodify.data.model.CartItem;
import com.piyush.foodify.data.repository.FoodRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private FoodRepository repository;

    public CartViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);
    }

    public LiveData<List<CartItem>> getAllCartItems() {
        return repository.getAllCartItems();
    }

    public LiveData<Integer> getCartItemCount() {
        return repository.getCartItemCount();
    }

    public void addToCart(CartItem cartItem) {
        repository.addToCart(cartItem);
    }

    public void updateCartItem(CartItem cartItem) {
        repository.updateCartItem(cartItem);
    }

    public void removeFromCart(CartItem cartItem) {
        repository.removeFromCart(cartItem);
    }

    public void clearCart() {
        repository.clearCart();
    }
}