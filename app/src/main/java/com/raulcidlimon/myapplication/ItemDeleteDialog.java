package com.raulcidlimon.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class ItemDeleteDialog extends AppCompatDialogFragment {
    ItemDeleteDialogListener listener;
    boolean isBorrar = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.delete_dialog_layout, null);

        builder.setView(view)
                .setTitle(R.string.eliminar_todo)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.recibirDeleteAll(isBorrar = true);

            }
        });


        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ItemDeleteDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "falta implementar ItemCreationDialogListener");
        }
    }

    public interface ItemDeleteDialogListener {
        void recibirDeleteAll(Boolean isBorrar);
    }
}
