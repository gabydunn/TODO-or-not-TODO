package com.gdunn.owner.todoornottodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArchiveActivity extends AppCompatActivity {

    List<archiveItem> archiveList;
    private RecyclerView archvieRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    archiveRecyclerAdapter aAdapter;
    String url = "http://www.youcode.ca/Lab02Get.jsp?ALIAS=Gaby&PASSWORD=TooManyFragments";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);


        archvieRecycler = findViewById(R.id.archive_recycler);
        archvieRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        archvieRecycler.setLayoutManager(layoutManager);
        archiveList = new ArrayList<>();
        populateList();
        adapter = new archiveRecyclerAdapter(this, archiveList);
        archvieRecycler.setAdapter(adapter);
    }

    public void populateList()
    {
        RequestQueue que = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String[] requestData = response.split("\n\r");
                archiveItem newArchive = new archiveItem();

                int count = 0;
                boolean firstArchive = true;
                for (String string : requestData) {
                    if (count == 0 && !firstArchive) {
                        newArchive = new archiveItem();
                    }
                    switch (count) {
                        case 0: {
                            firstArchive = false;
                            newArchive.setPostedDate(string);
                            count++;
                            break;
                        }
                        case 1: {
                            newArchive.setListTitle(string);
                            count++;
                            break;
                        }
                        case 2: {
                            newArchive.setArchiveContent(string);
                            count++;
                            break;
                        }
                        case 3: {
                            newArchive.setCompleted(Integer.parseInt(string));
                            archiveList.add(newArchive);
                            count = 0;
                            break;
                        }
                    }
                }



            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error log here
                    }
                });
        que.add(stringRequest);
    }
}
