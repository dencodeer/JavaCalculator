package com.example.javacalculator;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.javacalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String currentDisplay = "0";
    private String currentOperation = "";

    private static final String KEY_CURRENT_DISPLAY = "current_display";
    private static final String KEY_CURRENT_OPERATION = "current_operation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Calculator);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            currentDisplay = savedInstanceState.getString(KEY_CURRENT_DISPLAY, "0");
            currentOperation = savedInstanceState.getString(KEY_CURRENT_OPERATION, "");
        }

        setupUI();
        setupClickListeners();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CURRENT_DISPLAY, currentDisplay);
        outState.putString(KEY_CURRENT_OPERATION, currentOperation);
    }

    private void setupUI() {
        binding.etDisplay.setText(currentDisplay);
        binding.tvOperation.setText(currentOperation);
    }

    private void setupClickListeners() {
        binding.btn0.setOnClickListener(v -> appendNumber("0"));
        binding.btn1.setOnClickListener(v -> appendNumber("1"));
        binding.btn2.setOnClickListener(v -> appendNumber("2"));
        binding.btn3.setOnClickListener(v -> appendNumber("3"));
        binding.btn4.setOnClickListener(v -> appendNumber("4"));
        binding.btn5.setOnClickListener(v -> appendNumber("5"));
        binding.btn6.setOnClickListener(v -> appendNumber("6"));
        binding.btn7.setOnClickListener(v -> appendNumber("7"));
        binding.btn8.setOnClickListener(v -> appendNumber("8"));
        binding.btn9.setOnClickListener(v -> appendNumber("9"));

        binding.btnAdd.setOnClickListener(v -> setOperation("+"));
        binding.btnSubtract.setOnClickListener(v -> setOperation("-"));
        binding.btnMultiply.setOnClickListener(v -> setOperation("×"));
        binding.btnDivide.setOnClickListener(v -> setOperation("÷"));

        binding.btnDecimal.setOnClickListener(v -> appendDecimal());
        binding.btnClear.setOnClickListener(v -> clearDisplay());
        binding.btnBackspace.setOnClickListener(v -> backspace());
        binding.btnEquals.setOnClickListener(v -> calculateResult());
    }

    private void appendNumber(String number) {
        if (currentDisplay.equals("0")) {
            currentDisplay = number;
        } else {
            currentDisplay += number;
        }
        updateDisplay();
    }

    private void appendDecimal() {
        if (!currentDisplay.contains(".")) {
            currentDisplay += ".";
            updateDisplay();
        }
    }

    private void setOperation(String operation) {
        currentOperation = operation;
        binding.tvOperation.setText(currentDisplay + " " + operation);
        currentDisplay = "0";
        updateDisplay();
    }

    private void clearDisplay() {
        currentDisplay = "0";
        currentOperation = "";
        binding.tvOperation.setText("");
        updateDisplay();
    }

    private void backspace() {
        if (currentDisplay.length() > 1) {
            currentDisplay = currentDisplay.substring(0, currentDisplay.length() - 1);
        } else {
            currentDisplay = "0";
        }
        updateDisplay();
    }

    private void calculateResult() {
        if (!currentOperation.isEmpty()) {
            String expression = binding.tvOperation.getText().toString() + " " + currentDisplay;
            binding.tvOperation.setText(expression + " =");
            Toast.makeText(this, "Расчеты будут добавлены позже", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDisplay() {
        binding.etDisplay.setText(currentDisplay);
    }
}