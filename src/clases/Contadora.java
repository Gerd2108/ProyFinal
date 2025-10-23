package clases;

import interfaces.Rol;

public class Contadora implements Rol {

    @Override
    public String getNombreRol() {
        return "Contadora";
    }

    @Override
    public void mostrarMenu() {
        System.out.println("Menú de Contadora: acceso a contabilidad, pagos e informes financieros.");
    }
}
