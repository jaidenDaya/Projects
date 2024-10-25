package com.example.evaluation01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.viewmodel.CreationExtras;

public class MainActivity extends AppCompatActivity {
    EditText a;
    EditText b;
    TextView opp;
    RadioGroup group;
    Button reset;
    Button calculate;
    TextView result;
    String test;
    int id;
    Button add;
    Button sub;
    Button mult;
    Button div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        opp = findViewById(R.id.opp);
        group = findViewById(R.id.radioGroup);
        reset = findViewById(R.id.reset);
        calculate = findViewById(R.id.calculate);
        result = findViewById(R.id.result);
        add = findViewById(R.id.addButton);
        sub = findViewById(R.id.subButton);
        mult = findViewById(R.id.multButton);
        div = findViewById(R.id.divButton);
        opp.setText("?");
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String A = a.getText().toString();
                String B = b.getText().toString();
                try {
                    id = checkedId;
                    if (checkedId == R.id.addButton) {
                        opp.setText("+");
                        double val1 = Double.valueOf(A);
                        double val2 = Double.valueOf(B);
                        double res = val1 + val2;
                        result.setText(A + " + " + B + " = " + res);
                        test = String.valueOf(res);
                    } else if (checkedId == R.id.subButton) {
                        opp.setText("-");
                        double val1 = Double.valueOf(A);
                        double val2 = Double.valueOf(B);
                        double res = val1 - val2;
                        test = String.valueOf(res);
                        result.setText(A + " - " + B + " = " + res);
                    } else if (checkedId == R.id.multButton) {
                        opp.setText("*");
                        double val1 = Double.valueOf(A);
                        double val2 = Double.valueOf(B);
                        double res = val1 * val2;
                        test = String.valueOf(res);
                        result.setText(A + " * " + B + " = " + res);
                    } else if (checkedId == R.id.divButton){
                        opp.setText("/");
                        double val1 = Double.valueOf(A);
                        double val2 = Double.valueOf(B);
                        double res = val1 / val2;
                        test = String.valueOf(res);
                        result.setText(A + " / " + B + " = " + res);

                    }
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Fill out form!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String A = a.getText().toString();
                String B = b.getText().toString();
                String o = opp.getText().toString();
                if(A.equals("")){
                    Toast.makeText(MainActivity.this, "Enter a number!", Toast.LENGTH_SHORT).show();
                } else if (B.equals("")){
                    Toast.makeText(MainActivity.this, "Enter a number!", Toast.LENGTH_SHORT).show();
                }
                else if (opp.getText().toString().equals("/") && b.getText().toString().equals("0.0")) {
                    Toast.makeText(MainActivity.this, "Cannot divide by 0!", Toast.LENGTH_SHORT).show();
                }
                else if(id == -1) {
                    Toast.makeText(MainActivity.this, "Choose an operator!", Toast.LENGTH_SHORT).show();
                }
                else {
                    result.setText(A + " " + o + " " + B + " = " + test);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.setText("");
                b.setText("");
                result.setText("");
                group.clearCheck();
                opp.setText("?");
                test = "";

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}