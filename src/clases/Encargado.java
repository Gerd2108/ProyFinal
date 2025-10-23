package clases;

import interfaces.Rol;


public class Encargado implements Rol {

    @Override
    public String getNombreRol() {
        return "Encargado Laboral";
    }

    @Override
    public void mostrarMenu() {
        System.out.println("Menú del Encargado: acceso a personal, proyectos e inventario (sin finanzas).");
    }
}
