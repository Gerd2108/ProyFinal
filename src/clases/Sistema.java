package clases;

import interfaces.Operacion;
import java.util.ArrayList;

public class Sistema {

    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private Inventario inventario = new Inventario();
    private ArrayList<Operacion> operaciones = new ArrayList<>();

    public void registrarUsuario(Usuario u) {
        usuarios.add(u);
    }

    public Usuario login(String dni) {
        for (Usuario u : usuarios) {
            if (u.getDni().equals(dni)) {
                return u;
            }
        }
        return null;
    }

    public void registrarOperacion(Operacion op) {
        operaciones.add(op);
    }

    public double calcularTotalIngresos() {
        double total = 0;
        for (Operacion op : operaciones) {
            total += op.calcularTotal();
        }
        return total;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public ArrayList<Operacion> getOperaciones() {
        return operaciones;
    }
}
