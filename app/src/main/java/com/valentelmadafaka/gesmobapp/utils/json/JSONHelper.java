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

public class JSONHelper {

    private GesMobDB db;

    public JSONHelper(Context context){
        this.db = new GesMobDB(context);
    }

    public Alumno obtenerAlumno(String JSON) throws JSONException {
        JSONObject data = new JSONObject(JSON);
        Alumno alumno = new Alumno();
        alumno.setId(data.getString("idAlumno"));
        alumno.setNombre(data.getString("nombreAlumno"));
        alumno.setEmail(data.getString("emailAlumno"));
        alumno.setDireccion(data.getString("direccionAlumno"));
        alumno.setIdProfesor("idProfesor");
        alumno.setIdEmpresa("idEmpresa");
        return alumno;
    }

    public Profesor obtenerProfesor(String JSON) throws JSONException {
        JSONObject data = new JSONObject(JSON);
        Profesor profesor = new Profesor();
        profesor.setId(data.getString("idProfesor"));
        profesor.setNombre(data.getString("nombreProfesor"));
        profesor.setEmail(data.getString("emailProfesor"));
        return profesor;
    }

    public Empresa obtenerEmpresa(String JSON) throws JSONException{
        JSONObject data = new JSONObject(JSON);
        Empresa empresa = new Empresa();
        empresa.setId(data.getString("idEmpresa"));
        empresa.setNombre(data.getString("nombreEmpresa"));
        empresa.setEmail(data.getString("emailEmpresa"));
        return empresa;
    }
}
