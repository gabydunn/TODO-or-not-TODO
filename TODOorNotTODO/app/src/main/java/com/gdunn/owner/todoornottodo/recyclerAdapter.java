package com.gdunn.owner.todoornottodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<ListName> mLists;

    public recyclerAdapter(Context context, List<ListName> fullList)
    {
        mContext = context;
        mLists = fullList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView lastnameText;
        LinearLayout parerntLayout;
        private Context mContext;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
            parerntLayout = itemView.findViewById(R.id.listitem_layout);
            lastnameText = itemView.findViewById(R.id.info_text);
        }
        public void bindList(ListName fullList){
            lastnameText.setText(fullList.getName());
        }
    }
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(recyclerAdapter.MyViewHolder holder, final int position) {
        holder.bindList(mLists.get(position));
        holder.parerntLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mLists.get(position).name, Toast.LENGTH_LONG).show();
                int listID = mLists.get(position).id;
                Intent intent = new Intent(mContext, someclass.class);
                intent.putExtra("LISTNAMEFK", listID);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }
}
