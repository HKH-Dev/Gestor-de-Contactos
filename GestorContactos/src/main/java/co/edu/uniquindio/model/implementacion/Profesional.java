package co.edu.uniquindio.model.implementacion;

import co.edu.uniquindio.model.Contacto;

import java.time.LocalDate;

public class Profesional extends Contacto {
    private String profesion;
    private String lugarTrabajo;

    public Profesional(String nombre, String apellido, String numeroTelefono, LocalDate fechaNacimiento, String email, String profesion, String lugarTrabajo) {
        super(nombre, apellido, numeroTelefono, fechaNacimiento, email);
        this.profesion = profesion;
        this.lugarTrabajo = lugarTrabajo;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getLugarTrabajo() {
        return lugarTrabajo;
    }

    public void setLugarTrabajo(String lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
    }

    @Override
    public String mostrarDetallesEspecificos() {
        return String.format("Profesión: %s%nLugar de trabajo: %s", profesion, lugarTrabajo);
    }

    @Override
    public String getCategoria() {
        return "Profesional";
    }
}
