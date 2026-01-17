package com.piyush.foodify.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.piyush.foodify.data.model.Meal;
import com.piyush.foodify.databinding.FragmentSearchBinding;
import com.piyush.foodify.ui.activities.RestaurantDetailActivity;
import com.piyush.foodify.ui.adapters.RestaurantAdapter;
import com.piyush.foodify.utils.Constants;
import com.piyush.foodify.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements RestaurantAdapter.OnRestaurantClickListener {
    private FragmentSearchBinding binding;
    private HomeViewModel viewModel;
    private RestaurantAdapter searchAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViewModel();
        setupViews();
        observeViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void setupViews() {
        searchAdapter = new RestaurantAdapter(new ArrayList<>(), this);
        binding.recyclerSearchResults.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerSearchResults.setAdapter(searchAdapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    viewModel.searchMeals(s.toString());
                    binding.tvEmptyState.setVisibility(View.GONE);
                } else if (s.length() == 0) {
                    searchAdapter.updateMeals(new ArrayList<>());
                    binding.tvEmptyState.setVisibility(View.VISIBLE);
                    binding.tvEmptyState.setText("Search for restaurants, cuisines...");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void observeViewModel() {
        viewModel.getMealsLiveData().observe(getViewLifecycleOwner(), meals -> {
            if (meals != null && !meals.isEmpty()) {
                searchAdapter.updateMeals(meals);
                binding.tvEmptyState.setVisibility(View.GONE);
            } else {
                searchAdapter.updateMeals(new ArrayList<>());
                binding.tvEmptyState.setVisibility(View.VISIBLE);
                binding.tvEmptyState.setText("No results found");
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

    @Override
    public void onRestaurantClick(Meal meal) {
        Intent intent = new Intent(getContext(), RestaurantDetailActivity.class);
        intent.putExtra(Constants.EXTRA_RESTAURANT_NAME, meal.getRestaurantName());
        intent.putExtra(Constants.EXTRA_CATEGORY, meal.getStrCategory());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}