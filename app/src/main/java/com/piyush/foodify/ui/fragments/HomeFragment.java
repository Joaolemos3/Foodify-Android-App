package com.piyush.foodify.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.piyush.foodify.data.model.Category;
import com.piyush.foodify.data.model.Meal;
import com.piyush.foodify.databinding.FragmentHomeBinding;
import com.piyush.foodify.ui.activities.RestaurantDetailActivity;
import com.piyush.foodify.ui.adapters.CategoryAdapter;
import com.piyush.foodify.ui.adapters.RestaurantAdapter;
import com.piyush.foodify.utils.Constants;
import com.piyush.foodify.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements CategoryAdapter.OnCategoryClickListener, RestaurantAdapter.OnRestaurantClickListener {
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private RestaurantAdapter restaurantAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViewModel();
        setupViews();
        observeViewModel();
        loadData();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void setupViews() {
        // Setup location
        binding.tvLocation.setText(Constants.DUMMY_LOCATIONS[0]);
        
        // Setup search
        binding.etSearch.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Search functionality", Toast.LENGTH_SHORT).show();
        });

        // Setup categories RecyclerView
        categoryAdapter = new CategoryAdapter(new ArrayList<>(), this);
        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerCategories.setAdapter(categoryAdapter);

        // Setup restaurants RecyclerView
        restaurantAdapter = new RestaurantAdapter(new ArrayList<>(), this);
        binding.recyclerRestaurants.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerRestaurants.setAdapter(restaurantAdapter);
    }

    private void observeViewModel() {
        viewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                categoryAdapter.updateCategories(categories);
                // Load meals for first category
                if (!categories.isEmpty()) {
                    viewModel.loadMealsByCategory(categories.get(0).getStrCategory());
                }
            }
        });

        viewModel.getMealsLiveData().observe(getViewLifecycleOwner(), meals -> {
            if (meals != null) {
                restaurantAdapter.updateMeals(meals);
            }
        });

        viewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        Toast.makeText(getContext(), "Loading food data...", Toast.LENGTH_SHORT).show();
        viewModel.loadCategories();
    }

    @Override
    public void onCategoryClick(Category category) {
        viewModel.loadMealsByCategory(category.getStrCategory());
    }

    @Override
    public void onRestaurantClick(Meal meal) {
        Intent intent = new Intent(getContext(), RestaurantDetailActivity.class);
        intent.putExtra(Constants.EXTRA_RESTAURANT_NAME, meal.getStrMeal());
        intent.putExtra(Constants.EXTRA_CATEGORY, meal.getStrCategory());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}