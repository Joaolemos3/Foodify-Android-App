package com.piyush.foodify.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.piyush.foodify.R;
import com.piyush.foodify.data.model.Meal;

import java.util.List;

public class SimpleMenuAdapter extends RecyclerView.Adapter<SimpleMenuAdapter.ViewHolder> {
    private List<Meal> meals;
    private OnMenuClickListener listener;

    public interface OnMenuClickListener {
        void onAddToCart(Meal meal);
    }

    public SimpleMenuAdapter(List<Meal> meals, OnMenuClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        
        holder.tvFoodName.setText(meal.getStrMeal());
        holder.tvFoodPrice.setText("₹" + String.format("%.0f", meal.getPrice()));
        holder.tvFoodDescription.setText(meal.getStrInstructions());
        
        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.placeholder_food)
                .into(holder.ivFoodImage);
        
        holder.btnAddToCart.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddToCart(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void updateMeals(List<Meal> newMeals) {
        this.meals = newMeals;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoodImage;
        TextView tvFoodName, tvFoodPrice, tvFoodDescription;
        Button btnAddToCart;

        ViewHolder(View itemView) {
            super(itemView);
            ivFoodImage = itemView.findViewById(R.id.iv_food_image);
            tvFoodName = itemView.findViewById(R.id.tv_food_name);
            tvFoodPrice = itemView.findViewById(R.id.tv_food_price);
            tvFoodDescription = itemView.findViewById(R.id.tv_food_description);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }
}