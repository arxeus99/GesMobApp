package com.valentelmadafaka.gesmobapp.utils.json;

import android.content.Context;
import android.database.Cursor;

import com.valentelmadafaka.gesmobapp.model.Alumno;
import com.valentelmadafaka.gesmobapp.model.Empresa;
import com.valentelmadafaka.gesmobapp.model.Profesor;
import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Policy;

public abstract class JSONHelper {

//    private GesMobDB db;
//
//    public JSONHelper(Context context){
//        this.db = new GesMobDB(context);
//    }

    public static Alumno obtenerAlumno(String JSON) throws JSONException {
        JSONObject data = new JSONObject(JSON);
        Alumno alumno = new Alumno();
        alumno.setId(data.getString("id"));
        alumno.setNombre(data.getString("nombre"));
        alumno.setEmail(data.getString("email"));
        alumno.setDireccion(data.getString("direccion"));
        alumno.setIdProfesor(data.getString("idProfesor"));
        alumno.setIdEmpresa(data.getString("idEmpresa"));
        return alumno;
    }

    public static Profesor obtenerProfesor(String JSON) throws JSONException {
        JSONObject data = new JSONObject(JSON);
        Profesor profesor = new Profesor();
        profesor.setId(data.getString("id"));
        profesor.setNombre(data.getString("nombre"));
        profesor.setEmail(data.getString("email"));
        return profesor;
    }

    public static Empresa obtenerEmpresa(String JSON) throws JSONException{
        JSONObject data = new JSONObject(JSON);
        Empresa empresa = new Empresa();
        empresa.setId(data.getString("id"));
        empresa.setNombre(data.getString("nombre"));
        empresa.setEmail(data.getString("email"));
        empresa.setDireccion(data.getString("direccion"));
        empresa.setWeb(data.getString("web"));
        empresa.setTelefono(data.getString("telefono"));
        return empresa;
    }
}
