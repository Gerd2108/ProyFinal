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
    private ArrayList<Cliente> clientes = new ArrayList<>();

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

        if (clientes.isEmpty()) {
            System.out.println("Generando cliente por defecto...");
            clientes.add(new Cliente("99999999", "Cliente", "General"));
        }
    }

    // GESTIÓN DE USUARIOS Y AUTENTICACIÓN
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
        System.out.println("Agregando usuario: " + nuevoUsuario.getNombre());
        return usuarios.add(nuevoUsuario);
    }

    public boolean modificarUsuario(String dni, Usuario usuarioModificado) {
        System.out.println("Modificando usuario con DNI: " + dni);
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getDni().equals(dni)) {
                usuarios.set(i, usuarioModificado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarUsuario(String dni) {
        System.out.println("Eliminando usuario con DNI: " + dni);
        return usuarios.removeIf(u -> u.getDni().equals(dni));
    }

    public Usuario buscarUsuarioPorDNI(String dni) {
        for (Usuario u : usuarios) {
            if (u.getDni().equals(dni)) {
                return u;
            }
        }
        return null;
    }

    public int generarNuevoIdUsuario() {
        int maxId = 0;
        for (Usuario u : usuarios) {

        }
        return usuarios.size() + 1;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    //GESTIÓN DE PRODUCTOS E INVENTARIO
    public Inventario getInventario() {
        return inventario;
    }

    public boolean modificarProducto(int id, Producto productoModificado) {
        for (int i = 0; i < inventario.getListaProductos().size(); i++) {
            if (inventario.getListaProductos().get(i).getIdProducto() == id) {
                inventario.getListaProductos().set(i, productoModificado);
                return true;
            }
        }
        return false;
    }

    public int generarNuevoIdProducto() {
        int maxId = 0;
        for (Producto p : inventario.getListaProductos()) {
            if (p.getIdProducto() > maxId) {
                maxId = p.getIdProducto();
            }
        }
        return maxId + 1;
    }

    public boolean sePuedeEliminarProducto(int idProducto) {
        for (Alquiler a : alquileres) {
            for (Producto p : a.getProductos()) {
                if (p.getIdProducto() == idProducto) {
                    return false;
                }
            }
        }
        return true;
    }

    // GESTIÓN DE CLIENTES
    public void agregarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public Cliente buscarClientePorDNI(String dni) {
        for (Cliente c : clientes) {
            if (c.getDni().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    //GESTIÓN DE ALQUILERES Y DEVOLUCIONES
    public void agregarAlquiler(Alquiler alquiler) {
        this.alquileres.add(alquiler);
        System.out.println("Alquiler registrado para: " + alquiler.getCliente().getNombre());
    }

    public Alquiler buscarAlquilerPorID(int id) {
        for (Alquiler a : alquileres) {
            if (a.getIdAlquiler() == id) {
                return a;
            }
        }
        return null;
    }

    public boolean procesarDevolucion(Alquiler alquiler) {
        if (alquiler == null) {
            return false;
        }

        //Aumentar Stock
        for (Producto p : alquiler.getProductos()) {
            Producto productoEnInventario = inventario.buscarProducto(p.getIdProducto());
            if (productoEnInventario != null) {
                productoEnInventario.aumentarStock(1);
            }
        }

        //Eliminar Alquiler de la lista activa
        boolean eliminado = alquileres.remove(alquiler);

        //Guardar cambios
        if (eliminado) {
            guardarDatos();
            return true;
        }
        return false;
    }

    public int generarNuevoIdAlquiler() {
        int maxId = 0;
        for (Alquiler a : alquileres) {
            if (a.getIdAlquiler() > maxId) {
                maxId = a.getIdAlquiler();
            }
        }
        return maxId + 1;
    }

    public ArrayList<Alquiler> getAlquileres() {
        return alquileres;
    }

    //ASISTENCIAS Y PAGOS
    public void registrarAsistencia(Usuario usuario, String tipo) {
        String registro = tipo + " - " + new java.util.Date().toString();
        usuario.getAsistencia().add(registro);
        System.out.println("Registrando " + tipo + " para: " + usuario.getNombre());
    }

    public List<String> obtenerRegistrosAsistencia(Usuario usuario) {
        return usuario.getAsistencia();
    }

    public String generarReciboPorHonorarios(Usuario trabajador) {
        double monto = 1200 + (Math.random() * 300);
        String montoFormateado = String.format("%.2f", monto);
        return "Recibo de Honorarios para " + trabajador.getNombre() + " - Monto: S/ " + montoFormateado;
    }

    public void registrarPagoPersonal(Usuario contador, Usuario empleado, double monto) {
        String nombreArchivo = "pagos.csv";
        java.io.File archivo = new java.io.File(nombreArchivo);
        boolean existe = archivo.exists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            if (!existe) {
                bw.write("FECHA;HORA;DNI_EMPLEADO;NOMBRE_EMPLEADO;ROL;MONTO_PAGADO;REGISTRADO_POR");
                bw.newLine();
            }

            java.text.SimpleDateFormat sdfFecha = new java.text.SimpleDateFormat("dd/MM/yyyy");
            java.text.SimpleDateFormat sdfHora = new java.text.SimpleDateFormat("HH:mm:ss");
            java.util.Date ahora = new java.util.Date();

            String linea = String.join(";",
                    sdfFecha.format(ahora),
                    sdfHora.format(ahora),
                    empleado.getDni(),
                    empleado.getNombre() + " " + empleado.getApellido(),
                    empleado.getRol().getNombreRol(),
                    String.format("%.2f", monto).replace(",", "."),
                    contador.getNombre() + " (Contadora)"
            );
            bw.write(linea);
            bw.newLine();
            System.out.println("Pago registrado en " + nombreArchivo);

        } catch (IOException e) {
            System.err.println("Error al guardar el pago: " + e.getMessage());
        }
    }

    //REPORTES Y CONTABILIDAD
    public String generarReporteGlobal() {
        return "Reporte Global - Total Usuarios: " + usuarios.size() + " - Items Inventario: " + inventario.getListaProductos().size();
    }

    public String generarReporteFinanciero() {
        double totalIngresos = calcularTotalIngresos();
        return "Reporte Financiero - Ingresos Totales (Alquileres): S/ " + totalIngresos;
    }

    public double calcularTotalIngresos() {
        double total = 0;
        for (Alquiler a : alquileres) {
            total += a.calcularTotal();
        }
        return total;
    }

    public void exportarReporteContable() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_contable.csv"))) {
            // Ingresos 
            bw.write("--- REPORTE DE INGRESOS (ALQUILERES) ---");
            bw.newLine();
            bw.write("ID_ALQUILER;CLIENTE;DNI_RUC;ITEMS;DIAS;SUBTOTAL;IGV;TOTAL");
            bw.newLine();

            double sumaIngresos = 0;
            for (Alquiler a : alquileres) {
                double total = a.calcularTotal();
                double subtotal = total / 1.18;
                double igv = total - subtotal;
                sumaIngresos += total;

                String linea = String.join(";",
                        String.valueOf(a.getIdAlquiler()),
                        a.getCliente().getNombre() + " " + a.getCliente().getApellido(),
                        a.getCliente().getDni(),
                        String.valueOf(a.getProductos().size()),
                        String.valueOf(a.getDias()),
                        String.format("%.2f", subtotal).replace(",", "."),
                        String.format("%.2f", igv).replace(",", "."),
                        String.format("%.2f", total).replace(",", ".")
                );
                bw.write(linea);
                bw.newLine();
            }
            bw.write(";;;;;;TOTAL INGRESOS:;" + String.format("%.2f", sumaIngresos).replace(",", "."));
            bw.newLine();
            bw.newLine();

            //Inventario
            bw.write("--- VALORIZACIÓN DE INVENTARIO (ACTIVOS) ---");
            bw.newLine();
            bw.write("ID;PRODUCTO;CATEGORÍA;COSTO_UNIT;STOCK_ACTUAL;VALOR_TOTAL");
            bw.newLine();

            double valorTotalInventario = 0;
            for (Producto p : inventario.getListaProductos()) {
                double valorItem = p.getPrecio() * p.getStock();
                valorTotalInventario += valorItem;

                String linea = String.join(";",
                        String.valueOf(p.getIdProducto()),
                        p.getNomProducto(),
                        p.getCatProducto(),
                        String.format("%.2f", p.getPrecio()).replace(",", "."),
                        String.valueOf(p.getStock()),
                        String.format("%.2f", valorItem).replace(",", ".")
                );
                bw.write(linea);
                bw.newLine();
            }
            bw.write(";;;;;TOTAL ACTIVOS:;" + String.format("%.2f", valorTotalInventario).replace(",", "."));
            bw.newLine();

            System.out.println("Reporte contable generado.");

        } catch (IOException e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
        }
    }

    //GUARDAR Y CARGAR DATOS
    public void guardarDatos() {
        //Guardar Usuarios
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usuarios.txt"))) {
            for (Usuario u : usuarios) {
                String asistenciasStr = String.join("#", u.getAsistencia());
                String linea = String.join(",",
                        u.getDni(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getClave(),
                        u.getRol().getNombreRol(),
                        asistenciasStr
                );
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando usuarios: " + e.getMessage());
        }

        //Guardar Productos
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("productos.txt"))) {
            for (Producto p : inventario.getListaProductos()) {
                String linea = String.join(",",
                        String.valueOf(p.getIdProducto()),
                        p.getNomProducto(),
                        p.getCatProducto(),
                        String.valueOf(p.getPrecio()),
                        String.valueOf(p.getStock())
                );
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando productos: " + e.getMessage());
        }

        //Guardar Clientes
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("clientes.txt"))) {
            for (Cliente c : clientes) {
                String linea = String.join(",", c.getDni(), c.getNombre(), c.getApellido());
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando clientes: " + e.getMessage());
        }

        //Guardar Alquileres
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
                        a.getCliente().getDni(),
                        String.valueOf(a.getPrecioPorDia()),
                        String.valueOf(a.getDias()),
                        idsProductos
                );
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando alquileres: " + e.getMessage());
        }

        //Exportar Planilla
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("planilla_personal.csv"))) {
            bw.write("DNI;NOMBRE;APELLIDO;ROL;CLAVE_ACCESO");
            bw.newLine();
            for (Usuario u : usuarios) {
                String linea = String.join(";",
                        u.getDni(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getRol().getNombreRol(),
                        u.getClave()
                );
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error exportando planilla: " + e.getMessage());
        }

        System.out.println("Todos los datos han sido guardados exitosamente.");
    }

    private void cargarDatos() {
        //Cargar Usuarios
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linea;
            int idCounter = 1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5) {
                    String dni = datos[0];
                    String nombre = datos[1];
                    String apellido = datos[2];
                    String clave = datos[3];
                    Rol rol = getRolPorNombre(datos[4]);

                    Usuario u = new Usuario(idCounter++, dni, nombre, apellido, clave, rol);
                    if (datos.length > 5 && !datos[5].isEmpty()) {
                        String[] registros = datos[5].split("#");
                        for (String reg : registros) {
                            u.getAsistencia().add(reg);
                        }
                    }
                    usuarios.add(u);
                }
            }
        } catch (IOException e) {
            System.err.println("Info: Archivo 'usuarios.txt' no encontrado. Se crearán valores por defecto.");
        }

        //Cargar Productos
        try (BufferedReader br = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String categoria = datos[2];
                    double precio = Double.parseDouble(datos[3]);
                    int stock = Integer.parseInt(datos[4]);
                    inventario.agregarProducto(new Producto(id, nombre, categoria, precio, stock));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Info: 'productos.txt' no encontrado o error de formato.");
        }

        //Cargar Clientes
        try (BufferedReader br = new BufferedReader(new FileReader("clientes.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    clientes.add(new Cliente(datos[0], datos[1], datos[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Info: 'clientes.txt' no encontrado.");
        }

        //Cargar Alquileres
        try (BufferedReader br = new BufferedReader(new FileReader("alquileres.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    int id = Integer.parseInt(datos[0]);
                    Cliente cliente = buscarClientePorDNI(datos[1]);
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

                    if (cliente != null) {
                        alquileres.add(new Alquiler(id, cliente, productosAlquilados, precioDia, dias));
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Info: 'alquileres.txt' no encontrado.");
        }

        System.out.println("Carga de datos completada.");
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
}
