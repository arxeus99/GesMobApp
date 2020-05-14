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
import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.model.Usuario;

public class GesMobDB {

    public static final int VERSION_DB = 11;
    public static final String NOMBRE_DB = "gesmob.db";
    public static final String TAB_USUARIO = "usuario";
    public static final String USUARIO_ID = "id";
    public static final String USUARIO_NOMBRE = "nombre";
    public static final String USUARIO_EMAIL = "email";
    public static final String USUARIO_TIPO = "tipo";
    public static final String CREATE_TABLE_USUARIO = "create table " + TAB_USUARIO +
            "("
            + USUARIO_ID + " integer primary key, "
            + USUARIO_NOMBRE + " text not null, "
            + USUARIO_EMAIL + " text not null, "
            + USUARIO_TIPO + " text not null)";
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
    public static final String ALUMNO_ID_EMPRESA = "idEmpresa";
    public static final String ALUMNO_DIRECCION = "direccion";
    public static final String ALUMNO_ID_PROFESOR = "idProfesor";
    public static final String ALUMNO_SEMANAS = "semanas";
    public static final String CREATE_TABLE_ALUMNO = "create table " + TAB_ALUMNO +
            "("
            + ALUMNO_ID + " integer primary key, "
            + ALUMNO_ID_EMPRESA + " text not null, "
            + ALUMNO_DIRECCION + " text not null, "
            + ALUMNO_ID_PROFESOR + " text not null,"
            + ALUMNO_SEMANAS + " integer not null)";
    public static final String TAB_EMPRESA = "empresa";
    public static final String EMPRESA_ID = "id";
    public static final String EMPRESA_NOMBRE = "nombre";
    public static final String EMPRESA_EMAIL = "email";
    public static final String EMPRESA_DIRECCION = "direccion";
    public static final String EMPRESA_WEB = "web";
    public static final String EMPRESA_TELEFONO = "telefono";
    public static final String CREATE_TABLE_EMPRESA = "create table " + TAB_EMPRESA +
            "("
            + EMPRESA_ID + " integer primary key, "
            + EMPRESA_NOMBRE + " text not null, "
            + EMPRESA_EMAIL + " text not null, "
            + EMPRESA_DIRECCION + " text not null, "
            + EMPRESA_WEB + " text not null, "
            + EMPRESA_TELEFONO + " text not null)";
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
    public static final String TAB_SEMANAS = "semanas";
    public static final String SEMANA_ID = "id";
    public static final String SEMANA_INICIO = "inicio";
    public static final String SEMANA_FINAL = "final";
    public static final String SEMANA_HORAS = "horas";
    public static final String CREATE_TABLE_SEMANA = "create table " + TAB_SEMANAS +
            "("
            + SEMANA_ID + " integer primary key, "
            + SEMANA_INICIO + " text not null, "
            + SEMANA_FINAL + " text not null, "
            + SEMANA_HORAS + " integer not null)";
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

    public long insertaUsuario(Usuario usuario){
        ContentValues contentValues = new ContentValues();
        contentValues.put(USUARIO_ID, usuario.getId());
        contentValues.put(USUARIO_NOMBRE, usuario.getNombre());
        contentValues.put(USUARIO_EMAIL, usuario.getEmail());
        contentValues.put(USUARIO_TIPO, usuario.getTipo());
        return bd.insert(TAB_USUARIO, null, contentValues);
    }

    public Cursor obtenerUsuario(long id){
        Cursor cursor = bd.query(true, TAB_USUARIO, new String[]{USUARIO_ID, USUARIO_NOMBRE, USUARIO_EMAIL, USUARIO_TIPO}, USUARIO_ID + " = " + id, null, null, null, null, null);
        return cursor;
    }

    public Cursor obtenerUsuario(String nombre){
        Cursor cursor = bd.query(true, TAB_USUARIO, new String[]{USUARIO_ID, USUARIO_NOMBRE, USUARIO_EMAIL, USUARIO_TIPO}, USUARIO_NOMBRE + " LIKE '" + nombre + "%'", null, null, null, null, null);
        return cursor;
    }

