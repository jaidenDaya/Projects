package com.example.assignment01b;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


//Name: Jaiden Daya and Nhu Nguyen
//Assignment 01B
//MainActivity.java
public class MainActivity extends AppCompatActivity {

    EditText enterTemp;
    TextView convertedTemp;
    RadioGroup group;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        enterTemp = findViewById(R.id.enterTemp);
        convertedTemp = findViewById(R.id.convertedTemp);
        group = findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String enteredTemp = enterTemp.getText().toString();
                if (checkedId == R.id.cToF) {
                    try {
                        double temp = Double.valueOf(enteredTemp);
                        double fahrenheit = ((temp * 9) / 5) + 32;
                        test = (String.valueOf(fahrenheit) + " F");
                    } catch (NumberFormatException exception) {
                        Toast.makeText(MainActivity.this, "Enter a valid number!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        double temp = Double.valueOf(enteredTemp);
                        double celcius = ((temp - 32) * 5) / 9;
                        test = (String.valueOf(celcius) + " C");
                    } catch (NumberFormatException exception) {
                        Toast.makeText(MainActivity.this, "Enter a valid number!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterTemp.setText("");
                convertedTemp.setText("");
            }
        });

        findViewById(R.id.calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                convertedTemp.setText(test);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}