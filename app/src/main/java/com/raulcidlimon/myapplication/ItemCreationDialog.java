package com.raulcidlimon.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ItemCreationDialog extends AppCompatDialogFragment {
    private EditText etDialogOName;
    private EditText etDialogMoney;
    private EditText etDialogHName;
    ItemCreationDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_layout, null);

        builder.setView(view)
                .setTitle(R.string.crear_objeto)
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    String objectName = etDialogOName.getText().toString().toUpperCase();
                    String money = etDialogMoney.getText().toString();
                    String humanName = etDialogHName.getText().toString().toUpperCase();
                    listener.recibirInformacion(objectName, money, humanName);

            }
        });

        etDialogOName = view.findViewById(R.id.etDialogOName);
        etDialogHName = view.findViewById(R.id.etDialogHName);
        etDialogMoney = view.findViewById(R.id.etDialogMoney);

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ItemCreationDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "falta implementar ItemCreationDialogListener");
        }
    }

    public interface ItemCreationDialogListener {
        void recibirInformacion(String objectName, String money, String humanName);
    }
}
