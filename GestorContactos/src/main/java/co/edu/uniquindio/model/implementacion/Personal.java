package co.edu.uniquindio.model.implementacion;

import co.edu.uniquindio.model.Contacto;

import java.time.LocalDate;

public class Personal extends Contacto {
    private String relacion;

    public Personal(String nombre, String apellido, String numeroTelefono, LocalDate fechaNacimiento, String email, String relacion) {
        super(nombre, apellido, numeroTelefono, fechaNacimiento, email);
        this.relacion = relacion;
    }

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    @Override
    public String mostrarDetallesEspecificos() {
        return String.format("Relación: %s", relacion);
    }

    @Override
    public String getCategoria() {
        return "Personal";
    }
}
