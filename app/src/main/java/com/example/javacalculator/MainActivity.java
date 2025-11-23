package com.example.javacalculator;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.javacalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String currentDisplay = "0";
    private String currentOperation = "";
    private double firstNumber = 0;
    private double secondNumber = 0;
    private boolean isNewCalculation = true;
    private boolean shouldResetDisplay = false;

    private static final String KEY_CURRENT_DISPLAY = "current_display";
    private static final String KEY_CURRENT_OPERATION = "current_operation";
    private static final String KEY_FIRST_NUMBER = "first_number";
    private static final String KEY_IS_NEW_CALCULATION = "is_new_calculation";
    private static final String KEY_SHOULD_RESET_DISPLAY = "should_reset_display";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Calculator);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            currentDisplay = savedInstanceState.getString(KEY_CURRENT_DISPLAY, "0");
            currentOperation = savedInstanceState.getString(KEY_CURRENT_OPERATION, "");
            firstNumber = savedInstanceState.getDouble(KEY_FIRST_NUMBER, 0);
            isNewCalculation = savedInstanceState.getBoolean(KEY_IS_NEW_CALCULATION, true);
            shouldResetDisplay = savedInstanceState.getBoolean(KEY_SHOULD_RESET_DISPLAY, false);
        }

        setupUI();
        setupClickListeners();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CURRENT_DISPLAY, currentDisplay);
        outState.putString(KEY_CURRENT_OPERATION, currentOperation);
        outState.putDouble(KEY_FIRST_NUMBER, firstNumber);
        outState.putBoolean(KEY_IS_NEW_CALCULATION, isNewCalculation);
        outState.putBoolean(KEY_SHOULD_RESET_DISPLAY, shouldResetDisplay);
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
        if (currentDisplay.equals("0") || isNewCalculation || shouldResetDisplay) {
            currentDisplay = number;
            isNewCalculation = false;
            shouldResetDisplay = false;
        } else {
            currentDisplay += number;
        }
        updateDisplay();
    }

    private void appendDecimal() {
        if (isNewCalculation || shouldResetDisplay) {
            currentDisplay = "0.";
            isNewCalculation = false;
            shouldResetDisplay = false;
        } else if (!currentDisplay.contains(".")) {
            currentDisplay += ".";
        }
        updateDisplay();
    }

    private void setOperation(String operation) {
        try {
            if (!currentOperation.isEmpty() && !shouldResetDisplay) {
                calculateResult();
            }

            if (!shouldResetDisplay) {
                firstNumber = Double.parseDouble(currentDisplay);
            }

            currentOperation = operation;
            binding.tvOperation.setText(formatNumber(firstNumber) + " " + operation);
            shouldResetDisplay = true;

        } catch (NumberFormatException e) {
            showError("Ошибка ввода числа");
            clearDisplay();
        }
    }

    private void clearDisplay() {
        currentDisplay = "0";
        currentOperation = "";
        firstNumber = 0;
        secondNumber = 0;
        isNewCalculation = true;
        shouldResetDisplay = false;
        binding.tvOperation.setText("");
        updateDisplay();
    }

    private void backspace() {
        if (!isNewCalculation && currentDisplay.length() > 1 && !shouldResetDisplay) {
            currentDisplay = currentDisplay.substring(0, currentDisplay.length() - 1);
        } else {
            currentDisplay = "0";
            isNewCalculation = true;
            shouldResetDisplay = false;
        }
        updateDisplay();
    }

    private void calculateResult() {
        if (!currentOperation.isEmpty()) {
            try {
                if (!shouldResetDisplay) {
                    secondNumber = Double.parseDouble(currentDisplay);
                }

                double result = performCalculation(firstNumber, secondNumber, currentOperation);

                String expression = binding.tvOperation.getText().toString();
                if (!shouldResetDisplay) {
                    expression += " " + formatNumber(secondNumber);
                }
                binding.tvOperation.setText(expression + " =");

                if (Double.isInfinite(result)) {
                    currentDisplay = "Ошибка";
                    showError("Деление на ноль");
                } else if (Double.isNaN(result)) {
                    currentDisplay = "Ошибка";
                    showError("Неопределенный результат");
                } else {
                    currentDisplay = formatNumber(result);
                    firstNumber = result;
                }

                shouldResetDisplay = true;
                updateDisplay();

            } catch (NumberFormatException e) {
                showError("Ошибка ввода числа");
                clearDisplay();
            } catch (ArithmeticException e) {
                showError("Арифметическая ошибка");
                currentDisplay = "Ошибка";
                updateDisplay();
            }
        }
    }

    private double performCalculation(double num1, double num2, String operation) {
        switch (operation) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "×":
                return num1 * num2;
            case "÷":
                if (num2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return num1 / num2;
            default:
                return num2;
        }
    }

    private String formatNumber(double number) {
        if (number == (long) number) {
            return String.valueOf((long) number);
        } else {
            String result = String.valueOf(number);
            if (result.contains(".") && result.length() > 10) {
                result = String.format("%.6f", number).replace(",", ".");
                while (result.contains(".") && (result.endsWith("0") || result.endsWith("."))) {
                    result = result.substring(0, result.length() - 1);
                }
            }
            return result;
        }
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateDisplay() {
        binding.etDisplay.setText(currentDisplay);
    }
}