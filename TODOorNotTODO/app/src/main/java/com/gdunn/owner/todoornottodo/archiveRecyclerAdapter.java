package com.gdunn.owner.todoornottodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;

public class archiveRecyclerAdapter extends RecyclerView.Adapter<archiveRecyclerAdapter.NewViewHolder>{

    Context mContext;
    List<archiveItem> archiveList;
    //adapter constructor
    public archiveRecyclerAdapter(Context context, List<archiveItem> archives)
    {
        mContext = context;
        archiveList = archives;
    }

    //viewholder constructor
    public class NewViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout parent;
        TextView archiveDate;
        TextView archiveStatus;
        TextView archiveListName;
        TextView archiveListContent;

        public NewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            parent = itemView.findViewById(R.id.archive_parent);
            archiveDate = itemView.findViewById(R.id.archive_date);
            archiveStatus = itemView.findViewById(R.id.archive_status);
            archiveListName = itemView.findViewById(R.id.archive_listName);
            archiveListContent = itemView.findViewById(R.id.archive_listContent);

        }
        public void bindList(archiveItem oneItem)
        {
            archiveDate.setText(oneItem.getPostedDate());
            if(oneItem.getCompleted() == 1)
            {
                archiveStatus.setText("Completed");
            }
            else
            {
                archiveStatus.setText("Incomplete");
            }
            archiveListName.setText(oneItem.getListTitle());
            archiveListContent.setText(oneItem.getArchiveContent());

        }
    }
    //Create view holder
    @Override
    public archiveRecyclerAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.archive_recycler_item, parent, false);
        NewViewHolder viewHolder =  new NewViewHolder(view);
        return viewHolder;
    }
    //Bind view holder to view
    @Override
    public void onBindViewHolder(archiveRecyclerAdapter.NewViewHolder holder, int position) {
        holder.bindList(archiveList.get(position));

    }

    //Archive list count
    @Override
    public int getItemCount() {
        return archiveList.size();
    }
}
