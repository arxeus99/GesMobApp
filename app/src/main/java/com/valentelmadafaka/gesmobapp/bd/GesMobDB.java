package com.valentelmadafaka.gesmobapp.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.valentelmadafaka.gesmobapp.model.Profesor;

public class GesMobDB {

    public static final int VERSION_DB = 11;
    public static final String NOMBRE_DB = "gesmob.db";
    public static final String TAB_PROFESOR = "profesor";
    public static final String PROFESOR_ID = "id";
    public static final String PROFESOR_NOMBRE = "nombre";
    public static final String PROFESOR_EMAIL = "email";
    public static final String CREATE_TABLE_PROFESOR = "create table " + TAB_PROFESOR +
            "("
            + PROFESOR_ID + " integer primary key, "
            + PROFESOR_NOMBRE + " text not null, "
            + PROFESOR_EMAIL + " text not null)";
    public static final String TAB_ALUMNOS = "alumnos";
    public static final String TAB_EMPRESAS = "empresas";

    private Context context;
    private HelperDB helperDB;
    private SQLiteDatabase bd;

    public GesMobDB(Context context){
        this.context = context;
        helperDB = new HelperDB(context);
    }

    public  GesMobDB open() throws SQLException{
        bd = helperDB.getWritableDatabase();
        return this;
    }

    public void close(){ helperDB.close(); }

    public SQLiteDatabase getBd(){ return this.bd; }

    public long insertaProfesor(Profesor profesor){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFESOR_ID, profesor.getId());
        contentValues.put(PROFESOR_NOMBRE, profesor.getNombre());
        contentValues.put(PROFESOR_EMAIL, profesor.getEmail());
        return bd.insert(TAB_PROFESOR, null, contentValues);
    }

}
