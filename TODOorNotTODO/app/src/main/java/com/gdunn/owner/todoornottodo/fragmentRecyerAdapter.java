package com.gdunn.owner.todoornottodo;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public class fragmentRecyerAdapter  extends RecyclerView.Adapter<fragmentRecyerAdapter.NewViewHolder> {
    Context mContext;
    List<ListItem> listitems;
    SQLiteDatabase db;
    DBManager writeManager;
    DBManager readManager;
    static RequestQueue que;
    public fragmentRecyerAdapter(Context context, List<ListItem> thislist)
    {
        mContext = context;
        listitems = thislist;
    }

    //-------------------------view holder constructor--------------------------//
    public class NewViewHolder extends RecyclerView.ViewHolder
    {
        TextView listitemtext;
        LinearLayout parent;
        Button archiveButton;
        Button deleteButton;
        CheckBox active;
        //whats in the view
        public NewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            parent = itemView.findViewById(R.id.mostpart_Layout);
            listitemtext = itemView.findViewById(R.id.textview_listitem);
            archiveButton = itemView.findViewById(R.id.listitem_archive);
            deleteButton =itemView.findViewById(R.id.listitem_delete);
            active = itemView.findViewById(R.id.listitem_checkbox);
        }
        //set values
        public void bindList(ListItem oneitem)
        {
            listitemtext.setText(oneitem.getContent());
            if(oneitem.getStatus() == 1)
            {
                active.setChecked(true);
            }
            else
            {
                active.setChecked(false);
            }
        }
    }
//-------------------general constructors----------------------//
    @Override
    public int getItemCount() {
        return listitems.size();
    }
//create view (inflate view)
    @Override
    public fragmentRecyerAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._listitemfragment, parent,false);
        NewViewHolder viewHolder = new NewViewHolder(view);

        return viewHolder;
    }

    //bind items into view
    //set events for items
    @Override
    public void onBindViewHolder(final fragmentRecyerAdapter.NewViewHolder holder, final int position) {
        holder.bindList(listitems.get(position));
        //long click the card
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                //get an instance of appcompat (for the fragment managers methods)
                AppCompatActivity activity = (AppCompatActivity)  v.getContext();
                dialogFragment dialog = new dialogFragment();
                Bundle args = new Bundle();
                String itemtext =listitems.get(position).getContent();
                int itemID = listitems.get(position).getId();
                args.putString("ListItemText", itemtext);
                args.putInt("ListItemID",itemID );
                dialog.setArguments(args);
                dialog.show(activity.getFragmentManager(), "MyCustomDialog");
                notifyDataSetChanged();
                return true;
            }
        });
        //on click delete
        holder.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                delete(position);
                notifyDataSetChanged();
            }
        });
        //on click for completed
        holder.active.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(holder.active.isChecked())
                {
                    updateStatus(position, 1);
                }
                else
                {
                    updateStatus(position, 0);
                }

            }
        });
        //on click archive
        holder.archiveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                archiveItem(position);
                delete(position);
                notifyDataSetChanged();
            }
        });

    }


//----------------------------List item CRUD----------------------------------//
    //delete list items
    public void delete(int position)
    {
        ListItem deleteitem = listitems.get(position);
        writeManager =new DBManager(mContext);
        db = writeManager.getWritableDatabase();
        writeManager = new DBManager(mContext.getApplicationContext());
        writeManager.DeleteListItem(deleteitem);
        listitems.remove(position);
        this.notifyItemRemoved(position);
    }
    //update list items
    public void updateStatus(int position, int newStatus)
    {
        final ListItem updateItem = listitems.get(position);
        writeManager =new DBManager(mContext);
        db = writeManager.getWritableDatabase();
        writeManager = new DBManager(mContext.getApplicationContext());
        updateItem.setStatus(newStatus);
        writeManager.UpdateListItem(updateItem);
    }
    //archive list item
    public void archiveItem(int position)
    {
        que = Volley.newRequestQueue(mContext);
        final ListItem archiveItem = listitems.get(position);
        readManager =new DBManager(mContext);
        db = readManager.getReadableDatabase();
        readManager = new DBManager(mContext.getApplicationContext());
        final ListName listName= readManager.GetListNameByItemID(archiveItem.getListIdFK());
        String url = "http://www.youcode.ca/Lab02Post.jsp";
        final String status =  String.valueOf(archiveItem.getStatus())+"'|'";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mContext, "Posted!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "shit "+ error, Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", "failed");
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params= new HashMap<>();
                params.put("LIST_TITLE", listName.getName()+"|");
                params.put("CONTENT", archiveItem.getContent()+"|");
                params.put("COMPLETED_FLAG", status);
                //change this to prefs name
                params.put("ALIAS", "Gaby4");
                params.put("PASSWORD", "TooManyFragments4");
                params.put("CREATED_DATE", archiveItem.getCreatedDate()+"|");
                return params;
            }
        };
        que.add(postRequest);
    }
}
