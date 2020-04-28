package com.valentelmadafaka.gesmobapp.model;

public class Mensaje {
    private String id;
    private String contenido;
    private Persona emisor;
    private Persona receptor;
    private boolean leido;

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Persona getEmisor() {
        return emisor;
    }

    public void setEmisor(Persona emisor) {
        this.emisor = emisor;
    }

    public Persona getReceptor() {
        return receptor;
    }

    public void setReceptor(Persona receptor) {
        this.receptor = receptor;
    }
}
