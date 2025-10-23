package clases;

import interfaces.Rol;

public class Trabajador implements Rol {

    @Override
    public String getNombreRol() {
        return "Trabajador";
    }

    @Override
    public void mostrarMenu() {
        System.out.println("Menú de Trabajador: ver horarios, registrar asistencia y descargar recibos.");
    }
}
