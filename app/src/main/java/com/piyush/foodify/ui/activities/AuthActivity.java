package com.piyush.foodify.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.piyush.foodify.R;
import com.piyush.foodify.utils.SharedPrefsHelper;

public class AuthActivity extends AppCompatActivity {
    private SharedPrefsHelper prefsHelper;
    private boolean isLoginMode = true;
    private EditText etEmail, etPassword, etName, etPhone;
    private Button btnAuth;
    private TextView tvToggle, tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        
        prefsHelper = new SharedPrefsHelper(this);
        initViews();
        setupClickListeners();
        updateUI();
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        btnAuth = findViewById(R.id.btn_auth);
        tvToggle = findViewById(R.id.tv_toggle_auth);
        tvTitle = findViewById(R.id.tv_title);
    }

    private void setupClickListeners() {
        btnAuth.setOnClickListener(v -> {
            if (isLoginMode) {
                performLogin();
            } else {
                performSignup();
            }
        });

        tvToggle.setOnClickListener(v -> {
            isLoginMode = !isLoginMode;
            updateUI();
        });
    }

    private void updateUI() {
        if (isLoginMode) {
            findViewById(R.id.til_name).setVisibility(android.view.View.GONE);
            findViewById(R.id.til_phone).setVisibility(android.view.View.GONE);
            btnAuth.setText("Login");
            tvToggle.setText("Don't have an account? Sign Up");
            tvTitle.setText("Welcome Back!");
        } else {
            findViewById(R.id.til_name).setVisibility(android.view.View.VISIBLE);
            findViewById(R.id.til_phone).setVisibility(android.view.View.VISIBLE);
            btnAuth.setText("Sign Up");
            tvToggle.setText("Already have an account? Login");
            tvTitle.setText("Create Account");
        }
    }

    private void performLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        prefsHelper.setLoggedIn(true);
        prefsHelper.saveUserData("User", email, "");
        
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void performSignup() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        prefsHelper.setLoggedIn(true);
        prefsHelper.saveUserData(name, email, phone);
        
        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}