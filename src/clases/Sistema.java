package clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import interfaces.Rol;
import java.util.ArrayList;
import java.util.List;

public class Sistema {

    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private Inventario inventario = new Inventario();
    private ArrayList<Alquiler> alquileres = new ArrayList<>();

    public Sistema() {

        cargarDatos();

        if (usuarios.isEmpty()) {
            System.out.println("Generando usuarios por defecto...");
            usuarios.add(new Usuario(1, "12345678", "María", "Pérez", "admin1", new Administrador()));
            usuarios.add(new Usuario(2, "87654321", "Juan", "Lopez", "trabajador1", new Trabajador()));
            usuarios.add(new Usuario(3, "11223344", "Ana", "Torres", "invitado1", new Invitado()));
            usuarios.add(new Usuario(4, "74769138", "Maria", "Luna", "secretaria1", new Secretaria()));
            usuarios.add(new Usuario(5, "99887766", "Carlos", "Ruiz", "encargado1", new Encargado()));
            usuarios.add(new Usuario(6, "66778899", "Luisa", "Mendez", "contadora1", new Contadora()));
        }

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
        String registro = tipo + " - " + new java.util.Date().toString();
        usuario.getAsistencia().add(registro);
        System.out.println("Registrando " + tipo + " para: " + usuario.getNombre());

    }

    public List<String> obtenerRegistrosAsistencia(Usuario usuario) {
        System.out.println("Obteniendo registros de asistencia para: " + usuario.getNombre());
        return usuario.getAsistencia();
    }

    public String generarReciboPorHonorarios(Usuario trabajador) {
        System.out.println("Generando recibo para: " + trabajador.getNombre());
        double monto = 1200 + (Math.random() * 300);
        String montoFormateado = String.format("%.2f", monto);

        return "Recibo de Honorarios para " + trabajador.getNombre() + " - Monto: S/ " + montoFormateado;
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

    public void guardarDatos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("alquileres.txt"))) {
            for (Alquiler a : alquileres) {
                String idsProductos = "";
                for (Producto p : a.getProductos()) {
                    idsProductos += p.getIdProducto() + ";";
                }
                if (!idsProductos.isEmpty()) {
                    idsProductos = idsProductos.substring(0, idsProductos.length() - 1);
                }

                String linea = String.join(",",
                        String.valueOf(a.getIdAlquiler()),
                        a.getUsuario().getDni(),
                        String.valueOf(a.getPrecioPorDia()),
                        String.valueOf(a.getDias()),
                        idsProductos
                );
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Alquileres guardados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar alquileres: " + e.getMessage());
        }
    }

    private void cargarDatos() {

        try (BufferedReader br = new BufferedReader(new FileReader("alquileres.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    int id = Integer.parseInt(datos[0]);
                    Usuario usuario = buscarUsuarioPorDNI(datos[1]);
                    double precioDia = Double.parseDouble(datos[2]);
                    int dias = Integer.parseInt(datos[3]);
                    String[] idsProductos = datos[4].split(";");

                    List<Producto> productosAlquilados = new ArrayList<>();
                    for (String idProd : idsProductos) {
                        Producto p = inventario.buscarProducto(Integer.parseInt(idProd));
                        if (p != null) {
                            productosAlquilados.add(p);
                        }
                    }

                    if (usuario != null) {
                        alquileres.add(new Alquiler(id, usuario, productosAlquilados, precioDia, dias));
                    }
                }
            }
            System.out.println("Alquileres cargados: " + alquileres.size());
        } catch (IOException | NumberFormatException e) {
            System.err.println("No se encontró 'alquileres.txt' o está corrupto.");
        }
    }

    private Rol getRolPorNombre(String nombreRol) {
        switch (nombreRol.toLowerCase()) {
            case "administrador":
                return new Administrador();
            case "contadora":
                return new Contadora();
            case "encargado":
                return new Encargado();
            case "secretaria":
                return new Secretaria();
            case "trabajador":
                return new Trabajador();
            case "invitado":
                return new Invitado();
            default:
                return new Invitado();
        }
    }

    public void agregarAlquiler(Alquiler alquiler) {
        this.alquileres.add(alquiler);
        System.out.println("Alquiler registrado para: " + alquiler.getUsuario().getNombre());
    }
}
