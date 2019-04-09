package com.gdunn.owner.todoornottodo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class fragement_list_1 extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private fragmentRecyerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button addButton;
    private EditText addInput;
    private List<ListItem> itemList;
    private SQLiteDatabase db;
    private DBManager dbManager;
    private DBManager writeManager;
    private int listID;
    private ImageView closebutton;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        dbManager = new DBManager(getActivity());
        writeManager = new DBManager(getActivity());
        db = dbManager.getReadableDatabase();
        db = writeManager.getWritableDatabase();
        Bundle bundle = this.getArguments();
        listID = bundle.getInt("LISTNAMEFK");
        itemList = dbManager.GetListItemsByID(listID);
        View layoutview = inflater.inflate(R.layout.fragment_listitem, container,false);
        recyclerView = layoutview.findViewById(R.id.recycler_listitem);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new fragmentRecyerAdapter(getActivity(),itemList);
        recyclerView.setAdapter(adapter);
        addButton = layoutview.findViewById(R.id.button_additem);
        addInput = layoutview.findViewById(R.id.edittext_listitem);
        addButton.setOnClickListener(this);
        closebutton = layoutview.findViewById(R.id.closefragment);
        closebutton.setOnClickListener(this);
        return layoutview;
    }
    public fragement_list_1(){}
    public void refreshAdapter(int id)
    {
        itemList.clear();
        itemList = dbManager.GetListItemsByID(id);
        adapter = new fragmentRecyerAdapter(getActivity(), itemList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_additem:
            {
                String listitemname = addInput.getText().toString();
                if(listitemname.isEmpty())
                {
                    Toast.makeText(getContext(), "Enter an item name", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd");
                    String today = mdformat.format(calendar.getTime());

                    ListItem newItem = new ListItem();
                    newItem.setListIdFK(listID);
                    newItem.setContent(listitemname);
                    newItem.setStatus(0);
                    newItem.setCreatedDate(today);
                    addInput.setText("");

                    long newcarditem = writeManager.CreateListItem(newItem);
                    refreshAdapter(listID);

                }

                break;
            }
            case R.id.closefragment:
            {
                Toast.makeText(getActivity(), "closed",Toast.LENGTH_LONG).show();
                AppCompatActivity activity = (AppCompatActivity)getActivity();
                activity.getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;
            }
        }
    }

    @Override
    public void onResume() {
        refreshAdapter(listID);
        super.onResume();
    }
}
