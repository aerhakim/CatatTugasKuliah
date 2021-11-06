package io.github.aerhakim.catattugaskuliah.database;

import android.provider.BaseColumns;

public class Data {
    public static final String DB_NAME = "io.github.aerhakim.catattugaskuliah.db";
    public static final int DB_VERSION = 1;

    public class DataEntry implements BaseColumns {
        public static final String TABLE = "data";

        public static final String COL_TUGAS_TITLE = "title";
    }
}