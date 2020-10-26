package com.raulcidlimon.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class SuyoActivity extends AppCompatActivity implements ItemCreationDialog.ItemCreationDialogListener, ItemDeleteDialog.ItemDeleteDialogListener {

    private SQLiteDatabase sqLiteDatabase;
    SplitBillDBHelper splitBillDBHelper = new SplitBillDBHelper(this);

    private BillAdapter billAdapter;
    private ResultadoAdapter resultadoAdapter;

    ImageButton ibFromSuyo;
    Button btnAdd;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suyo);
        sqLiteDatabase = splitBillDBHelper.getWritableDatabase();

        iniciarBotones();
        iniciarRV();
    }

    private void iniciarRV() {
        final RecyclerView rvListaItems = findViewById(R.id.rvListaItems);
        rvListaItems.setLayoutManager(new LinearLayoutManager(this));
        billAdapter = new BillAdapter(this, getAllItems());

        rvListaItems.setAdapter(billAdapter);

        final RecyclerView rvResultadoItems = findViewById(R.id.rvResultadoItems);
        rvResultadoItems.setLayoutManager(new LinearLayoutManager(this));
        resultadoAdapter = new ResultadoAdapter(this, getAllResultadoItems());

        rvResultadoItems.setAdapter(resultadoAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                eliminarObjeto((long) viewHolder.itemView.getTag());

            }
        }).attachToRecyclerView(rvListaItems);
    }

    private void iniciarBotones() {
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
                openAddDialog();
            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteDialog();
            }
        });
    }

    private void totalSumRv(String humanName, double money, boolean isBorrar) {
        String comprobarHumano;
        SQLiteDatabase writableDatabase = splitBillDBHelper.getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("SELECT * FROM resultTable WHERE "
                + SplitBillContract.BillEntry.COLUMN_RESULT_HUMAN + " LIKE '"
                + humanName + "'", null);

        if (isBorrar) {
            cursor.moveToFirst();
            do {
                comprobarHumano = cursor.getString(2);
                if (comprobarHumano.equalsIgnoreCase(humanName)) {

                    double restarDinero = cursor.getDouble(
                            cursor.getColumnIndex(
                                    SplitBillContract.BillEntry.COLUMN_RESULT_MONEY)) - money;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(SplitBillContract.BillEntry.COLUMN_RESULT_MONEY, restarDinero);

                    sqLiteDatabase.update(SplitBillContract.BillEntry.TABLE_RESULT_NAME,
                            contentValues,
                            "humanR = ?",
                            new String[]{humanName});

                    if (restarDinero == 0) {
                        sqLiteDatabase.delete(SplitBillContract.BillEntry.TABLE_RESULT_NAME,
                                SplitBillContract.BillEntry.COLUMN_RESULT_MONEY + "=0",
                                null);
                    }
                }
            } while (cursor.moveToNext());
        } else {

            if (cursor.getCount() <= 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(SplitBillContract.BillEntry.COLUMN_RESULT_HUMAN, humanName);
                contentValues.put(SplitBillContract.BillEntry.COLUMN_RESULT_MONEY, money);

                sqLiteDatabase.insert(SplitBillContract.BillEntry.TABLE_RESULT_NAME,
                        null,
                        contentValues);
            } else {
                cursor.moveToFirst();
                do {
                    comprobarHumano = cursor.getString(2);
                    if (comprobarHumano.equalsIgnoreCase(humanName)) {

                        double sumarDinero = money + cursor.getDouble(
                                cursor.getColumnIndex(
                                        SplitBillContract.BillEntry.COLUMN_RESULT_MONEY));

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(SplitBillContract.BillEntry.COLUMN_RESULT_MONEY, sumarDinero);

                        sqLiteDatabase.update(SplitBillContract.BillEntry.TABLE_RESULT_NAME,
                                contentValues,
                                "humanR = ?",
                                new String[]{humanName});
                    }
                } while (cursor.moveToNext());

            }
        }
        resultadoAdapter.cambiarResultadoCursor(getAllResultadoItems());
        cursor.close();
    }

    private void openDeleteDialog() {
        ItemDeleteDialog itemDeleteDialog = new ItemDeleteDialog();
        itemDeleteDialog.show(getSupportFragmentManager(), String.valueOf(R.string.eliminar_todo));
    }

    @Override
    public void recibirDeleteAll(Boolean isBorrar) {
        if (isBorrar) {
            sqLiteDatabase.delete(SplitBillContract.BillEntry.TABLE_NAME,
                    null,
                    null);
            billAdapter.cambiarCursor(getAllItems());

            sqLiteDatabase.delete(SplitBillContract.BillEntry.TABLE_RESULT_NAME,
                    null,
                    null);
            resultadoAdapter.cambiarResultadoCursor(getAllResultadoItems());
        }
    }


    private void eliminarObjeto(long tag) {
        SQLiteDatabase writableDatabase = splitBillDBHelper.getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("SELECT * FROM splitBill WHERE _id LIKE '" + tag + "'", null);
        double dinero = 0;
        String humano = "";

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                long comprobarTag = cursor.getLong(cursor.getColumnIndex(SplitBillContract.BillEntry._ID));

                if (comprobarTag == tag) {
                    dinero = cursor.getDouble(cursor.getColumnIndex(SplitBillContract.BillEntry.COLUMN_MONEY));
                    humano = cursor.getString(cursor.getColumnIndex(SplitBillContract.BillEntry.COLUMN_HUMAN));
                }
            } while (cursor.moveToNext());

            sqLiteDatabase.delete(SplitBillContract.BillEntry.TABLE_NAME,
                    SplitBillContract.BillEntry._ID + "=" + tag,
                    null);
            billAdapter.cambiarCursor(getAllItems());

            totalSumRv(humano, dinero, true);
        }
        cursor.close();
    }


    private void openAddDialog() {
        ItemCreationDialog itemCreationDialog = new ItemCreationDialog();
        itemCreationDialog.show(getSupportFragmentManager(), String.valueOf(R.string.crear_objeto));
    }

    @Override
    public void recibirAddInformacion(String objectName, String money, String humanName) {
        if (!objectName.trim().equalsIgnoreCase("")
                && !humanName.trim().equalsIgnoreCase("")
                && !money.trim().equalsIgnoreCase("")) {
            double moneyC = Double.parseDouble(money);

            ContentValues contentValues = new ContentValues();
            contentValues.put(SplitBillContract.BillEntry.COLUMN_NAME, objectName);
            contentValues.put(SplitBillContract.BillEntry.COLUMN_HUMAN, humanName);
            contentValues.put(SplitBillContract.BillEntry.COLUMN_MONEY, moneyC);

            sqLiteDatabase.insert(SplitBillContract.BillEntry.TABLE_NAME, null,
                    contentValues);
            billAdapter.cambiarCursor(getAllItems());

            totalSumRv(humanName, moneyC, false);
        } else {
            Toast.makeText(this, R.string.faltan_valores, Toast.LENGTH_LONG).show();
        }
    }

    private Cursor getAllItems() {
        return sqLiteDatabase.query(
                SplitBillContract.BillEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                SplitBillContract.BillEntry.COLUMN_TIMESTAMP + " DESC"
        );

    }

    private Cursor getAllResultadoItems() {
        return sqLiteDatabase.query(
                SplitBillContract.BillEntry.TABLE_RESULT_NAME,
                null,
                null,
                null,
                null,
                null,
                SplitBillContract.BillEntry.COLUMN_TIMESTAMP + " DESC"
        );

    }

}
