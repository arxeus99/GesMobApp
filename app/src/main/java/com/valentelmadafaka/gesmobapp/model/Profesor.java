package com.valentelmadafaka.gesmobapp.model;

import java.util.List;

public class Profesor extends Persona {
    private List<Alumno> alumnos;
    private List<Mensaje> mensajes;

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}
