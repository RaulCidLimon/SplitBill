package com.raulcidlimon.myapplication;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class SuyoActivity extends AppCompatActivity implements ItemCreationDialog.ItemCreationDialogListener {

    ImageButton ibFromSuyo;
    RecyclerView rvListaObjetos;
    Button btnAdd;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suyo);
        //TODO ELIMINAR TODO
        //TODO 1º RECYCLERVIEW QUE MUESTRE TODOS LOS OBJETOS CON SUS PRECIOS
        //TODO 2º RV QUE MUESTRE LO QUE TIENE QUE PAGAR CADA UNO
        //TODO AÑADIR A CADA RV UN ITEMLISTENER PARA ELIMINAR LOS ELEMENTOS DE MANERA INDIVIAL...
        // MOSTRANDO UN DIALOG QUE PREGUNTE SI O NO


        ibFromSuyo = findViewById(R.id.ibFromSuyo);
        ibFromSuyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void openDialog() {
        ItemCreationDialog itemCreationDialog = new ItemCreationDialog();
        itemCreationDialog.show(getSupportFragmentManager(), String.valueOf(R.string.crear_objeto));

    }

    @Override
    public void recibirInformacion(String objectName, String money, String humanName) {
        //recibimos la información
        if (!objectName.equalsIgnoreCase("")
                && !humanName.equalsIgnoreCase("")
                && !money.equalsIgnoreCase("")) {
            double moneyC = Double.parseDouble(money);
            Toast.makeText(this, objectName + " " + moneyC + " " + humanName, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No hay valores", Toast.LENGTH_LONG).show();
        }

    }
}
