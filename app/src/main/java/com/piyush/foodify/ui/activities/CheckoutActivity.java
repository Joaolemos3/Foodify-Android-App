package com.piyush.foodify.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.piyush.foodify.R;
import com.piyush.foodify.data.model.CartItem;
import com.piyush.foodify.databinding.ActivityCheckoutBinding;
import com.piyush.foodify.utils.Constants;
import com.piyush.foodify.viewmodel.CartViewModel;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    private ActivityCheckoutBinding binding;
    private CartViewModel cartViewModel;
    private double itemTotal = 0.0;
    private double deliveryFee = Constants.DELIVERY_FEE;
    private double taxes = 0.0;
    private double grandTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        setupViews();
        observeViewModel();
    }

    private void initViewModel() {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void setupViews() {
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Setup address spinner
        ArrayAdapter<String> addressAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Constants.DUMMY_LOCATIONS);
        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAddress.setAdapter(addressAdapter);

        // Setup payment method spinner
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Constants.PAYMENT_METHODS);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerPayment.setAdapter(paymentAdapter);

        binding.btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void observeViewModel() {
        cartViewModel.getAllCartItems().observe(this, this::calculateTotals);
    }

    private void calculateTotals(List<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            finish();
            return;
        }

        itemTotal = 0.0;
        for (CartItem item : cartItems) {
            itemTotal += item.getTotalPrice();
        }

        taxes = itemTotal * Constants.TAX_PERCENTAGE;
        grandTotal = itemTotal + deliveryFee + taxes;

        updateTotalViews();
    }

    private void updateTotalViews() {
        binding.tvItemTotal.setText("₹" + String.format("%.2f", itemTotal));
        binding.tvDeliveryFee.setText("₹" + String.format("%.2f", deliveryFee));
        binding.tvTaxes.setText("₹" + String.format("%.2f", taxes));
        binding.tvGrandTotal.setText("₹" + String.format("%.2f", grandTotal));
    }

    private void placeOrder() {
        String address = binding.spinnerAddress.getSelectedItem().toString();
        String paymentMethod = binding.spinnerPayment.getSelectedItem().toString();

        if (address.isEmpty() || paymentMethod.isEmpty()) {
            Toast.makeText(this, "Please select address and payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simulate order placement
        cartViewModel.clearCart();
        
        Intent intent = new Intent(this, OrderSuccessActivity.class);
        intent.putExtra("order_total", grandTotal);
        startActivity(intent);
        finish();
    }
}