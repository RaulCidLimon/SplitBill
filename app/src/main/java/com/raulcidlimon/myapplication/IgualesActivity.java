package com.raulcidlimon.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class IgualesActivity extends AppCompatActivity {


    int divisor;
    final Integer[] sItems = new Integer[]{2, 3, 4, 5, 6, 7, 8, 9, 10};
    Spinner spinnerIguales;
    EditText etIguales;
    TextView tvIgualesRes;
    ImageButton ibFromIguales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iguales);

        ibFromIguales = findViewById(R.id.ibFromIguales);
        ibFromIguales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinnerAdapter();

        etIguales = findViewById(R.id.etIguales);
        tvIgualesRes = findViewById(R.id.tvIgualesRes);

        cambiarTexto();
    }

    private void spinnerAdapter() {
        spinnerIguales = findViewById(R.id.spinnerIguales);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sItems);
        spinnerIguales.setAdapter(adapter);
        spinnerIguales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                divisor = position + 2;
                operacion();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void cambiarTexto() {
        etIguales.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("Main", "e");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    operacion();
                } catch (Exception e) {
                    Log.d("Main", "On text changed");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1 && s.toString().equalsIgnoreCase("0")) {
                    s.clear();
                } else if (s.toString().length() == 1 && s.toString().equalsIgnoreCase(".")) {
                    etIguales.setText("0.");
                    etIguales.setSelection(2);
                }
                Log.d("Main", "e");
            }
        });

    }

    private void operacion() {
        if (!etIguales.getText().toString().isEmpty() && etIguales != null) {
            double calculo = Double.parseDouble(etIguales.getText().toString()) / ((double) divisor);
            DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00");
            tvIgualesRes.setText(String.valueOf(decimalFormat.format(calculo)));
        } else {
            tvIgualesRes.setText(R.string.zero);
        }

    }
}
