package com.piyush.foodify.utils;

public class Constants {
    public static final String EXTRA_MEAL = "extra_meal";
    public static final String EXTRA_RESTAURANT_NAME = "extra_restaurant_name";
    public static final String EXTRA_CATEGORY = "extra_category";
    
    public static final double DELIVERY_FEE = 30.0;
    public static final double TAX_PERCENTAGE = 0.05; // 5% tax
    
    // Foursquare API
    public static final String FOURSQUARE_API_KEY = "0C5RTRRXQPOJPAIHJQ02IFT4PZKFOY4HF0HIBTLEJ51VSBFW";
    public static final String FOURSQUARE_BASE_URL = "https://api.foursquare.com/v3/";
    
    // PositionStack API
    public static final String POSITIONSTACK_API_KEY = "7b7d4367826f5976e0438a9e3d6d5fb5";
    public static final String POSITIONSTACK_BASE_URL = "http://api.positionstack.com/v1/";
    
    // Mappls API - Add your API keys here
    public static final String MAPPLS_API_KEY = "YOUR_MAPPLS_API_KEY";
    public static final String MAPPLS_REST_KEY = "YOUR_MAPPLS_REST_KEY";
    
    public static final String[] DUMMY_LOCATIONS = {
        "Koramangala, Bangalore",
        "Indiranagar, Bangalore", 
        "Whitefield, Bangalore",
        "HSR Layout, Bangalore",
        "BTM Layout, Bangalore"
    };
    
    public static final String[] PAYMENT_METHODS = {
        "UPI",
        "Credit/Debit Card",
        "Cash on Delivery"
    };
}