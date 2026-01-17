package com.piyush.foodify.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.piyush.foodify.R;
import com.piyush.foodify.data.model.Meal;
import com.piyush.foodify.databinding.ItemRestaurantBinding;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private List<Meal> meals;
    private OnRestaurantClickListener listener;

    public interface OnRestaurantClickListener {
        void onRestaurantClick(Meal meal);
    }

    public RestaurantAdapter(List<Meal> meals, OnRestaurantClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRestaurantBinding binding = ItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new RestaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.bind(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void updateMeals(List<Meal> newMeals) {
        this.meals = newMeals;
        notifyDataSetChanged();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private ItemRestaurantBinding binding;

        public RestaurantViewHolder(ItemRestaurantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Meal meal) {
            binding.tvRestaurantName.setText(meal.getRestaurantName());
            binding.tvCuisine.setText(meal.getStrCategory());
            binding.tvRating.setText(String.format("%.1f", meal.getRating()));
            binding.tvDeliveryTime.setText(meal.getDeliveryTime() + " mins");
            binding.tvOffer.setText("50% OFF up to ₹100");

            Glide.with(binding.getRoot().getContext())
                    .load(meal.getStrMealThumb())
                    .placeholder(R.drawable.placeholder_restaurant)
                    .into(binding.ivRestaurantImage);

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRestaurantClick(meal);
                }
            });
        }
    }
}