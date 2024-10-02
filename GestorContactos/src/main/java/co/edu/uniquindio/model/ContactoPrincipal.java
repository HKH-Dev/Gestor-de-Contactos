package co.edu.uniquindio.model;

import co.edu.uniquindio.model.implementacion.Personal;
import co.edu.uniquindio.model.implementacion.Profesional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactoPrincipal {
    private List<Contacto> listacontactos;

    public ContactoPrincipal() {
        this.listacontactos = new ArrayList<>();
    }

    public void crearContacto(Contacto contacto) {
        listacontactos.add(contacto);
        System.out.println("Contacto creado correctamente");
    }

    public void agregarContacto(String nombre, String apellido, String numeroTelefono, LocalDate fechaNacimiento, String email, String categoria) {
        if (nombre.isEmpty() || apellido.isEmpty() || numeroTelefono.isEmpty() || fechaNacimiento == null || email.isEmpty() || categoria.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }
        Contacto contacto;
        if (categoria.equals("Profesional")) {
            // Valores de ejemplo para profesion y lugarTrabajo
            contacto = new Profesional(nombre, apellido, numeroTelefono, fechaNacimiento, email, "Ingeniero", "Nasa");
        } else if (categoria.equals("Personal")) {
            // Valor de ejemplo para relacion
            contacto = new Personal(nombre, apellido, numeroTelefono, fechaNacimiento, email, "Amigo");
        } else {
            // Contacto genérico
            contacto = new Contacto(nombre, apellido, numeroTelefono, fechaNacimiento, email) {
                @Override
                public String mostrarDetallesEspecificos() {
                    return "Categoría: " + categoria;
                }

                @Override
                public String getCategoria() {
                    return categoria;
                }
            };
        }
        listacontactos.add(contacto);
    }

    public List<Contacto> listarContactos() {
        return new ArrayList<>(listacontactos); // Retorna una copia para evitar modificaciones externas
    }

    public void mostrarContactos(String numeroTelefono) {
        boolean encontrado = false;
        for (Contacto contacto : listacontactos) {
            if (contacto.getNumeroTelefono().equals(numeroTelefono)) {
                System.out.printf("Nombre: %s%nApellido: %s%nNúmero de teléfono: %s%nFecha de Cumpleaños: %s%nEmail: %s%n%s%n",
                        contacto.getNombre(), contacto.getApellido(), contacto.getNumeroTelefono(),
                        contacto.getFechaNacimiento(), contacto.getEmail(), contacto.mostrarDetallesEspecificos());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se ha encontrado ningún contacto asociado a este número de teléfono: " + numeroTelefono);
        }
    }

    public void actualizarContacto(String numeroTelefono, String nuevoNombre, String nuevoApellido, LocalDate nuevaFechaNacimiento, String nuevoEmail) {
        boolean encontrado = false;
        for (Contacto contacto : listacontactos) {
            if (contacto.getNumeroTelefono().equals(numeroTelefono)) {
                contacto.setNombre(nuevoNombre);
                contacto.setApellido(nuevoApellido);
                contacto.setFechaNacimiento(nuevaFechaNacimiento);
                contacto.setEmail(nuevoEmail);
                System.out.println("Contacto actualizado exitosamente.");
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se ha encontrado ningún contacto asociado a este número de teléfono.");
        }
    }

    public void eliminarContacto(String numeroTelefono) {
        Contacto contactoAEliminar = null;
        for (Contacto contacto : listacontactos) {
            if (contacto.getNumeroTelefono().equals(numeroTelefono)) {
                contactoAEliminar = contacto;
                break;
            }
        }

        if (contactoAEliminar != null) {
            listacontactos.remove(contactoAEliminar);
            System.out.println("Contacto eliminado exitosamente.");
        } else {
            System.out.println("No se ha encontrado ningún contacto asociado a este número de teléfono.");
        }
    }

    public ArrayList<String> listarCategorias() {
        ArrayList<String> categoriasElegir = new ArrayList<>();
        categoriasElegir.add("Personal");
        categoriasElegir.add("Profesional");
        return categoriasElegir;
    }

    public ArrayList<String>listarCategoriaBuscar(){
        ArrayList<String> categoriasElegitBuscar = new ArrayList<>();
        categoriasElegitBuscar.add("Numero de Telefono");
        categoriasElegitBuscar.add("Email");
        return categoriasElegitBuscar;
    }

    public Contacto buscarPorTelefono(String numeroTelefono) {
        for (Contacto contacto : listacontactos) {
            if (contacto.getNumeroTelefono().equals(numeroTelefono)) {
                return contacto;
            }
        }
        return null;
    }
    public Contacto buscarPorEmail(String email) {
        for (Contacto contacto : listacontactos) {
            if (contacto.getEmail().equals(email)) {
                return contacto;
            }
        }
        return null;
    }
}
