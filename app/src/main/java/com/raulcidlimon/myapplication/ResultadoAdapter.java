package com.raulcidlimon.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class ResultadoAdapter extends RecyclerView.Adapter<ResultadoAdapter.ResultadoViewHolder> {
    Context contextA;
    Cursor cursorA;

    public ResultadoAdapter(Context context, Cursor cursor) {
        contextA = context;
        cursorA = cursor;
    }

    public class ResultadoViewHolder extends RecyclerView.ViewHolder {
        public TextView tvResultadoHumano;
        public TextView tvResultadoDinero;

        public ResultadoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvResultadoHumano = itemView.findViewById(R.id.tvResultadoHumano);
            tvResultadoDinero = itemView.findViewById(R.id.tvResultadoDinero);
        }
    }

    @NonNull
    @Override
    public ResultadoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(contextA);
        View view = layoutInflater.inflate(R.layout.resultado_item, viewGroup, false);

        return new ResultadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultadoViewHolder resultadoViewHolder, int i) {
        if (!cursorA.moveToPosition(i)) {
            return;
        }

        double itemPrice = cursorA.getDouble(cursorA.getColumnIndex(SplitBillContract.BillEntry
                .COLUMN_RESULT_MONEY));
        String humanName = cursorA.getString(cursorA.getColumnIndex(SplitBillContract.BillEntry
                .COLUMN_RESULT_HUMAN));

        resultadoViewHolder.tvResultadoDinero.setText(String.valueOf(itemPrice));
        resultadoViewHolder.tvResultadoHumano.setText(humanName);
    }

    @Override
    public int getItemCount() {
        return cursorA.getCount();
    }

    public void cambiarResultadoCursor(Cursor newCursor) {
        if (cursorA != null) {
            cursorA.close();
        }

        cursorA = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
