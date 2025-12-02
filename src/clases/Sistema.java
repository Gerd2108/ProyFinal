/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import interfaces.Rol;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Sistema {

    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private Inventario inventario = new Inventario();
    private ArrayList<Alquiler> alquileres = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<>();

    public Sistema() {
        cargarDatos();

        if (usuarios.isEmpty()) {
            usuarios.add(new Usuario(1, "12345678", "María", "Pérez", "admin1", new Administrador()));
            usuarios.add(new Usuario(2, "87654321", "Juan", "Lopez", "trabajador1", new Trabajador()));
            usuarios.add(new Usuario(3, "11223344", "Ana", "Torres", "invitado1", new Invitado()));
            usuarios.add(new Usuario(4, "74769138", "Maria", "Luna", "secretaria1", new Secretaria()));
            usuarios.add(new Usuario(5, "99887766", "Carlos", "Ruiz", "encargado1", new Encargado()));
            usuarios.add(new Usuario(6, "66778899", "Luisa", "Mendez", "contadora1", new Contadora()));
        }

        if (clientes.isEmpty()) {
            clientes.add(new Cliente("99999999", "Cliente", "General"));
        }
    }

    //Cabecera para PDFs
    public void agregarCabecera(PDDocument doc, PDPageContentStream content, String titulo, String nombreUsuario) throws IOException {
        try {
            java.io.InputStream is = getClass().getResourceAsStream("/media/logohd.jpg");
            if (is != null) {
                PDImageXObject logo = PDImageXObject.createFromByteArray(doc, is.readAllBytes(), "logo");
                content.drawImage(logo, 40, 700, 80, 60);
            }
        } catch (Exception e) {
        }

        content.beginText();
        content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
        content.newLineAtOffset(130, 740);
        content.showText(titulo);
        content.endText();

        content.beginText();
        content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        content.newLineAtOffset(130, 725);
        content.showText("DAL ESTRUCTURAS S.A.C.  |  RUC: 20550267005");
        content.endText();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", new Locale("es", "PE"));
        String fechaActual = sdf.format(new java.util.Date());

        content.beginText();
        content.newLineAtOffset(130, 710);
        content.showText("Generado por: " + nombreUsuario);
        content.endText();

        content.beginText();
        content.newLineAtOffset(400, 710);
        content.showText("Fecha: " + fechaActual);
        content.endText();

        content.moveTo(40, 690);
        content.lineTo(570, 690);
        content.stroke();
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
        return usuarios.add(nuevoUsuario);
    }

    public boolean modificarUsuario(String dni, Usuario usuarioModificado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getDni().equals(dni)) {
                usuarios.set(i, usuarioModificado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarUsuario(String dni) {
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
        return usuarios.size() + 1;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    // GESTIÓN DE PRODUCTOS E INVENTARIO
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

    public boolean eliminarProducto(int idProducto) {
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

    // GESTIÓN DE ALQUILERES Y DEVOLUCIONES
    public void agregarAlquiler(Alquiler alquiler) {
        this.alquileres.add(alquiler);
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

        // Aumentar Stock
        for (Producto p : alquiler.getProductos()) {
            Producto productoEnInventario = inventario.buscarProducto(p.getIdProducto());
            if (productoEnInventario != null) {
                productoEnInventario.aumentarStock(1);
            }
        }

        // Eliminar Alquiler de la lista activa
        boolean eliminado = alquileres.remove(alquiler);

        // Guardar cambios
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

    // ASISTENCIAS Y PAGOS
    public void registrarAsistencia(Usuario usuario, String tipo) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", new Locale("es", "PE"));
        String fechaHora = sdf.format(new java.util.Date());

        String registro = tipo + " - " + fechaHora;

        usuario.getAsistencia().add(registro);
    }

    public java.util.List<String> obtenerRegistrosAsistencia(Usuario usuario) {
        return usuario.getAsistencia();
    }

    public void generarPDFAsistenciaPersonal(Usuario u, String rutaArchivo) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            agregarCabecera(doc, content, "CONSTANCIA DE ASISTENCIA", u.getNombre() + " " + u.getApellido());
            int y = 650;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("DATOS DEL COLABORADOR:");
            content.endText();
            y -= 20;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);
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
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("REGISTRO DE MARCAS:");
            content.endText();
            y -= 20;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 10);
            int contador = 0;
            for (String reg : u.getAsistencia()) {
                if (y < 100) {
                    break;
                }
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("• " + reg);
                content.endText();
                y -= 15;
                contador++;
            }
            y -= 30;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE), 10);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("Total de registros: " + contador);
            content.endText();
            content.close();
            doc.save(rutaArchivo);
        }
    }

    public void generarPDFReciboHonorarios(Usuario u, String rutaArchivo) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            agregarCabecera(doc, content, "RECIBO POR HONORARIOS ELECTRÓNICO", u.getNombre() + " " + u.getApellido());
            int y = 650;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
            content.beginText();
            content.newLineAtOffset(200, y);
            content.showText("Nro: E001-" + (1000 + u.getIdUsuario()));
            content.endText();
            y -= 40;
            int asistencias = u.getAsistencia().size();
            double monto = (asistencias > 0) ? asistencias * 50.00 : 1025.00;
            if (monto > 5000) {
                monto = 5000;
            }
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("CONCEPTO:");
            content.endText();
            y -= 15;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("Por servicios prestados como " + u.getRol().getNombreRol());
            content.endText();
            y -= 40;
            double ret = monto * 0.08;
            double neto = monto - ret;
            content.moveTo(50, y + 10);
            content.lineTo(300, y + 10);
            content.stroke();
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText(String.format("Total: S/ %8.2f", monto));
            content.endText();
            y -= 20;
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText(String.format("Retención: S/ %8.2f", ret));
            content.endText();
            y -= 20;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 11);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText(String.format("NETO: S/ %8.2f", neto));
            content.endText();
            content.close();
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

            SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
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
        } catch (IOException e) {
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

    public void generarPDFContable(String nombreUsuario) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);

            agregarCabecera(doc, content, "REPORTE CONTABLE INTEGRAL", nombreUsuario);

            int y = 650;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "PE"));

            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("1. INGRESOS (ALQUILERES)");
            content.endText();
            y -= 20;

            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 8);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText(String.format("%-4s %-20s %-10s %-10s %-9s %-9s", "ID", "CLIENTE", "FECHA", "SUBTOT.", "IGV", "TOTAL"));
            content.endText();
            y -= 15;
            content.moveTo(50, y + 10);
            content.lineTo(550, y + 10);
            content.stroke();

            double totalIngresos = 0;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 8);

            for (Alquiler a : alquileres) {
                if (y < 50) {
                    content.close();
                    page = new PDPage();
                    doc.addPage(page);
                    content = new PDPageContentStream(doc, page);
                    agregarCabecera(doc, content, "REPORTE CONTABLE (CONT.)", nombreUsuario);
                    y = 650;
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 8);
                }
                double total = a.calcularTotal();
                totalIngresos += total;

                String cliente = a.getCliente().getNombre() + " " + a.getCliente().getApellido();
                if (cliente.length() > 18) {
                    cliente = cliente.substring(0, 18);
                }

                String fecha = (a.getFecha() != null) ? sdf.format(a.getFecha()) : "--/--/----";

                String linea = String.format("%-4d %-20s %-10s %9.2f %9.2f %9.2f",
                        a.getIdAlquiler(), cliente, fecha, a.getSubtotal(), a.getIGV(), total);

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
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
            content.beginText();
            content.newLineAtOffset(350, y);
            content.showText(String.format("TOTAL INGRESOS: S/ %.2f", totalIngresos));
            content.endText();
            y -= 40;

            if (y < 100) {
                content.close();
                page = new PDPage();
                doc.addPage(page);
                content = new PDPageContentStream(doc, page);
                agregarCabecera(doc, content, "REPORTE CONTABLE (CONT.)", nombreUsuario);
                y = 650;
            }
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("2. VALORIZACIÓN DE INVENTARIO (ACTIVOS)");
            content.endText();
            y -= 20;

            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 8);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText(String.format("%-4s %-25s %-15s %-10s %-6s %-12s", "ID", "PRODUCTO", "CATEGORÍA", "COSTO U.", "STOCK", "VALOR TOTAL"));
            content.endText();
            y -= 15;
            content.moveTo(50, y + 10);
            content.lineTo(550, y + 10);
            content.stroke();

            double valorTotalInventario = 0;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 8);

            for (Producto p : inventario.getListaProductos()) {
                if (y < 50) {
                    content.close();
                    page = new PDPage();
                    doc.addPage(page);
                    content = new PDPageContentStream(doc, page);
                    agregarCabecera(doc, content, "REPORTE CONTABLE (CONT.)", nombreUsuario);
                    y = 650;
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 8);
                }
                double valorItem = p.getPrecio() * p.getStock();
                valorTotalInventario += valorItem;
                String nombre = p.getNomProducto().length() > 23 ? p.getNomProducto().substring(0, 23) : p.getNomProducto();

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
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
            content.beginText();
            content.newLineAtOffset(300, y);
            content.showText(String.format("TOTAL ACTIVOS: S/ %.2f", valorTotalInventario));
            content.endText();

            y -= 30;
            if (y < 50) {
                content.close();
                page = new PDPage();
                doc.addPage(page);
                content = new PDPageContentStream(doc, page);
                agregarCabecera(doc, content, "RESUMEN", nombreUsuario);
                y = 650;
            }

            content.setNonStrokingColor(1, 1, 1);
            content.addRect(50, y - 15, 500, 25);
            content.fill();
            content.setNonStrokingColor(0, 0, 0);
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.beginText();
            content.newLineAtOffset(60, y - 8);
            content.showText("PATRIMONIO BRUTO TOTAL:  S/ " + String.format("%,.2f", (valorTotalInventario + totalIngresos)));
            content.endText();

            content.close();
            doc.save(new File(obtenerCarpetaReportes(), "Reporte_Contable.pdf"));
        }
    }

    public void generarPDFHistorialPagos(String nombreUsuario) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);

            agregarCabecera(doc, content, "HISTORIAL DE PAGOS A PERSONAL", nombreUsuario);
            int y = 650;

            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 10);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText(String.format("%-12s %-10s %-25s %-10s", "FECHA", "HORA", "EMPLEADO", "MONTO"));
            content.endText();
            y -= 15;
            content.moveTo(50, y + 10);
            content.lineTo(550, y + 10);
            content.stroke();

            java.io.File f = new java.io.File("pagos.csv");
            if (f.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String linea;
                    br.readLine();
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 10);
                    while ((linea = br.readLine()) != null) {
                        if (y < 50) {
                            content.close();
                            page = new PDPage();
                            doc.addPage(page);
                            content = new PDPageContentStream(doc, page);
                            agregarCabecera(doc, content, "HISTORIAL PAGOS (CONT.)", nombreUsuario);
                            y = 650;
                            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 10);
                        }
                        String[] d = linea.split(";");
                        if (d.length >= 6) {
                            content.beginText();
                            content.newLineAtOffset(50, y);
                            content.showText(String.format("%-12s %-10s %-25s S/%-8s", d[0], d[1], d[3], d[5]));
                            content.endText();
                            y -= 15;
                        }
                    }
                }
            } else {
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("No hay historial registrado.");
                content.endText();
            }
            content.close();
            doc.save(new File(obtenerCarpetaReportes(), "Historial_Pagos.pdf"));
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
        }
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

    public void generarPDFGlobal(java.io.File archivo, String nombreUsuario) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            agregarCabecera(doc, content, "HISTORIAL GENERAL DE ALQUILERES", nombreUsuario);
            int y = 650;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 9);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText(String.format("%-5s %-12s %-35s %-12s", "ID", "FECHA", "CLIENTE", "TOTAL"));
            content.endText();
            y -= 10;
            content.moveTo(50, y);
            content.lineTo(550, y);
            content.stroke();
            y -= 15;
            double totalGeneral = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 9);
            for (Alquiler a : alquileres) {
                if (y < 50) {
                    content.close();
                    page = new PDPage();
                    doc.addPage(page);
                    content = new PDPageContentStream(doc, page);
                    agregarCabecera(doc, content, "HISTORIAL (CONT.)", nombreUsuario);
                    y = 650;
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 9);
                }
                double monto = a.calcularTotal();
                totalGeneral += monto;
                String fecha = (a.getFecha() != null) ? sdf.format(a.getFecha()) : "--/--/----";
                String cli = a.getCliente().getNombre() + " " + a.getCliente().getApellido();
                if (cli.length() > 33) {
                    cli = cli.substring(0, 30) + "...";
                }
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(String.format("%-5d %-12s %-35s S/%9.2f", a.getIdAlquiler(), fecha, cli, monto));
                content.endText();
                y -= 15;
            }
            y -= 10;
            content.moveTo(50, y);
            content.lineTo(550, y);
            content.stroke();
            y -= 20;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.beginText();
            content.newLineAtOffset(300, y);
            content.showText("TOTAL HISTÓRICO: S/ " + String.format("%,.2f", totalGeneral));
            content.endText();
            content.close();
            doc.save(archivo);
        }
    }

    // REPORTE DIARIO
    public void generarPDFCierreCaja(Usuario usuario, String rutaArchivo) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);

            agregarCabecera(doc, content, "REPORTE DIARIO DE CAJA", usuario.getNombre() + " " + usuario.getApellido());

            int y = 650;
            SimpleDateFormat sdfHoy = new SimpleDateFormat("dd/MM/yyyy");
            String hoy = sdfHoy.format(new java.util.Date());

            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("FECHA DE CIERRE: " + hoy);
            content.endText();
            y -= 30;

            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 10);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText(String.format("%-5s %-25s %-10s %-10s", "ID", "CLIENTE", "HORA", "TOTAL"));
            content.endText();
            y -= 15;
            content.moveTo(50, y + 10);
            content.lineTo(550, y + 10);
            content.stroke();

            double totalDia = 0;
            int transacciones = 0;
            SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 10);

            for (Alquiler a : alquileres) {
                if (a.getFecha() != null && sdfHoy.format(a.getFecha()).equals(hoy)) {
                    if (y < 50) {
                        content.close();
                        page = new PDPage();
                        doc.addPage(page);
                        content = new PDPageContentStream(doc, page);
                        agregarCabecera(doc, content, "REPORTE DIARIO (CONT.)", usuario.getNombre());
                        y = 650;
                        content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 10);
                    }
                    double monto = a.calcularTotal();
                    totalDia += monto;
                    transacciones++;

                    String cli = a.getCliente().getNombre() + " " + a.getCliente().getApellido();
                    if (cli.length() > 23) {
                        cli = cli.substring(0, 20) + "...";
                    }

                    String linea = String.format("%-5d %-25s %-10s S/%8.2f", a.getIdAlquiler(), cli, sdfHora.format(a.getFecha()), monto);
                    content.beginText();
                    content.newLineAtOffset(50, y);
                    content.showText(linea);
                    content.endText();
                    y -= 15;
                }
            }

            y -= 20;
            content.moveTo(50, y + 10);
            content.lineTo(550, y + 10);
            content.stroke();
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("Transacciones: " + transacciones);
            content.endText();
            content.beginText();
            content.newLineAtOffset(300, y);
            content.showText(String.format("TOTAL DEL DÍA: S/ %.2f", totalDia));
            content.endText();

            content.close();
            doc.save(rutaArchivo);
        }

    }

    public void generarPDFInventario(java.io.File archivo, Usuario usuario) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);

            String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();

            agregarCabecera(doc, content, "REPORTE GENERAL DE INVENTARIO", nombreCompleto);

            int y = 650;
            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD), 10);
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText(String.format("%-5s %-35s %-15s %-10s %-10s", "ID", "PRODUCTO", "CATEGORÍA", "PRECIO", "STOCK"));
            content.endText();
            y -= 10;
            content.moveTo(50, y);
            content.lineTo(550, y);
            content.stroke();
            y -= 15;

            content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 10);
            for (Producto p : inventario.getListaProductos()) {
                if (y < 50) {
                    content.close();
                    page = new PDPage();
                    doc.addPage(page);
                    content = new PDPageContentStream(doc, page);
                    agregarCabecera(doc, content, "INVENTARIO (CONT.)", nombreCompleto);
                    y = 650;
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 10);
                }

                String nom = p.getNomProducto().length() > 35 ? p.getNomProducto().substring(0, 32) + "..." : p.getNomProducto();
                String cat = p.getCatProducto().length() > 15 ? p.getCatProducto().substring(0, 13) + ".." : p.getCatProducto();

                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(String.format("%-5d %-35s %-15s S/%-9.2f %-10d", p.getIdProducto(), nom, cat, p.getPrecio(), p.getStock()));
                content.endText();

                if (p.getStock() < 5) {
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD_OBLIQUE), 10);
                    content.beginText();
                    content.newLineAtOffset(520, y);
                    content.showText("(!)");
                    content.endText();
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 10);
                }
                y -= 15;
            }
            content.close();
            doc.save(archivo);
        }
    }

    public File obtenerCarpetaReportes() {
        File carpeta = new File("Reportes");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
        return carpeta;
    }
}
