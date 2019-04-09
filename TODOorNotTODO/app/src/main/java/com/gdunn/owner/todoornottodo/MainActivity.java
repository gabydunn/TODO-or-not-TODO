package com.gdunn.owner.todoornottodo;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
private RecyclerView recyclerView;
private recyclerAdapter mAdapter;
private RecyclerView.LayoutManager layoutManager;
private Button createListNameButton;
private EditText newListName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.listname_recycler);
        //will help improve performance
        recyclerView.setHasFixedSize(true);
        //layout manager
        //positions items in recycler
        //linearLayout will give scrollable list type view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //set adapter and provide constructor components
        mAdapter = new recyclerAdapter(getApplicationContext(), lists);
        recyclerView.setAdapter(mAdapter);
        newListName =   findViewById(R.id.EditText_NewListItem);

        createListNameButton = findViewById(R.id.button_newList);
        createListNameButton.setOnClickListener(this);





    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_newList:
            {
                String newlist = newListName.getText().toString();
                if(newlist.isEmpty())
                {
                    Toast.makeText(this, "Enter a new list name", Toast.LENGTH_LONG).show();
                }
                else{
                    ListName newcard = new ListName(newlist);
                    long newcardid = manager.CreateList(newcard);
                    refreshAdapter();
                    newListName.setText("");
                    Toast.makeText(this, newlist+ " added!", Toast.LENGTH_LONG).show();
                }

                break;
            }
        }
    }
    public void refreshAdapter()
    {
        lists.clear();
        lists = manager.GetLists();
        mAdapter = new recyclerAdapter(getApplicationContext(), lists);
        recyclerView.setAdapter(mAdapter);
    }


}
