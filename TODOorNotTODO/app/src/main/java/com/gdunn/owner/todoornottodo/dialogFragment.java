package com.gdunn.owner.todoornottodo;

import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class dialogFragment extends DialogFragment
{
    private EditText mInput;
    private TextView mActionOk, mActionCancel;
    String captureText;
    int ListID;
    SQLiteDatabase db;
    DBManager manager;
    DBManager writeManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, container,false);
        manager = new DBManager(getActivity());
        db = manager.getReadableDatabase();
        manager = new DBManager(getActivity());
        writeManager = new DBManager(getActivity());
        db = writeManager.getWritableDatabase();
        writeManager = new DBManager(getActivity());
        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        mInput = view.findViewById(R.id.input);
        captureText = getArguments().getString("ListItemText");
        ListID= getArguments().getInt("ListItemID");
        mInput.setText(captureText);

        mActionCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String input = mInput.getText().toString();
                if(input.equals("")){
                    getDialog().dismiss();
                    Toast.makeText(getActivity(), "No changes made", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //update listitem
                    //get old version of listitem
                    ListItem oneItem = manager.GetListItemByItemID(ListID);
                    //change values
                    oneItem.setContent(input);
                    //update
                    writeManager.UpdateListItem(oneItem);
                    getDialog().dismiss();
                }
            }
        });


        return view;
    }

}
