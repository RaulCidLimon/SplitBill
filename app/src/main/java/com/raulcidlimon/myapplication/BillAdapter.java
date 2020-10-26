package com.raulcidlimon.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    Context contextA;
    Cursor cursorA;

    public BillAdapter(Context context, Cursor cursor) {
        contextA = context;
        cursorA = cursor;
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBillItemName;
        public TextView tvBillHumanName;
        public TextView tvBillItemPrice;


        public BillViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBillItemName = itemView.findViewById(R.id.tvBillItemName);
            tvBillHumanName = itemView.findViewById(R.id.tvBillHumanName);
            tvBillItemPrice = itemView.findViewById(R.id.tvBillItemPrice);
        }
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(contextA);
        View view = layoutInflater.inflate(R.layout.bill_item, viewGroup, false);

        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder billViewHolder, int i) {
        if (!cursorA.moveToPosition(i)) {
            return;
        }

        String itemName = cursorA.getString(cursorA.getColumnIndex(SplitBillContract.BillEntry
                .COLUMN_NAME));
        double itemPrice = cursorA.getDouble(cursorA.getColumnIndex(SplitBillContract.BillEntry
                .COLUMN_MONEY));
        String humanName = cursorA.getString(cursorA.getColumnIndex(SplitBillContract.BillEntry
                .COLUMN_HUMAN));
        long id = cursorA.getLong(cursorA.getColumnIndex(SplitBillContract.BillEntry._ID));

        billViewHolder.tvBillItemName.setText(itemName);
        billViewHolder.tvBillItemPrice.setText(String.valueOf(itemPrice));
        billViewHolder.tvBillHumanName.setText(humanName);
        billViewHolder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return cursorA.getCount();
    }

    public void cambiarCursor(Cursor newCursor) {
        if (cursorA != null) {
            cursorA.close();
        }

        cursorA = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
