package com.gdunn.owner.todoornottodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper
{
    static final String DB_Name = "todo.db";
    static final int DB_VERSION = 2;

    //table names

    private static String TABLE_LISTITEM = "ListItem";
    private static String TABLE_LISTNAME = "ListName";

    //Common column names
    private static String PK_ID = "id";

    //List item table
    private static String LISTITEM_PKID = "ListItemID";
    private static String LISTITEM_CONTENT = "content";
    private static String LISTITEM_STATUS = "status";
    private static String LISTITEM_FK = "ListId";
    private static String LISTITEM_DATE = "createdDate";

    //List Name table
     private static String LISTNAME_PKID = "ListID";
     private static String LISTNAME_NAME = "Name";

     //ListName table Create
    private static final String CREATETABLE_LISTNAME = "CREATE TABLE " + TABLE_LISTNAME + "("+LISTNAME_PKID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +LISTNAME_NAME+" TEXT)";
    //ListItem table create
    protected static final String CREATETABLE_LISTITEM = "CREATE TABLE " + TABLE_LISTITEM + "("+ LISTITEM_PKID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + LISTITEM_CONTENT+" TEXT, " +LISTITEM_STATUS+ " INTEGER, "+ LISTITEM_FK+ " INTEGER, " + LISTITEM_DATE+ " TEXT)";
    public DBManager(Context context)
    {
        super(context,DB_Name,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE_LISTNAME);
        db.execSQL(CREATETABLE_LISTITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL("Drop table if exists " + TABLE_LISTITEM);
     db.execSQL("Drop table if exists " + TABLE_LISTNAME);

     onCreate(db);
    }
 //-----------list item table methods-----------

        //public long CreateList
    public long CreateListItem(ListItem listitem)
    {
        SQLiteDatabase db = getWritableDatabase();
        //used to store a set of values that the ContentResolver can process.
        ContentValues values = new ContentValues();
        values.put(LISTITEM_CONTENT, listitem.getContent());
        values.put(LISTITEM_STATUS, listitem.getStatus());
        values.put(LISTITEM_FK, listitem.getListIdFK());
        values.put(LISTITEM_DATE, listitem.getCreatedDate().toString());

        long itemID = db.insert(TABLE_LISTITEM,null,values);

        return itemID;
    }
    //public List<ListItem> GetListItemsByID
    public List<ListItem> GetListItemsByID(int listid)
    {
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "WHERE " + LISTITEM_FK + " = " + listid;
        List<ListItem> itemlist = new ArrayList<ListItem>();
        Cursor cursor = db.query(TABLE_LISTITEM,null, whereClause, null,null,null, LISTITEM_CONTENT + " DESC");
        while(cursor.moveToNext())
        {
            ListItem cItem = new ListItem();
            cItem.setId(cursor.getInt((cursor.getColumnIndex(LISTITEM_PKID))));
            cItem.setContent(cursor.getString((cursor.getColumnIndex(LISTITEM_CONTENT))));
            cItem.setStatus(cursor.getInt((cursor.getColumnIndex(LISTITEM_STATUS))));
            cItem.setListIdFK(cursor.getInt((cursor.getColumnIndex(LISTITEM_FK))));
            itemlist.add(cItem);
        }
        cursor.close();
        return itemlist;
    }
    //Update ListItem
    public int UpdateListItem(ListItem item)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LISTITEM_CONTENT, item.getContent());
        values.put(LISTITEM_STATUS, item.getStatus());
        values.put(LISTITEM_FK, item.getListIdFK());
        values.put(LISTITEM_DATE, item.getCreatedDate().toString());

        return db.update(TABLE_LISTITEM, values, LISTITEM_PKID + " =?",
                new String[] {String.valueOf(item.getId())});
    }

    //Delete ListItem
    public void DeleteListItem(ListItem item)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_LISTITEM, LISTITEM_PKID+ " =?", new String[] {String.valueOf(item.getId())});
        db.close();
    }
    //-----------list table methods-----------
    //public long CreateList
    public long CreateList(ListName list)
    {
        SQLiteDatabase db = getWritableDatabase();
        //used to store a set of values that the ContentResolver can process.
        ContentValues values = new ContentValues();
        values.put(LISTNAME_NAME, list.getName());

        long listID = db.insert(TABLE_LISTNAME,null,values);

        return listID;
    }
    //Update ListName
    public int UpdateListName(ListName list)
    {
        SQLiteDatabase db = getWritableDatabase();
        //used to store a set of values that the ContentResolver can process.
        ContentValues values = new ContentValues();
        values.put(LISTNAME_NAME, list.getName());

        int listID = db.update(TABLE_LISTNAME, values, LISTNAME_PKID + " =?", new String[] {String.valueOf(list.getId())} );

        return listID;
    }

    //Get ListNames
    public List<ListName> GetLists()
    {
        SQLiteDatabase db = getReadableDatabase();
        List<ListName> listnames = new ArrayList<>();
        Cursor cursor = db.query(TABLE_LISTITEM,null, null, null,null,null, LISTNAME_NAME + " DESC");
        while (cursor.moveToNext())
        {
            ListName newList = new ListName();
            newList.setId(cursor.getInt((cursor.getColumnIndex(LISTNAME_PKID))));
            newList.setListName(cursor.getString((cursor.getColumnIndex((LISTNAME_NAME)))));
            listnames.add(newList);
        }
        cursor.close();
        return listnames;
    }
}
