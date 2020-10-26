package com.raulcidlimon.myapplication;

import android.provider.BaseColumns;

class SplitBillContract {

    private SplitBillContract() {
    }

    public static final class BillEntry implements BaseColumns {
        public static final String TABLE_NAME = "splitBill";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MONEY = "money";
        public static final String COLUMN_HUMAN = "human";
        public static final String COLUMN_TIMESTAMP = "timestamp";

        public static final String TABLE_RESULT_NAME = "resultTable";
        public static final String COLUMN_RESULT_MONEY = "moneyR";
        public static final String COLUMN_RESULT_HUMAN = "humanR";
        public static final String COLUMN_RESULT_TIMESTAMP = "timestamp";
    }
}
