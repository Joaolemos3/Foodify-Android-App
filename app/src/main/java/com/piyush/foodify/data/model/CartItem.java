package com.piyush.foodify.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartItem {
    @PrimaryKey
    @NonNull
    private String mealId;
    private String mealName;
    private String mealImage;
    private double price;
    private int quantity;
    private String restaurantName;

    public CartItem() {}

    @Ignore
    public CartItem(String mealId, String mealName, String mealImage, double price, int quantity, String restaurantName) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealImage = mealImage;
        this.price = price;
        this.quantity = quantity;
        this.restaurantName = restaurantName;
    }

    // Getters and Setters
    public String getMealId() { return mealId; }
    public void setMealId(String mealId) { this.mealId = mealId; }

    public String getMealName() { return mealName; }
    public void setMealName(String mealName) { this.mealName = mealName; }

    public String getMealImage() { return mealImage; }
    public void setMealImage(String mealImage) { this.mealImage = mealImage; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public double getTotalPrice() {
        return price * quantity;
    }
}