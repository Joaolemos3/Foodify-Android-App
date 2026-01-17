package com.piyush.foodify.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.piyush.foodify.R;
import com.piyush.foodify.data.model.CartItem;
import com.piyush.foodify.databinding.ItemCartBinding;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnCartItemClickListener listener;

    public interface OnCartItemClickListener {
        void onQuantityChanged(CartItem cartItem);
        void onRemoveItem(CartItem cartItem);
    }

    public CartAdapter(List<CartItem> cartItems, OnCartItemClickListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = ItemCartBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(cartItems.get(position));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateCartItems(List<CartItem> newCartItems) {
        this.cartItems = newCartItems;
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private ItemCartBinding binding;

        public CartViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CartItem cartItem) {
            binding.tvMealName.setText(cartItem.getMealName());
            binding.tvRestaurantName.setText(cartItem.getRestaurantName());
            binding.tvPrice.setText("₹" + String.format("%.0f", cartItem.getPrice()));
            binding.tvQuantity.setText(String.valueOf(cartItem.getQuantity()));
            binding.tvTotalPrice.setText("₹" + String.format("%.0f", cartItem.getTotalPrice()));

            Glide.with(binding.getRoot().getContext())
                    .load(cartItem.getMealImage())
                    .placeholder(R.drawable.placeholder_food)
                    .into(binding.ivMealImage);

            binding.btnDecrease.setOnClickListener(v -> {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    binding.tvQuantity.setText(String.valueOf(cartItem.getQuantity()));
                    binding.tvTotalPrice.setText("₹" + String.format("%.0f", cartItem.getTotalPrice()));
                    if (listener != null) {
                        listener.onQuantityChanged(cartItem);
                    }
                }
            });

            binding.btnIncrease.setOnClickListener(v -> {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                binding.tvQuantity.setText(String.valueOf(cartItem.getQuantity()));
                binding.tvTotalPrice.setText("₹" + String.format("%.0f", cartItem.getTotalPrice()));
                if (listener != null) {
                    listener.onQuantityChanged(cartItem);
                }
            });

            binding.btnRemove.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRemoveItem(cartItem);
                }
            });
        }
    }
}