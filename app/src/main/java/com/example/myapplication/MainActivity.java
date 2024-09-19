package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner shapeSpinner;
    private EditText input1, input2, input3;
    private TextView labelInput1, labelInput2, labelInput3, resultArea, resultPerimeter, resultOther;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shapeSpinner = findViewById(R.id.shape_spinner);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        labelInput1 = findViewById(R.id.label_input1);
        labelInput2 = findViewById(R.id.label_input2);
        labelInput3 = findViewById(R.id.label_input3);
        resultArea = findViewById(R.id.result_area);
        resultPerimeter = findViewById(R.id.result_perimeter);
        resultOther = findViewById(R.id.result_other);
        calculateButton = findViewById(R.id.calculate_button);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shapes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shapeSpinner.setAdapter(adapter);

        shapeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                input3.setVisibility(View.GONE);
                labelInput3.setVisibility(View.GONE);

                switch (position) {
                    case 0: // Cuadrado
                        labelInput1.setText("Lado");
                        labelInput2.setVisibility(View.GONE);
                        input2.setVisibility(View.GONE);
                        break;
                    case 1: // Rectángulo
                        labelInput1.setText("Base");
                        labelInput2.setText("Altura");
                        labelInput2.setVisibility(View.VISIBLE);
                        input2.setVisibility(View.VISIBLE);
                        break;
                    case 2: // Círculo
                        labelInput1.setText("Radio");
                        labelInput2.setVisibility(View.GONE);
                        input2.setVisibility(View.GONE);
                        break;
                    case 3: // Triángulo Equilátero
                        labelInput1.setText("Lado");
                        labelInput2.setVisibility(View.GONE);
                        input2.setVisibility(View.GONE);
                        break;
                    case 4: // Triángulo Isósceles
                        labelInput1.setText("Base");
                        labelInput2.setText("Lado");
                        labelInput2.setVisibility(View.VISIBLE);
                        input2.setVisibility(View.VISIBLE);
                        break;
                    case 5: // Triángulo Escaleno
                        labelInput1.setText("Lado 1");
                        labelInput2.setText("Lado 2");
                        labelInput3.setText("Lado 3");
                        labelInput2.setVisibility(View.VISIBLE);
                        input2.setVisibility(View.VISIBLE);
                        labelInput3.setVisibility(View.VISIBLE);
                        input3.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        calculateButton.setOnClickListener(v -> {
            int selectedShape = shapeSpinner.getSelectedItemPosition();
            try {
                double value1 = Double.parseDouble(input1.getText().toString());
                double value2 = selectedShape == 1 || selectedShape == 4 || selectedShape == 5 ? Double.parseDouble(input2.getText().toString()) : 0;
                double value3 = selectedShape == 5 ? Double.parseDouble(input3.getText().toString()) : 0;

                switch (selectedShape) {
                    case 0: // Cuadrado
                        calculateSquare(value1);
                        break;
                    case 1: // Rectángulo
                        calculateRectangle(value1, value2);
                        break;
                    case 2: // Círculo
                        calculateCircle(value1);
                        break;
                    case 3: // Triángulo Equilátero
                        calculateEquilateralTriangle(value1);
                        break;
                    case 4: // Triángulo Isósceles
                        calculateIsoscelesTriangle(value1, value2);
                        break;
                    case 5: // Triángulo Escaleno
                        calculateScaleneTriangle(value1, value2, value3);
                        break;
                }
            } catch (NumberFormatException e) {
                resultArea.setText("Error: Ingrese valores válidos");
            }
        });
    }

    private void calculateSquare(double side) {
        double area = side * side;
        double perimeter = 4 * side;
        double diagonal = Math.sqrt(2) * side;
        displayResults(area, perimeter, "Diagonal: " + diagonal);
    }

    private void calculateRectangle(double base, double height) {
        double area = base * height;
        double perimeter = 2 * (base + height);
        double diagonal = Math.sqrt(base * base + height * height);
        displayResults(area, perimeter, "Diagonal: " + diagonal);
    }

    private void calculateCircle(double radius) {
        double area = Math.PI * radius * radius;
        double perimeter = 2 * Math.PI * radius;
        double diameter = 2 * radius;
        displayResults(area, perimeter, "Diámetro: " + diameter);
    }

    private void calculateEquilateralTriangle(double side) {
        double area = (Math.sqrt(3) / 4) * side * side;
        double perimeter = 3 * side;
        double semiPerimeter = perimeter / 2;
        displayResults(area, perimeter, "Semiperímetro: " + semiPerimeter);
    }

    private void calculateIsoscelesTriangle(double base, double side) {
        double height = Math.sqrt(side * side - (base * base) / 4);
        double area = (base * height) / 2;
        double perimeter = 2 * side + base;
        displayResults(area, perimeter, "Altura: " + height);
    }

    private void calculateScaleneTriangle(double side1, double side2, double side3) {
        double semiPerimeter = (side1 + side2 + side3) / 2;
        double area = Math.sqrt(semiPerimeter * (semiPerimeter - side1) * (semiPerimeter - side2) * (semiPerimeter - side3)); // Fórmula de Herón
        double perimeter = side1 + side2 + side3;
        displayResults(area, perimeter, "Semiperímetro: " + semiPerimeter);
    }

    private void displayResults(double area, double perimeter, String otherResult) {
        resultArea.setText("Área: " + area);
        resultPerimeter.setText("Perímetro: " + perimeter);
        resultOther.setText(otherResult);
    }
}