    public long insertaAlumno(Alumno alumno){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ALUMNO_ID, alumno.getId());
        contentValues.put(ALUMNO_ID_EMPRESA, alumno.getIdEmpresa());
        contentValues.put(ALUMNO_DIRECCION, alumno.getDireccion());
        contentValues.put(ALUMNO_ID_PROFESOR, alumno.getIdProfesor());
        contentValues.put(ALUMNO_SEMANAS, alumno.getSemanas());
        return bd.insert(TAB_ALUMNO, null, contentValues);
    }

    public Cursor obtenerAlumno(long id){
        Cursor cursor = bd.query(true, TAB_ALUMNO, new String[]{ALUMNO_ID, ALUMNO_ID_EMPRESA, ALUMNO_DIRECCION, ALUMNO_ID_PROFESOR, ALUMNO_SEMANAS}, ALUMNO_ID + " = " + id, null, null, null, null, null);
        return cursor;
    }

    public long insertaEmpresa(Empresa empresa){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPRESA_ID, empresa.getId());
        contentValues.put(EMPRESA_NOMBRE, empresa.getNombre());
        contentValues.put(EMPRESA_EMAIL, empresa.getEmail());
        contentValues.put(EMPRESA_DIRECCION, empresa.getDireccion());
        contentValues.put(EMPRESA_WEB, empresa.getWeb());
        contentValues.put(EMPRESA_TELEFONO, empresa.getTelefono());
        return bd.insert(TAB_EMPRESA, null, contentValues);
    }

    public Cursor obtenerEmpresa(long id){
        Cursor cursor = bd.query(true, TAB_EMPRESA, new String[]{EMPRESA_ID, EMPRESA_NOMBRE, EMPRESA_EMAIL, EMPRESA_DIRECCION, EMPRESA_WEB, EMPRESA_TELEFONO}, EMPRESA_ID + " = " + id, null, null, null, null, null);
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
        contentValues.put(TAREA_ID_ALUMNO, tarea.getIdAlumno());
        contentValues.put(TAREA_ID_PROFESOR, tarea.getIdProfesor());
        return bd.insert(TAB_TAREA, null, contentValues);
    }

    public Cursor obtenerTareas(){
        Cursor cursor = bd.query(true, TAB_TAREA, new String[]{TAREA_ID, TAREA_NOMBRE, TAREA_DESCRIPCION, TAREA_FECHA, TAREA_HORAS, TAREA_REALIZADA, TAREA_ID_ALUMNO, TAREA_ID_PROFESOR},null, null, null, null, null, null);
        return cursor;
    }

    public Cursor obtenerMensaje(long id){
        Cursor cursor = bd.query(true, TAB_EMPRESA, new String[]{MENSAJE_ID, MENSAJE_CONTENIDO, MENSAJE_ID_EMISOR, MENSAJE_ID_RECEPTOR, MENSAJE_LEIDO}, MENSAJE_ID + " = " + id, null, null, null, null, null);
        return cursor;
    }

    public long insertaMensaje(Mensaje mensaje){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MENSAJE_ID, mensaje.getId());
        contentValues.put(MENSAJE_CONTENIDO, mensaje.getContenido());
        contentValues.put(MENSAJE_ID_EMISOR, mensaje.getIdEmisor());
        contentValues.put(MENSAJE_ID_RECEPTOR, mensaje.getIdReceptor());
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
        contentValues.put(OBSERVACION_ID_EMISOR, observacion.getAutorId());
        contentValues.put(OBSERVACION_ID_TAREA, observacion.getTareaId());
        contentValues.put(OBSERVACION_CONTENIDO, observacion.getContenido());
        return bd.insert(TAB_OBSERVACION, null, contentValues);
    }

    public int obtenerNumeroTareas(){
        Cursor c = bd.rawQuery("SELECT count(*) FROM " + TAB_TAREA, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public int obetenerNumeroObservaciones(){
        Cursor c = bd.rawQuery("SELECT count(*) FROM " + TAB_OBSERVACION, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public Cursor obetenerObservacionesDeTareas(long id){
        Cursor cursor = bd.query(true, TAB_OBSERVACION, new String[]{OBSERVACION_ID, OBSERVACION_ID_EMISOR, OBSERVACION_ID_TAREA, OBSERVACION_CONTENIDO}, OBSERVACION_ID_TAREA + " = " + id, null, null, null, null, null);
        return cursor;
    }

    public Cursor obtenerAlumnosDeProfeosor(long idProfesor){
        Cursor cursor = bd.query(true, TAB_ALUMNO, new String[]{ALUMNO_ID}, ALUMNO_ID_PROFESOR + " = "+idProfesor, null, null, null, null, null);
        return cursor;
    }

    public long insertaSemana(Semana semana){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SEMANA_ID, semana.getId());
        contentValues.put(SEMANA_INICIO, semana.getInicio());
        contentValues.put(SEMANA_FINAL, semana.getFin());
        contentValues.put(SEMANA_HORAS, semana.getHoras());
        return bd.insert(TAB_SEMANAS, null, contentValues);
    }

    public Cursor obtenerSemanas(){
        Cursor cursor = bd.query(true, TAB_SEMANAS, new String[]{SEMANA_ID, SEMANA_INICIO, SEMANA_FINAL, SEMANA_HORAS}, null, null, null, null, null, null);
        return cursor;
    }

    public Cursor obtenerSemana(long id){
        Cursor cursor = bd.query(true, TAB_SEMANAS, new String[]{SEMANA_ID, SEMANA_INICIO, SEMANA_FINAL, SEMANA_HORAS}, SEMANA_ID+ " = "+id, null, null, null, null, null);
        return cursor;
    }

    public void finalizarTarea(long id){
        bd.execSQL("UPDATE "+TAB_TAREA+" SET "+TAREA_REALIZADA+" = 1 WHERE "+TAREA_ID+" = "+id);
    }

    public int obtenerNumeroMensajes(){
        Cursor c = bd.rawQuery("SELECT count(*) FROM " + TAB_MENSAJE, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public Cursor obtenerMensajesRecibidos(long id){
        Cursor c = bd.query(true, TAB_MENSAJE, new String[]{MENSAJE_ID, MENSAJE_CONTENIDO, MENSAJE_ID_EMISOR, MENSAJE_ID_RECEPTOR, MENSAJE_LEIDO}, MENSAJE_ID_RECEPTOR+" = "+id, null, null, null, null, null);
        return c;
    }

    public Cursor obtenerMensajes(long id){
        Cursor c = bd.query(true, TAB_MENSAJE, new String[]{MENSAJE_ID, MENSAJE_CONTENIDO, MENSAJE_ID_EMISOR, MENSAJE_ID_RECEPTOR, MENSAJE_LEIDO}, MENSAJE_ID_RECEPTOR+" = "+id+" OR "+ MENSAJE_ID_EMISOR+" = "+id, null, null, null, null, null);
        return c;
    }

    public Cursor obtenerMensajesDeChat(long id1, long id2){
        Cursor c = bd.query(true, TAB_MENSAJE, new String[]{MENSAJE_ID, MENSAJE_CONTENIDO, MENSAJE_ID_EMISOR, MENSAJE_ID_RECEPTOR, MENSAJE_LEIDO}, "("+MENSAJE_ID_RECEPTOR+" = "+id1+" AND "+MENSAJE_ID_EMISOR+" = "+id2+") OR ("+MENSAJE_ID_RECEPTOR+" = "+id2+" AND "+MENSAJE_ID_EMISOR+" = "+id1+")", null, null, null, null, null);
        return c;
    }

    public Cursor obtenerUltimoMensaje(long id1, long id2){
        Cursor c = bd.query(true, TAB_MENSAJE, new String[]{MENSAJE_CONTENIDO, MENSAJE_LEIDO, MENSAJE_ID_EMISOR}, "("+MENSAJE_ID_RECEPTOR+" = "+id1+" AND "+MENSAJE_ID_EMISOR+" = "+id2+") OR ("+MENSAJE_ID_RECEPTOR+" = "+id2+" AND "+MENSAJE_ID_EMISOR+" = "+id1+")", null, null, null, MENSAJE_ID+" DESC", "1");
        return c;
    }

    public void leerMensaje(long id){
        bd.execSQL("UPDATE "+TAB_MENSAJE+" SET "+MENSAJE_LEIDO+" = 1 WHERE "+MENSAJE_ID+" = "+id);
    }

    public void cambiarSemanas(long id, int semanas){
        bd.execSQL("UPDATE "+TAB_ALUMNO+" SET "+ALUMNO_SEMANAS+" = "+semanas+" WHERE "+ALUMNO_ID+" = "+id);
    }

}
