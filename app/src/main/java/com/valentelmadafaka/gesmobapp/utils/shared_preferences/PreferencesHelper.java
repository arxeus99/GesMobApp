package com.valentelmadafaka.gesmobapp.utils.shared_preferences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valentelmadafaka.gesmobapp.model.Alumno;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class PreferencesHelper {

    public static void desaUsuari(String key, Object data, Context context){
        SharedPreferences prefs = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString(key, json);
        editor.apply();
    }

    public static Alumno recuperarUsuari(String key, Context context){
        SharedPreferences prefs = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String json = prefs.getString(key, "");
        Alumno object;
        if(json.isEmpty()){
            object = null;
        }else{
            java.lang.reflect.Type type = new TypeToken<Alumno>(){}.getType();
            Gson gson = new Gson();
            object = gson.fromJson(json, type);
        }
        return object;
    }
}
