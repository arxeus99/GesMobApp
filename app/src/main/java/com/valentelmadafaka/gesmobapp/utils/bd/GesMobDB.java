package com.valentelmadafaka.gesmobapp.utils.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.valentelmadafaka.gesmobapp.model.Alumno;
import com.valentelmadafaka.gesmobapp.model.Empresa;
import com.valentelmadafaka.gesmobapp.model.Mensaje;
import com.valentelmadafaka.gesmobapp.model.Observacion;
import com.valentelmadafaka.gesmobapp.model.Profesor;
import com.valentelmadafaka.gesmobapp.model.Tarea;

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
    public static final String TAB_ALUMNO = "alumno";
    public static final String ALUMNO_ID = "id";
    public static final String ALUMNO_NOMBRE = "nombre";
    public static final String ALUMNO_EMAIL = "email";
    public static final String ALUMNO_ID_EMPRESA = "idEmpresa";
    public static final String ALUMNO_DIRECCION = "direccion";
    public static final String ALUMNO_ID_PROFESOR = "idProfesor";
    public static final String CREATE_TABLE_ALUMNO = "create table " + TAB_ALUMNO +
            "("
            + ALUMNO_ID + " integer primary key, "
            + ALUMNO_NOMBRE + " text not null, "
            + ALUMNO_EMAIL + " text not null, "
            + ALUMNO_ID_EMPRESA + " text not null, "
            + ALUMNO_DIRECCION + " text not null, "
            + ALUMNO_ID_PROFESOR + " text not null)";
    public static final String TAB_EMPRESA = "empresa";
    public static final String EMPRESA_ID = "id";
    public static final String EMPRESA_NOMBRE = "nombre";
    public static final String EMPRESA_EMAIL = "email";
    public static final String EMPRESA_DIRECCION = "direccion";
    public static final String CREATE_TABLE_EMPRESA = "create table " + TAB_EMPRESA +
            "("
            + EMPRESA_ID + " integer primary key, "
            + EMPRESA_NOMBRE + " text not null, "
            + EMPRESA_EMAIL + " text not null, "
            + EMPRESA_DIRECCION + " text not null)";
    public static final String TAB_TAREA = "tarea";
    public static final String TAREA_ID = "id";
    public static final String TAREA_NOMBRE = "nombre";
    public static final String TAREA_DESCRIPCION = "descripcion";
    public static final String TAREA_FECHA = "fecha";
    public static final String TAREA_HORAS = "horas";
    public static final String TAREA_REALIZADA = "realizada";
    public static final String TAREA_ID_ALUMNO = "idAlumno";
    public static final String TAREA_ID_PROFESOR = "idProfesor";
    public static final String CREATE_TABLE_TAREA = "create table " + TAB_TAREA +
            "("
            + TAREA_ID + " integer primary key, "
            + TAREA_NOMBRE + " text not null, "
            + TAREA_DESCRIPCION + " text not null, "
            + TAREA_FECHA + " text not null, "
            + TAREA_HORAS + " integer not null, "
            + TAREA_REALIZADA + " integer default 0, "
            + TAREA_ID_ALUMNO + " text not null, "
            + TAREA_ID_PROFESOR + " text not null)";
    public static final String TAB_MENSAJE = "mensaje";
    public static final String MENSAJE_ID = "id";
    public static final String MENSAJE_CONTENIDO = "contenido";
    public static final String MENSAJE_ID_EMISOR = "idEmisor";
    public static final String MENSAJE_ID_RECEPTOR = "idReceptor";
    public static final String MENSAJE_LEIDO = "leido";
    public static final String CREATE_TABLE_MENSAJE = "create table " + TAB_MENSAJE +
            "("
            + MENSAJE_ID + " integer primary key, "
            + MENSAJE_CONTENIDO + " text not null, "
            + MENSAJE_ID_EMISOR + " text not null, "
            + MENSAJE_ID_RECEPTOR + " text not null, "
            + MENSAJE_LEIDO + " integer default 0)";
    public static final String TAB_OBSERVACION = "observacion";
    public static final String OBSERVACION_ID = "id";
    public static final String OBSERVACION_ID_EMISOR = "idEmisor";
    public static final String OBSERVACION_ID_TAREA = "idTarea";
    public static final String OBSERVACION_CONTENIDO = "contenido";
    public static final String CREATE_TABLE_OBSERVACION = "create table " + TAB_OBSERVACION +
            "("
            + OBSERVACION_ID + " integer primary key, "
            + OBSERVACION_ID_EMISOR + " text not null, "
            + OBSERVACION_ID_TAREA + " text not null, "
            + OBSERVACION_CONTENIDO + " text not null)";
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

    public Cursor obtenerProfesor(long id){
        Cursor cursor = bd.query(true, TAB_PROFESOR, new String[] {PROFESOR_ID, PROFESOR_NOMBRE, PROFESOR_EMAIL}, PROFESOR_ID + " = " + id, null, null, null, null, null);
        return cursor;
    }

    public long insertaAlumno(Alumno alumno){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ALUMNO_ID, alumno.getId());
        contentValues.put(ALUMNO_NOMBRE, alumno.getNombre());
        contentValues.put(ALUMNO_EMAIL, alumno.getEmail());
        contentValues.put(ALUMNO_ID_EMPRESA, alumno.getIdEmpresa());
        contentValues.put(ALUMNO_DIRECCION, alumno.getDireccion());
        contentValues.put(ALUMNO_ID_PROFESOR, alumno.getIdProfesor());
        return bd.insert(TAB_ALUMNO, null, contentValues);
    }

    public Cursor obtenerAlumno(long id){
        Cursor cursor = bd.query(true, TAB_ALUMNO, new String[]{ALUMNO_ID, ALUMNO_NOMBRE, ALUMNO_EMAIL, ALUMNO_ID_EMPRESA, ALUMNO_DIRECCION, ALUMNO_ID_PROFESOR}, ALUMNO_ID + " = " + id, null, null, null, null, null);
        return cursor;
    }

    public long insertaEmpresa(Empresa empresa){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPRESA_ID, empresa.getId());
        contentValues.put(EMPRESA_NOMBRE, empresa.getNombre());
        contentValues.put(EMPRESA_EMAIL, empresa.getEmail());
        contentValues.put(EMPRESA_DIRECCION, empresa.getDireccion());
        return bd.insert(TAB_EMPRESA, null, contentValues);
    }

    public Cursor obtenerEmpresa(long id){
        Cursor cursor = bd.query(true, TAB_EMPRESA, new String[]{EMPRESA_ID, EMPRESA_NOMBRE, EMPRESA_EMAIL, EMPRESA_DIRECCION}, EMPRESA_ID + " = " + id, null, null, null, null, null);
        return cursor;
    }

    public long insertaTarea(Tarea tarea){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAREA_ID, tarea.getId());
        contentValues.put(TAREA_NOMBRE, tarea.getNombre());
        contentValues.put(TAREA_DESCRIPCION, tarea.getDescripcion());
        contentValues.put(TAREA_FECHA, tarea.getFecha());
        contentValues.put(TAREA_HORAS, tarea.getHoras());
        if(tarea.isRealizada()){
            contentValues.put(TAREA_REALIZADA, 1);
        }else{
            contentValues.put(TAREA_REALIZADA, 0);
        }
        contentValues.put(TAREA_ID_ALUMNO, tarea.getAlumno().getId());
        contentValues.put(TAREA_ID_PROFESOR, tarea.getProfesor().getId());
        return bd.insert(TAB_TAREA, null, contentValues);
    }

    public Cursor obtenerMensaje(long id){
        Cursor cursor = bd.query(true, TAB_EMPRESA, new String[]{MENSAJE_ID, MENSAJE_CONTENIDO, MENSAJE_ID_EMISOR, MENSAJE_ID_RECEPTOR, MENSAJE_LEIDO}, MENSAJE_ID + " = " + id, null, null, null, null, null);
        return cursor;
    }

    public long insertaMensaje(Mensaje mensaje){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MENSAJE_ID, mensaje.getId());
        contentValues.put(MENSAJE_CONTENIDO, mensaje.getContenido());
        contentValues.put(MENSAJE_ID_EMISOR, mensaje.getEmisor().getId());
        if(mensaje.isLeido()){
            contentValues.put(MENSAJE_LEIDO, 1);
        }else{
            contentValues.put(MENSAJE_LEIDO, 0);
        }
        return bd.insert(TAB_MENSAJE, null, contentValues);
    }

    public Cursor obtenerObservacion(long id){
        Cursor cursor = bd.query(true, TAB_OBSERVACION, new String[]{OBSERVACION_ID, OBSERVACION_ID_EMISOR, OBSERVACION_ID_TAREA, OBSERVACION_CONTENIDO}, OBSERVACION_ID + " = " + id, null, null, null, null, null);
        return cursor;
    }

    public long insertaObservacion(Observacion observacion){
        ContentValues contentValues = new ContentValues();
        contentValues.put(OBSERVACION_ID, observacion.getId());
        contentValues.put(OBSERVACION_ID_EMISOR, observacion.getAutor().getId());
        contentValues.put(OBSERVACION_ID_TAREA, observacion.getTarea().getId());
        contentValues.put(OBSERVACION_CONTENIDO, observacion.getContenido());
        return bd.insert(TAB_OBSERVACION, null, contentValues);
    }





}