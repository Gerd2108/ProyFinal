package clases;

import interfaces.Rol;

public class Secretaria implements Rol {

    @Override
    public String getNombreRol() {
        return "Secretaria";
    }

    @Override
    public void mostrarMenu() {
        System.out.println("Menú de Secretaria: acceso a administración, personal y cuentas básicas.");
    }
}
