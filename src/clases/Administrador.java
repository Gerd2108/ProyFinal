package clases;

import interfaces.Rol;


public class Administrador implements Rol {

    @Override
    public String getNombreRol() {
        return "administrador";
    }

    @Override
    public void mostrarMenu() {
        System.out.println("Acceso total: administración, personal, proyectos, inventario y finanzas.");
    }
}
