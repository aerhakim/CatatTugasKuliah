package io.github.aerhakim.catattugaskuliah.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

import io.github.aerhakim.catattugaskuliah.R;
import io.github.aerhakim.catattugaskuliah.database.Data;
import io.github.aerhakim.catattugaskuliah.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DatabaseHelper databaseHelper;
    private ListView TugasList;
    private ArrayAdapter<String> arrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        TugasList = (ListView) findViewById(R.id.lv_tugas);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_tugas:
                final EditText tugasEdit = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Tambah Tugas Baru").setMessage("Tambahkan Informasi Tugas Mata Kuliah Baru").setView(tugasEdit)
                        .setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String tugas = String.valueOf(tugasEdit.getText());
                                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(Data.DataEntry.COL_TUGAS_TITLE, tugas);
                                db.insertWithOnConflict(Data.DataEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Batal", null).create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView tugasTextView = (TextView) parent.findViewById(R.id.tv_tugas);
        String tugas = String.valueOf(tugasTextView.getText());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(Data.DataEntry.TABLE, Data.DataEntry.COL_TUGAS_TITLE + " = ?", new String[]{tugas});
        db.close();
        updateUI();
    }

    private void updateUI() {
        ArrayList<String> tugasList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(Data.DataEntry.TABLE,
                new String[]{Data.DataEntry._ID, Data.DataEntry.COL_TUGAS_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(Data.DataEntry.COL_TUGAS_TITLE);
            tugasList.add(cursor.getString(idx));
        }

        if (arrAdapter == null) {
            arrAdapter = new ArrayAdapter<>(this, R.layout.item_data, R.id.tv_tugas, tugasList);
            TugasList.setAdapter(arrAdapter);
        } else {
            arrAdapter.clear();
            arrAdapter.addAll(tugasList);
            arrAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }
}
