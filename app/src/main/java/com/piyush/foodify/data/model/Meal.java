package com.piyush.foodify.data.model;

public class Meal {
    private String idMeal;
    private String strMeal;
    private String strMealThumb;
    private String strCategory;
    private String strArea;
    private String strInstructions;
    private String strYoutube;
    private double price; // Dummy field for price
    private String restaurantName; // Dummy field for restaurant
    private double rating; // Dummy field for rating
    private int deliveryTime; // Dummy field for delivery time

    public Meal() {}

    public Meal(String idMeal, String strMeal, String strMealThumb, String strCategory, String strArea, String strInstructions) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        // Set dummy values
        this.price = 150 + (Math.random() * 300); // Random price between 150-450
        this.rating = 3.5 + (Math.random() * 1.5); // Random rating between 3.5-5.0
        this.deliveryTime = 20 + (int)(Math.random() * 40); // Random delivery time 20-60 mins
        this.restaurantName = generateRestaurantName();
    }

    private String generateRestaurantName() {
        String[] names = {"Spice Garden", "Food Palace", "Tasty Bites", "Royal Kitchen", "Flavor House", "Delicious Corner"};
        return names[(int)(Math.random() * names.length)];
    }

    // Getters and Setters
    public String getIdMeal() { return idMeal; }
    public void setIdMeal(String idMeal) { this.idMeal = idMeal; }

    public String getStrMeal() { return strMeal; }
    public void setStrMeal(String strMeal) { this.strMeal = strMeal; }

    public String getStrMealThumb() { return strMealThumb; }
    public void setStrMealThumb(String strMealThumb) { this.strMealThumb = strMealThumb; }

    public String getStrCategory() { return strCategory; }
    public void setStrCategory(String strCategory) { this.strCategory = strCategory; }

    public String getStrArea() { return strArea; }
    public void setStrArea(String strArea) { this.strArea = strArea; }

    public String getStrInstructions() { return strInstructions; }
    public void setStrInstructions(String strInstructions) { this.strInstructions = strInstructions; }

    public String getStrYoutube() { return strYoutube; }
    public void setStrYoutube(String strYoutube) { this.strYoutube = strYoutube; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(int deliveryTime) { this.deliveryTime = deliveryTime; }
}