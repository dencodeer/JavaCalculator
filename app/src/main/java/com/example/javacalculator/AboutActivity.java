package com.example.javacalculator;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.javacalculator.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();
        setupGif();
        setupContent();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("О приложении");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupGif() {
        Glide.with(this)
                .load(R.drawable.calculator_gif)
                .placeholder(R.drawable.calculator_placeholder)
                .into(binding.ivGif);
    }

    private void setupContent() {
        binding.tvAppName.setText("Умный калькулятор");
        binding.tvVersion.setText("Версия 1.0");
        binding.tvDescription.setText("Это современный калькулятор с интуитивно понятным интерфейсом и всеми необходимыми функциями для повседневных вычислений.");

        binding.tvFeatures.setText("Возможности:\n• Базовые арифметические операции\n• Поддержка десятичных дробей\n• История операций\n• Красивый и удобный интерфейс");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}