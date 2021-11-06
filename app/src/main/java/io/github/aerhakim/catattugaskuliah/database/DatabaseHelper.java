package io.github.aerhakim.catattugaskuliah.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, Data.DB_NAME, null, Data.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + Data.DataEntry.TABLE + " ( " +
                Data.DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Data.DataEntry.COL_TUGAS_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Data.DataEntry.TABLE);
        onCreate(db);
    }
}
