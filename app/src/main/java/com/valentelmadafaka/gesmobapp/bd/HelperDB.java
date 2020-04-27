package com.valentelmadafaka.gesmobapp.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.valentelmadafaka.gesmobapp.bd.GesMobDB.CREATE_TABLE_PROFESOR;
import static com.valentelmadafaka.gesmobapp.bd.GesMobDB.NOMBRE_DB;
import static com.valentelmadafaka.gesmobapp.bd.GesMobDB.TAB_PROFESOR;
import static com.valentelmadafaka.gesmobapp.bd.GesMobDB.VERSION_DB;

public class HelperDB extends SQLiteOpenHelper {

    HelperDB(Context con){ super(con, NOMBRE_DB, null, VERSION_DB);}

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_PROFESOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(GesMobDB.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TAB_PROFESOR);
        onCreate(db);
    }
}
