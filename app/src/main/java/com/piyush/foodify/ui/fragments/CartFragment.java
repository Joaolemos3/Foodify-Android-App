package com.piyush.foodify.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.piyush.foodify.data.model.CartItem;
import com.piyush.foodify.databinding.FragmentCartBinding;
import com.piyush.foodify.ui.activities.CheckoutActivity;
import com.piyush.foodify.ui.adapters.CartAdapter;
import com.piyush.foodify.utils.Constants;
import com.piyush.foodify.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.OnCartItemClickListener {
    private FragmentCartBinding binding;
    private CartViewModel viewModel;
    private CartAdapter cartAdapter;
    private double itemTotal = 0.0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
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
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void setupViews() {
        cartAdapter = new CartAdapter(new ArrayList<>(), this);
        binding.recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerCart.setAdapter(cartAdapter);

        binding.btnCheckout.setOnClickListener(v -> {
            if (itemTotal > 0) {
                startActivity(new Intent(getContext(), CheckoutActivity.class));
            }
        });
    }

    private void observeViewModel() {
        viewModel.getAllCartItems().observe(getViewLifecycleOwner(), this::updateCartItems);
    }

    private void updateCartItems(List<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            binding.layoutEmptyCart.setVisibility(View.VISIBLE);
            binding.layoutCartContent.setVisibility(View.GONE);
        } else {
            binding.layoutEmptyCart.setVisibility(View.GONE);
            binding.layoutCartContent.setVisibility(View.VISIBLE);
            
            cartAdapter.updateCartItems(cartItems);
            calculateTotals(cartItems);
        }
    }

    private void calculateTotals(List<CartItem> cartItems) {
        itemTotal = 0.0;
        for (CartItem item : cartItems) {
            itemTotal += item.getTotalPrice();
        }

        double deliveryFee = Constants.DELIVERY_FEE;
        double taxes = itemTotal * Constants.TAX_PERCENTAGE;
        double grandTotal = itemTotal + deliveryFee + taxes;

        binding.tvItemTotal.setText("₹" + String.format("%.2f", itemTotal));
        binding.tvDeliveryFee.setText("₹" + String.format("%.2f", deliveryFee));
        binding.tvTaxes.setText("₹" + String.format("%.2f", taxes));
        binding.tvGrandTotal.setText("₹" + String.format("%.2f", grandTotal));
    }

    @Override
    public void onQuantityChanged(CartItem cartItem) {
        viewModel.updateCartItem(cartItem);
    }

    @Override
    public void onRemoveItem(CartItem cartItem) {
        viewModel.removeFromCart(cartItem);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}