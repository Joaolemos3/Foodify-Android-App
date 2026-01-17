package com.piyush.foodify.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.piyush.foodify.R;
import com.piyush.foodify.data.model.Meal;
import com.piyush.foodify.databinding.ItemMenuBinding;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private List<Meal> meals;
    private OnMenuItemClickListener listener;

    public interface OnMenuItemClickListener {
        void onAddToCart(Meal meal);
    }

    public MenuAdapter(List<Meal> meals, OnMenuItemClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMenuBinding binding = ItemMenuBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
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

    class MenuViewHolder extends RecyclerView.ViewHolder {
        private ItemMenuBinding binding;

        public MenuViewHolder(ItemMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Meal meal) {
            binding.tvMealName.setText(meal.getStrMeal());
            binding.tvMealDescription.setText(meal.getStrInstructions() != null ? 
                meal.getStrInstructions().substring(0, Math.min(100, meal.getStrInstructions().length())) + "..." : 
                "Delicious " + meal.getStrCategory() + " dish");
            binding.tvPrice.setText("₹" + String.format("%.0f", meal.getPrice()));

            Glide.with(binding.getRoot().getContext())
                    .load(meal.getStrMealThumb())
                    .placeholder(R.drawable.placeholder_food)
                    .into(binding.ivMealImage);

            binding.btnAddToCart.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAddToCart(meal);
                }
            });
        }
    }
}