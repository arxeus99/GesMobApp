package com.valentelmadafaka.gesmobapp.utils.json;

import android.content.Context;
import android.database.Cursor;

import com.valentelmadafaka.gesmobapp.model.Alumno;
import com.valentelmadafaka.gesmobapp.model.Empresa;
import com.valentelmadafaka.gesmobapp.model.Mensaje;
import com.valentelmadafaka.gesmobapp.model.Profesor;
import com.valentelmadafaka.gesmobapp.model.Semana;
import com.valentelmadafaka.gesmobapp.model.Tarea;
import com.valentelmadafaka.gesmobapp.model.Usuario;
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

    public static Usuario obtenerUsuario(String JSON) throws JSONException{
        JSONObject data = new JSONObject(JSON);
        Usuario usuario = new Usuario();
        usuario.setId(data.getString("id"));
        usuario.setNombre(data.getString("nombre"));
        usuario.setEmail(data.getString("email"));
        usuario.setTipo(data.getString("tipo"));
        return usuario;
    }

    public static Alumno obtenerAlumno(String JSON) throws JSONException {
        JSONObject data = new JSONObject(JSON);
        Alumno alumno = new Alumno();
        alumno.setId(data.getString("id"));
        alumno.setDireccion(data.getString("direccion"));
        alumno.setIdProfesor(data.getString("idProfesor"));
        alumno.setIdEmpresa(data.getString("idEmpresa"));
        alumno.setSemanas(data.getString("semanas"));
        return alumno;
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

    public static Tarea obtenerTarea(String JSON) throws JSONException {
        JSONObject data = new JSONObject(JSON);
        Tarea tarea = new Tarea();
        tarea.setId(data.getString("id"));
        tarea.setNombre(data.getString("nombre"));
        tarea.setDescripcion(data.getString("descripcion"));
        tarea.setFecha(data.getString("fecha"));
        tarea.setHoras(data.getInt("horas"));
        if(data.getInt("realizada") == 1){
            tarea.setRealizada(true);
        }else{
            tarea.setRealizada(false);
        }
        tarea.setIdAlumno(data.getString("idAlumno"));
        tarea.setIdProfesor(data.getString("idProfesor"));
        return tarea;
    }

    public static Mensaje obtenerMensaje(String JSON) throws JSONException{
        JSONObject data = new JSONObject(JSON);
        Mensaje mensaje = new Mensaje();
        mensaje.setId(data.getString("id"));
        mensaje.setIdReceptor(data.getString("receptor"));
        mensaje.setIdEmisor(data.getString("emisor"));
        mensaje.setContenido(data.getString("contenido"));
        if(data.getInt("leido") == 1){
            mensaje.setLeido(true);
        }else{
            mensaje.setLeido(false);
        }
        return mensaje;
    }

    public static Semana obtenerSemana(String JSON) throws JSONException{
        JSONObject data = new JSONObject(JSON);
        Semana semana = new Semana();
        semana.setId(data.getString("id"));
        semana.setInicio(data.getString("inicio"));
        semana.setFin(data.getString("fin"));
        semana.setHoras(data.getInt("horas"));
        return semana;
    }
}
