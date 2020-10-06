package com.raulcidlimon.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnIguales;
    Button btnSuyo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO CREAR UN GOTOINFORMACION
        //TODO CREAR UN GOTOAJUSTES

        btnIguales = findViewById(R.id.btnIguales);
        btnIguales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToIguales();
            }
        });

        btnSuyo = findViewById(R.id.btnSuyo);
        btnSuyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSuyo();
            }
        });

    }

    private void goToSuyo() {
        Intent intent = new Intent(this, SuyoActivity.class);
        startActivity(intent);
    }

    private void goToIguales() {
        Intent intent = new Intent(this, IgualesActivity.class);
        startActivity(intent);

    }
}
