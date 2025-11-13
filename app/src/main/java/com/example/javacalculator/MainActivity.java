package com.example.javacalculator;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.javacalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Calculator);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupUI();
    }

    private void setupUI() {
        binding.etDisplay.setText("0");
        binding.tvOperation.setText("");
    }
}