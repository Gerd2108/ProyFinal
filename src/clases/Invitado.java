package clases;

public class Invitado extends Trabajador {

    @Override
    public String getNombreRol() {
        return "Invitado";
    }

    @Override
    public void mostrarMenu() {
        System.out.println("Menú de Invitado: ver horarios y registrar asistencia (sin descarga de recibos).");
    }
}
