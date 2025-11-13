package clases;

import interfaces.Rol;
import java.util.ArrayList;
import java.util.List;

public class Sistema {

    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private Inventario inventario = new Inventario();
    private ArrayList<Alquiler> alquileres = new ArrayList<>();

    public Sistema() {

        usuarios.add(new Usuario(1, "12345678", "María", "Pérez", "admin1", new Administrador()));
        usuarios.add(new Usuario(2, "87654321", "Juan", "Lopez", "trabajador1", new Trabajador()));
        usuarios.add(new Usuario(3, "11223344", "Ana", "Torres", "invitado1", new Invitado()));
        usuarios.add(new Usuario(4, "74769138", "Maria", "Luna", "secretaria1", new Secretaria()));

        usuarios.add(new Usuario(5, "99887766", "Carlos", "Ruiz", "encargado1", new Encargado()));
        usuarios.add(new Usuario(6, "66778899", "Luisa", "Mendez", "contadora1", new Contadora()));

    }

    public Usuario login(String dni, String clave) {
        for (Usuario u : usuarios) {

            if (u.getDni().equals(dni)) {
                if (u.getRol() instanceof Invitado) {
                    return u;
                } else if (u.getClave().equals(clave)) {
                    return u;
                }
            }
        }
        return null;
    }

    public boolean agregarUsuario(Usuario nuevoUsuario) {

        System.out.println("Intentando agregar usuario: " + nuevoUsuario.getNombre());
        return usuarios.add(nuevoUsuario);
    }

    public boolean modificarUsuario(String dni, Usuario usuarioModificado) {
        System.out.println("Intentando modificar usuario con DNI: " + dni);
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getDni().equals(dni)) {
                usuarios.set(i, usuarioModificado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarUsuario(String dni) {
        System.out.println("Intentando eliminar usuario con DNI: " + dni);
        return usuarios.removeIf(u -> u.getDni().equals(dni));
    }

    public Usuario buscarUsuarioPorDNI(String dni) {
        System.out.println("Buscando usuario con DNI: " + dni);
        for (Usuario u : usuarios) {
            if (u.getDni().equals(dni)) {
                return u;
            }
        }
        return null;
    }

    public void registrarAsistencia(Usuario usuario, String tipo) {
        System.out.println("Registrando " + tipo + " para: " + usuario.getNombre());

    }

    public List<String> obtenerRegistrosAsistencia(Usuario usuario) {
        System.out.println("Obteniendo registros de asistencia para: " + usuario.getNombre());

        return new ArrayList<>();
    }

    public String generarReciboPorHonorarios(Usuario trabajador) {
        System.out.println("Generando recibo para: " + trabajador.getNombre());

        return "Recibo de Honorarios para " + trabajador.getNombre() + " - Monto: S/ XXXX.XX"; 
    }

    public String generarReporteGlobal() {
        System.out.println("Generando reporte global...");

        return "Reporte Global - Total Usuarios: " + usuarios.size() + " - Items Inventario: " + inventario.getListaProductos().size(); 
    }

    public String generarReporteFinanciero() {
        System.out.println("Generando reporte financiero...");
        double totalIngresos = calcularTotalIngresos();

        return "Reporte Financiero - Ingresos Totales (Alquileres): S/ " + totalIngresos;
    }

    public void registrarPagoPersonal(Usuario contador, Usuario empleado, double monto) {
        System.out.println(contador.getNombre() + " está registrando un pago de S/" + monto + " para " + empleado.getNombre());

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

    public double calcularTotalIngresos() {
        double total = 0;

        for (Alquiler a : alquileres) {
            total += a.calcularTotal();
        }
        System.out.println("Calculando ingresos totales...");
        return total;
    }
}
