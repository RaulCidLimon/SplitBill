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
        //TODO CREAR UN GOTOAJUSTES
        iniciarBotonesMain();
    }

    private void iniciarBotonesMain() {
        btnIguales = findViewById(R.id.btnIguales);
        btnIguales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IgualesActivity.class);
                startActivity(intent);
            }
        });

        btnSuyo = findViewById(R.id.btnSuyo);
        btnSuyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SuyoActivity.class);
                startActivity(intent);
            }
        });

    }
}
