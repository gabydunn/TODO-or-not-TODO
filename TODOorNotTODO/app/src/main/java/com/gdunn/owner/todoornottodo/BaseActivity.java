package com.gdunn.owner.todoornottodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

public class BaseActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    SQLiteDatabase db;
    DBManager manager;
    DBManager writeManager;
    List<ListName>  lists;
    int listId;
    PreferenceManager prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        manager = new DBManager(this);
        db = manager.getReadableDatabase();
        manager = new DBManager(getApplicationContext());
        writeManager = new DBManager(this);
        db = writeManager.getWritableDatabase();
        writeManager = new DBManager(getApplicationContext());

        lists = manager.GetLists();
        //prefs = (PreferenceManager) PreferenceManager.getDefaultSharedPreferences(this);



    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings: {
                Intent intent = new Intent(this, prefActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_archive:{
                Intent intent = new Intent(this, ArchiveActivity                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }

}
