package com.example.laptop.icraudi;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Audi.db";
    public static final String TABLE_NAME = "cars_table";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String TABLE_GEAR="additional_gear";
    public static final String COL_GID="ID";
    public static final String COL_GNAME="GEAR TYPE";
    public SQLiteDatabase _dbDatabase = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT)");
        db.execSQL("create table " +TABLE_GEAR+"("+COL_GID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_GNAME+" TEXT)");
    }

    public void close()
    {
        _dbDatabase.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void insertCars (String name)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME,name);
        db.insert(TABLE_NAME,null,cv);
        db.close();

    }
    public void insertAdditionalGear (String name)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_GNAME,name);
        db.insert(TABLE_GEAR,null,cv);
        db.close();

    }

   public ArrayList<String> getAdditionalGears()
   {
       ArrayList<String> list=new ArrayList<String>();
       SQLiteDatabase db =this.getReadableDatabase();
       Cursor result = db.rawQuery("Select * from "+TABLE_GEAR,null);
       if (result.moveToFirst()) {
           do {
               list.add(result.getString(1));
           } while (result.moveToNext());
       }
       result.close();
       db.close();
       return  list;
   }

    public List<String> getCarsData()
    {
        List<String> al=new ArrayList<String>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor result = db.rawQuery("Select * from "+TABLE_NAME,null);

        if (result.moveToFirst()) {
            do {
                al.add(result.getString(1));
            } while (result.moveToNext());
        }
        result.close();
        db.close();
        return  al;
    }
}
