package com.gdunn.owner.todoornottodo;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {
    SQLiteDatabase db;
    DBManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        manager = new DBManager(this);
        db = manager.getReadableDatabase();
    }
}
