package com.piyush.foodify.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.piyush.foodify.data.model.CartItem;

import java.util.List;

@Dao
public interface CartDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCartItem(CartItem cartItem);
    
    @Update
    void updateCartItem(CartItem cartItem);
    
    @Delete
    void deleteCartItem(CartItem cartItem);
    
    @Query("SELECT * FROM cart_items")
    LiveData<List<CartItem>> getAllCartItems();
    
    @Query("SELECT * FROM cart_items WHERE mealId = :mealId")
    CartItem getCartItemById(String mealId);
    
    @Query("DELETE FROM cart_items")
    void clearCart();
    
    @Query("SELECT COUNT(*) FROM cart_items")
    LiveData<Integer> getCartItemCount();
}