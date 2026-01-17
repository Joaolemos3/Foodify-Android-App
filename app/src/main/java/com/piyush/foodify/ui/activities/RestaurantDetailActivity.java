package com.piyush.foodify.ui.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.piyush.foodify.R;
import com.piyush.foodify.data.model.CartItem;
import com.piyush.foodify.data.model.Meal;
import com.piyush.foodify.ui.adapters.SimpleMenuAdapter;
import com.piyush.foodify.utils.Constants;
import com.piyush.foodify.viewmodel.CartViewModel;
import com.piyush.foodify.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantDetailActivity extends AppCompatActivity implements SimpleMenuAdapter.OnMenuClickListener {
    private HomeViewModel homeViewModel;
    private CartViewModel cartViewModel;
    private SimpleMenuAdapter menuAdapter;
    private String restaurantName;
    private RecyclerView recyclerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail_simple);

        restaurantName = getIntent().getStringExtra(Constants.EXTRA_RESTAURANT_NAME);
        String category = getIntent().getStringExtra(Constants.EXTRA_CATEGORY);

        initViewModels();
        setupViews();
        loadDummyMenu();
        
        if (category != null) {
            homeViewModel.loadMealsByCategory(category);
        }
    }

    private void initViewModels() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void setupViews() {
        recyclerMenu = findViewById(R.id.recycler_menu);
        
        // Back button
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        
        menuAdapter = new SimpleMenuAdapter(new ArrayList<>(), this);
        recyclerMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenu.setAdapter(menuAdapter);
        
        observeViewModels();
    }

    private void loadDummyMenu() {
        List<Meal> dummyMenu = Arrays.asList(
            createMeal("1", "Margherita Pizza", 299, "Classic pizza with tomato and mozzarella"),
            createMeal("2", "Chicken Burger", 199, "Juicy chicken burger with fries"),
            createMeal("3", "Pasta Alfredo", 249, "Creamy pasta with herbs"),
            createMeal("4", "Caesar Salad", 179, "Fresh salad with caesar dressing")
        );
        menuAdapter.updateMeals(dummyMenu);
    }

    private Meal createMeal(String id, String name, double price, String description) {
        Meal meal = new Meal();
        meal.setIdMeal(id);
        meal.setStrMeal(name);
        meal.setPrice(price);
        meal.setStrInstructions(description);
        meal.setStrMealThumb("https://via.placeholder.com/150");
        return meal;
    }

    private void observeViewModels() {
        homeViewModel.getMealsLiveData().observe(this, meals -> {
            if (meals != null && !meals.isEmpty()) {
                for (Meal meal : meals) {
                    meal.setPrice(Math.random() * 200 + 100);
                }
                menuAdapter.updateMeals(meals);
            }
        });
    }

    @Override
    public void onAddToCart(Meal meal) {
        CartItem cartItem = new CartItem(
            meal.getIdMeal(),
            meal.getStrMeal(),
            meal.getStrMealThumb(),
            meal.getPrice(),
            1,
            restaurantName != null ? restaurantName : "Restaurant"
        );
        
        cartViewModel.addToCart(cartItem);
        Toast.makeText(this, meal.getStrMeal() + " added to cart!", Toast.LENGTH_SHORT).show();
    }
}