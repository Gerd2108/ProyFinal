package clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import interfaces.Rol;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

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

    public void generarPDFAsistenciaPersonal(Usuario u, String rutaArchivo) throws IOException {
        try (org.apache.pdfbox.pdmodel.PDDocument doc = new org.apache.pdfbox.pdmodel.PDDocument()) {
            org.apache.pdfbox.pdmodel.PDPage page = new org.apache.pdfbox.pdmodel.PDPage();
            doc.addPage(page);

            try (org.apache.pdfbox.pdmodel.PDPageContentStream content = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page)) {
                int y = 750;

                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("CONSTANCIA DE ASISTENCIA LABORAL");
                content.endText();
                y -= 25;

                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA), 12);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("Empresa: DAL ESTRUCTURAS S.A.C");
                content.endText();
                y -= 40;

                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("DATOS DEL COLABORADOR:");
                content.endText();
                y -= 20;

                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA), 11);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("Nombre: " + u.getNombre() + " " + u.getApellido());
                content.endText();
                y -= 15;
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("DNI: " + u.getDni());
                content.endText();
                y -= 15;
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("Cargo: " + u.getRol().getNombreRol());
                content.endText();
                y -= 40;

                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("REGISTRO DE MARCAS:");
                content.endText();
                y -= 20;

                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.COURIER), 10);

                int contadorMarcas = 0;
                for (String registro : u.getAsistencia()) {
                    if (y < 100) {
                        break;
                    }

                    content.beginText();
                    content.newLineAtOffset(50, y);
                    content.showText("• " + registro);
                    content.endText();
                    y -= 15;
                    contadorMarcas++;
                }

                y -= 30;
                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_OBLIQUE), 10);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("Total de registros procesados: " + contadorMarcas);
                content.endText();

                content.beginText();
                content.newLineAtOffset(50, 50);
                content.showText("Certificado generado automáticamente por el Sistema de Gestión.");
                content.endText();
            }
            doc.save(rutaArchivo);
        }
    }

    public List<String> obtenerRegistrosAsistencia(Usuario usuario) {
        return usuario.getAsistencia();
    }

    public void generarPDFReciboHonorarios(Usuario u, String rutaArchivo) throws IOException {
        try (org.apache.pdfbox.pdmodel.PDDocument doc = new org.apache.pdfbox.pdmodel.PDDocument()) {
            org.apache.pdfbox.pdmodel.PDPage page = new org.apache.pdfbox.pdmodel.PDPage();
            doc.addPage(page);

            try (org.apache.pdfbox.pdmodel.PDPageContentStream content = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page)) {
                int y = 750;

                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                content.beginText();
                content.newLineAtOffset(200, y);
                content.showText("RECIBO POR HONORARIOS ELECTRÓNICO");
                content.endText();
                y -= 20;

                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA), 10);
                content.beginText();
                content.newLineAtOffset(250, y);
                content.showText("Nro: E001-" + (1000 + u.getIdUsuario()));
                content.endText();
                y -= 40;

                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 11);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("DE: " + u.getNombre().toUpperCase() + " " + u.getApellido().toUpperCase());
                content.endText();
                y -= 15;
                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA), 10);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("DNI: " + u.getDni());
                content.endText();
                y -= 15;
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("Domicilio: Lima, Perú");
                content.endText();
                y -= 30;

                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 11);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("PARA: DAL ESTRUCTURAS S.A.C.");
                content.endText();
                y -= 15;
                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA), 10);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("RUC: 20550267005");
                content.endText();
                y -= 40;
                int asistencias = u.getAsistencia().size();
                double montoBruto = (asistencias > 0) ? asistencias * 50.00 : 1025.00;
                if (montoBruto > 5000) {
                    montoBruto = 5000;
                }
                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("CONCEPTO:");
                content.endText();
                y -= 15;
                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA), 10);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("Por servicios prestados como " + u.getRol().getNombreRol() + " correspondientes al mes en curso.");
                content.endText();
                y -= 15;
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("Fecha de Emisión: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
                content.endText();
                y -= 40;

                double retencion = montoBruto * 0.08;
                double totalNeto = montoBruto - retencion;

                content.moveTo(50, y + 10);
                content.lineTo(300, y + 10);
                content.stroke();

                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(String.format("Total Honorarios:      S/ %8.2f", montoBruto));
                content.endText();
                y -= 20;
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(String.format("Retención (8%%):       S/ %8.2f", retencion));
                content.endText();
                y -= 20;
                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 11);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(String.format("TOTAL NETO RECIBIDO:   S/ %8.2f", totalNeto));
                content.endText();

                y -= 50;
                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_OBLIQUE), 8);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("Este documento es una representación impresa de un documento electrónico generado por el sistema.");
                content.endText();
            }
            doc.save(rutaArchivo);
        }
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

    public void generarPDFContable() throws IOException {
        try (org.apache.pdfbox.pdmodel.PDDocument doc = new org.apache.pdfbox.pdmodel.PDDocument()) {

            org.apache.pdfbox.pdmodel.PDPage page = new org.apache.pdfbox.pdmodel.PDPage();
            doc.addPage(page);
            org.apache.pdfbox.pdmodel.PDPageContentStream content = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page);

            int y = 750;

            content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 18);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("REPORTE CONTABLE INTEGRAL - DAL ESTRUCTURAS");
            content.endText();
            y -= 20;

            content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA), 10);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("Fecha de Emisión: " + new java.util.Date().toString());
            content.endText();
            y -= 40;

            content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("1. DETALLE DE INGRESOS (ALQUILERES)");
            content.endText();
            y -= 20;

            content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.COURIER_BOLD), 8);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText(String.format("%-4s %-20s %-10s %-3s %-3s %-9s %-9s %-9s",
                    "ID", "CLIENTE", "DNI/RUC", "ITM", "DIA", "SUBTOT.", "IGV", "TOTAL"));
            content.endText();

            y -= 5;
            content.moveTo(50, y);
            content.lineTo(550, y);
            content.stroke();
            y -= 15;

            double totalIngresos = 0;

            content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.COURIER), 8);

            for (Alquiler a : alquileres) {

                if (y < 50) {
                    content.close();
                    page = new org.apache.pdfbox.pdmodel.PDPage();
                    doc.addPage(page);
                    content = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page);
                    y = 750;
                    content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.COURIER), 8); // Reset fuente
                }

                double total = a.calcularTotal();
                double subtotal = a.getSubtotal();
                double igv = a.getIGV();
                totalIngresos += total;

                String cliente = a.getCliente().getNombre() + " " + a.getCliente().getApellido();
                if (cliente.length() > 18) {
                    cliente = cliente.substring(0, 18);
                }
                String linea = String.format("%-4d %-20s %-10s %-3d %-3d %9.2f %9.2f %9.2f",
                        a.getIdAlquiler(),
                        cliente,
                        a.getCliente().getDni(),
                        a.getProductos().size(),
                        a.getDias(),
                        subtotal, igv, total);

                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(linea);
                content.endText();
                y -= 12;
            }

            y -= 5;
            content.moveTo(50, y);
            content.lineTo(550, y);
            content.stroke();
            y -= 15;
            content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 10);
            content.beginText();
            content.newLineAtOffset(350, y);
            content.showText(String.format("TOTAL INGRESOS: S/ %.2f", totalIngresos));
            content.endText();

            y -= 40;

            if (y < 100) {
                content.close();
                page = new org.apache.pdfbox.pdmodel.PDPage();
                doc.addPage(page);
                content = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page);
                y = 750;
            }

            content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("2. VALORIZACIÓN DE INVENTARIO (ACTIVOS)");
            content.endText();
            y -= 20;

            content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.COURIER_BOLD), 8);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText(String.format("%-4s %-25s %-15s %-10s %-6s %-12s",
                    "ID", "PRODUCTO", "CATEGORÍA", "COSTO U.", "STOCK", "VALOR TOTAL"));
            content.endText();
            y -= 5;
            content.moveTo(50, y);
            content.lineTo(550, y);
            content.stroke();
            y -= 15;

            double valorTotalInventario = 0;
            content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.COURIER), 8);

            for (Producto p : inventario.getListaProductos()) {
                if (y < 50) {
                    content.close();
                    page = new org.apache.pdfbox.pdmodel.PDPage();
                    doc.addPage(page);
                    content = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page);
                    y = 750;
                    content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.COURIER), 8);
                }

                double valorItem = p.getPrecio() * p.getStock();
                valorTotalInventario += valorItem;

                String nombre = p.getNomProducto();
                if (nombre.length() > 23) {
                    nombre = nombre.substring(0, 23);
                }

                String linea = String.format("%-4d %-25s %-15s %8.2f %-6d %10.2f",
                        p.getIdProducto(), nombre, p.getCatProducto(), p.getPrecio(), p.getStock(), valorItem);

                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(linea);
                content.endText();
                y -= 12;
            }

            y -= 5;
            content.moveTo(50, y);
            content.lineTo(550, y);
            content.stroke();
            y -= 15;
            content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 10);
            content.beginText();
            content.newLineAtOffset(300, y);
            content.showText(String.format("TOTAL ACTIVOS: S/ %.2f", valorTotalInventario));
            content.endText();
            y -= 30;
            if (y < 50) {
                content.close();
                page = new org.apache.pdfbox.pdmodel.PDPage();
                doc.addPage(page);
                content = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page);
                y = 750;
            }
            content.setNonStrokingColor(1, 1, 1);
            content.addRect(50, y - 15, 500, 25);
            content.fill();
            content.setNonStrokingColor(0, 0, 0);

            content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.beginText();
            content.newLineAtOffset(60, y - 8);
            content.showText("PATRIMONIO BRUTO TOTAL:  S/ " + String.format("%,.2f", (valorTotalInventario + totalIngresos)));
            content.endText();

            content.close();
            doc.save("Reporte_Contable.pdf");
        }
    }

    public void generarPDFHistorialPagos() throws IOException {
        try (org.apache.pdfbox.pdmodel.PDDocument doc = new org.apache.pdfbox.pdmodel.PDDocument()) {
            org.apache.pdfbox.pdmodel.PDPage page = new org.apache.pdfbox.pdmodel.PDPage();
            doc.addPage(page);

            try (org.apache.pdfbox.pdmodel.PDPageContentStream content = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page)) {
                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                content.beginText();
                content.newLineAtOffset(50, 750);
                content.showText("HISTORIAL DE PAGOS AL PERSONAL");
                content.endText();

                int y = 700;
                content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.COURIER_BOLD), 10);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(String.format("%-12s %-10s %-25s %-10s", "FECHA", "HORA", "EMPLEADO", "MONTO"));
                content.endText();

                y -= 15;
                content.moveTo(50, y + 10);
                content.lineTo(550, y + 10);
                content.stroke();

                java.io.File archivoPagos = new java.io.File("pagos.csv");
                if (archivoPagos.exists()) {
                    try (BufferedReader br = new BufferedReader(new FileReader(archivoPagos))) {
                        String linea;
                        br.readLine();
                        content.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.COURIER), 10);

                        while ((linea = br.readLine()) != null) {
                            if (y < 50) {
                                break;
                            }
                            String[] d = linea.split(";");
                            if (d.length >= 6) {
                                String texto = String.format("%-12s %-10s %-25s S/%-8s", d[0], d[1], d[3], d[5]);
                                content.beginText();
                                content.newLineAtOffset(50, y);
                                content.showText(texto);
                                content.endText();
                                y -= 15;
                            }
                        }
                    }
                } else {
                    content.beginText();
                    content.newLineAtOffset(50, y);
                    content.showText("No hay historial de pagos registrado.");
                    content.endText();
                }
            }
            doc.save("Historial_Pagos.pdf");
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
                String dir = (c.getDireccion() == null) ? "Sin Dirección" : c.getDireccion();
                String linea = String.join(",", c.getDni(), c.getNombre(), c.getApellido(), dir);
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando clientes: " + e.getMessage());
        }

        // Guardar Alquileres
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("alquileres.txt"))) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            for (Alquiler a : alquileres) {
                String idsProductos = "";
                for (Producto p : a.getProductos()) {
                    idsProductos += p.getIdProducto() + ";";
                }
                if (!idsProductos.isEmpty()) {
                    idsProductos = idsProductos.substring(0, idsProductos.length() - 1);
                }

                String fechaStr = (a.getFecha() != null) ? sdf.format(a.getFecha()) : sdf.format(new java.util.Date());

                String linea = String.join(",",
                        String.valueOf(a.getIdAlquiler()),
                        a.getCliente().getDni(),
                        String.valueOf(a.getPrecioPorDia()),
                        String.valueOf(a.getDias()),
                        idsProductos,
                        fechaStr
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
                if (datos.length >= 3) {
                    String dni = datos[0];
                    String nom = datos[1];
                    String ape = datos[2];
                    String dir = (datos.length >= 4) ? datos[3] : "Sin Dirección";

                    clientes.add(new Cliente(dni, nom, ape, dir));
                }
            }
        } catch (IOException e) {
            System.err.println("Info: 'clientes.txt' no encontrado.");
        }

        // Cargar Alquileres
        try (BufferedReader br = new BufferedReader(new FileReader("alquileres.txt"))) {
            String linea;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5) {
                    int id = Integer.parseInt(datos[0]);
                    Cliente cliente = buscarClientePorDNI(datos[1]);
                    double precioDia = Double.parseDouble(datos[2]);
                    int dias = Integer.parseInt(datos[3]);
                    String[] idsProductos = datos[4].split(";");

                    java.util.Date fecha = new java.util.Date();
                    if (datos.length >= 6) {
                        try {
                            fecha = sdf.parse(datos[5]);
                        } catch (Exception e) {
                        }
                    }

                    List<Producto> productosAlquilados = new ArrayList<>();
                    for (String idProd : idsProductos) {
                        Producto p = inventario.buscarProducto(Integer.parseInt(idProd));
                        if (p != null) {
                            productosAlquilados.add(p);
                        }
                    }

                    if (cliente != null) {
                        alquileres.add(new Alquiler(id, cliente, productosAlquilados, precioDia, dias, fecha));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Info: Error al cargar 'alquileres.txt'. " + e.getMessage());
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
