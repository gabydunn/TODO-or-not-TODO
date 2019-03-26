package com.gdunn.owner.todoornottodo;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    SQLiteDatabase db;
    DBManager dbHelper;
    DBManager manager;
    List<ListName>  lists;
    int listId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        manager = new DBManager(this);
        db = manager.getReadableDatabase();
        dbHelper = new DBManager(getApplicationContext());
        //ListName testlist1 = new ListName("Shopping");
        //long listID1 = dbHelper.CreateList(testlist1);

        lists = dbHelper.GetLists();


    }
}
