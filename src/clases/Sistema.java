package clases;

import java.util.ArrayList;
import interfaces.Operacion;

public class Sistema {

    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private Inventario inventario = new Inventario();
    private ArrayList<Alquiler> alquileres = new ArrayList<>();

    public Sistema() {
        usuarios.add(new Usuario(1, "12345678", "María", "Pérez", "admin1", new Administrador()));
        usuarios.add(new Usuario(2, "87654321", "Juan", "Lopez", "trabajador1", new Trabajador()));
        usuarios.add(new Usuario(3, "11223344", "Ana", "Torres", "invitado1", new Invitado()));
        usuarios.add(new Usuario(3, "74769138", "Maria", "Luna", "secretaria1", new Secretaria()));

    }

    public void registrarUsuario(Usuario u) {
        usuarios.add(u);
    }

    public Usuario login(String dni, String clave) {
        for (Usuario u : usuarios) {
            if (u.getDni().equals(dni) && u.getClave().equals(clave)) {
                return u;
            }
        }
        return null;
    }

    public void registrarAlquiler(Alquiler a) {
        alquileres.add(a);
    }

    public double calcularTotalIngresos() {
        double total = 0;
        for (Alquiler a : alquileres) {
            total += a.calcularTotal();
        }
        return total;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public ArrayList<Alquiler> getAlquileres() {
        return alquileres;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}
