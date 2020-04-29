package com.valentelmadafaka.gesmobapp.model;

public class Observacion {
    private String id;
    private String autorId;
    private String contenido;
    private String tareaId;

    public void setAutorId(String autorId) {
        this.autorId = autorId;
    }

    public String getTareaId() {
        return tareaId;
    }

    public void setTareaId(String tareaId) {
        this.tareaId = tareaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutorId() {
        return autorId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
