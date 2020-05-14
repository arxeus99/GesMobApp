package com.valentelmadafaka.gesmobapp.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.valentelmadafaka.gesmobapp.R;

import java.util.ArrayList;

public class ChatArray extends ArrayAdapter<Chat> {

    private Context context;
    private ArrayList<Chat> chats;

    public ChatArray(Context context, int resource, ArrayList<Chat> chats){
        super(context, resource, chats);
        this.context = context;
        this.chats = chats;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        Chat chat = chats.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view;
        if(chat.isNotificacion())
            view =inflater.inflate(R.layout.chat_view_notificacion, null);
        else
            view = inflater.inflate(R.layout.chat_view, null);
        TextView nombre = view.findViewById(R.id.nombre);
        TextView ultimoMensaje = view.findViewById(R.id.ultimoMensaje);
        nombre.setText(chat.getUsuario().getNombre());
        ultimoMensaje.setText(chat.getUltimoMensaje());
        return view;
    }
}
