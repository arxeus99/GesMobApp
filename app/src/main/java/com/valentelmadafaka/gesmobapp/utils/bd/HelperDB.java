package com.valentelmadafaka.gesmobapp.utils.bd;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.CREATE_TABLE_ALUMNO;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.CREATE_TABLE_EMPRESA;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.CREATE_TABLE_MENSAJE;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.CREATE_TABLE_OBSERVACION;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.CREATE_TABLE_SEMANA;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.CREATE_TABLE_TAREA;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.CREATE_TABLE_USUARIO;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.NOMBRE_DB;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.TAB_ALUMNO;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.TAB_EMPRESA;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.TAB_MENSAJE;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.TAB_OBSERVACION;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.TAB_SEMANAS;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.TAB_TAREA;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.TAB_USUARIO;
import static com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB.VERSION_DB;

public class HelperDB extends SQLiteOpenHelper {

    HelperDB(Context con){ super(con, NOMBRE_DB, null, VERSION_DB);}

    @Override
    public void onCreate(SQLiteDatabase db){
        try{
            db.execSQL(CREATE_TABLE_USUARIO);
            db.execSQL(CREATE_TABLE_ALUMNO);
            db.execSQL(CREATE_TABLE_EMPRESA);
            db.execSQL(CREATE_TABLE_TAREA);
            db.execSQL(CREATE_TABLE_MENSAJE);
            db.execSQL(CREATE_TABLE_OBSERVACION);
            db.execSQL(CREATE_TABLE_SEMANA);
        }catch (SQLException e){
            System.out.printf(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(GesMobDB.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TAB_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TAB_ALUMNO);
        db.execSQL("DROP TABLE IF EXISTS " + TAB_EMPRESA);
        db.execSQL("DROP TABLE IF EXISTS " + TAB_TAREA);
        db.execSQL("DROP TABLE IF EXISTS " + TAB_MENSAJE);
        db.execSQL("DROP TABLE IF EXISTS " + TAB_OBSERVACION);
        db.execSQL("DROP TABLE IF EXISTS " + TAB_SEMANAS);
        onCreate(db);
    }
}
