package com.raulcidlimon.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
        View view = layoutInflater.inflate(R.layout.add_dialog_layout, null);

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
                listener.recibirAddInformacion(objectName, money, humanName);

            }
        });

        etDialogOName = view.findViewById(R.id.etDialogOName);
        etDialogHName = view.findViewById(R.id.etDialogHName);
        etDialogMoney = view.findViewById(R.id.etDialogMoney);
        cambiarTexto();

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
        void recibirAddInformacion(String objectName, String money, String humanName);
    }

    private void cambiarTexto() {
        etDialogMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("Main", "e");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("Main", "On text changed");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1 && s.toString().equalsIgnoreCase("0")) {
                    s.clear();
                } else if (s.toString().length() == 1 && s.toString().equalsIgnoreCase(".")) {
                    etDialogMoney.setText("0.");
                    etDialogMoney.setSelection(2);
                }
                Log.d("Main", "e");
            }
        });

    }

}
