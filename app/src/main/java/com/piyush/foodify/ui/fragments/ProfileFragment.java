package com.piyush.foodify.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.piyush.foodify.databinding.FragmentProfileBinding;
import com.piyush.foodify.ui.activities.AuthActivity;
import com.piyush.foodify.utils.SharedPrefsHelper;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private SharedPrefsHelper prefsHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        prefsHelper = new SharedPrefsHelper(requireContext());
        setupViews();
    }

    private void setupViews() {
        // Load user data
        binding.tvUserName.setText(prefsHelper.getUserName());
        binding.tvUserEmail.setText(prefsHelper.getUserEmail());
        binding.tvUserPhone.setText(prefsHelper.getUserPhone());

        // Setup click listeners
        binding.layoutOrders.setOnClickListener(v -> {
            // Navigate to orders (dummy)
        });

        binding.layoutAddresses.setOnClickListener(v -> {
            // Navigate to addresses (dummy)
        });

        binding.layoutPayments.setOnClickListener(v -> {
            // Navigate to payment methods (dummy)
        });

        binding.layoutSupport.setOnClickListener(v -> {
            // Navigate to support (dummy)
        });

        binding.layoutAbout.setOnClickListener(v -> {
            // Navigate to about (dummy)
        });

        binding.btnLogout.setOnClickListener(v -> logout());
    }

    private void logout() {
        prefsHelper.clearUserData();
        Intent intent = new Intent(getContext(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}