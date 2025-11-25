package clases;

import interfaces.Operacion;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alquiler implements Operacion {

    private int idAlquiler;
    private Cliente cliente;
    private List<Producto> productos;
    private double precioPorDia;
    private int dias;
    private Date fecha;

    public Alquiler(int idAlquiler, Cliente cliente, List<Producto> productos, double precioPorDia, int dias) {
        this.idAlquiler = idAlquiler;
        this.cliente = cliente;
        this.productos = productos;
        this.precioPorDia = precioPorDia;
        this.dias = dias;
        this.fecha = new Date();
    }

    public Alquiler(int idAlquiler, Cliente cliente, List<Producto> productos, double precioPorDia, int dias, Date fecha) {
        this.idAlquiler = idAlquiler;
        this.cliente = cliente;
        this.productos = productos;
        this.precioPorDia = precioPorDia;
        this.dias = dias;
        this.fecha = fecha;
    }

    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getSubtotal() {
        return this.precioPorDia * this.dias;
    }

    public double getIGV() {
        return getSubtotal() * 0.18;
    }

    @Override
    public double calcularTotal() {
        return getSubtotal() * 1.18;
    }

    @Override
    public void mostrarResumen() {
        System.out.println("Alquiler de " + cliente.getNombre() + ": " + calcularTotal());
    }

    public String generarTextoRecibo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String fechaEmision = (this.fecha != null) ? sdf.format(this.fecha) : sdf.format(new Date());

        java.util.Calendar cal = java.util.Calendar.getInstance();
        if (this.fecha != null) {
            cal.setTime(this.fecha);
        }
        cal.add(java.util.Calendar.DAY_OF_YEAR, this.dias);
        String fechaVencimiento = sdf.format(cal.getTime());

        String separador = "+-----------------------------------------------------------------------------+\n";
        String separadorTabla = "| N° | UNIDAD   | CÓDIGO   | DESCRIPCIÓN                     | CANT. | P. UNIT. | TOTAL    |\n";
        String separadorFino = "|----|----------|----------|---------------------------------|-------|----------|----------|\n";

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("DAL ESTRUCTURAS S.A.C %42s\n", "RUC 20550267005"));
        sb.append("Prol. Jorge Chavez. Mz G Lote 16, Villa El Salvador\n");
        sb.append("LIMA - LIMA - CARABAYLLO\n");
        sb.append("IMPORTADORA Y DISTRIBUIDORA DE MAQUINARIAS\n");
        sb.append(String.format("ventas@dalestructuras.com %34s\n", "Telf. 951638873"));

        sb.append("\n" + separador);
        sb.append(String.format("| %-57s | PROFORMA |\n", ""));
        sb.append(String.format("| %-57s | 0001-%06d |\n", "", this.idAlquiler));

        sb.append(String.format("%-15s %-30s %-18s %s\n", "DOCUMENTO", ": " + this.cliente.getDni(), "FECHA EMISIÓN", ": " + fechaEmision));
        sb.append(String.format("%-15s %-30s %-18s %s\n", "CLIENTE", ": " + this.cliente.getNombre() + " " + this.cliente.getApellido(), "FECHA VENCIMIENTO", ": " + fechaVencimiento));
        sb.append(String.format("%-15s %-30s %-18s %s\n", "DIRECCIÓN", ": SIN DIRECCIÓN", "MONEDA", ": SOLES"));
        sb.append("\n");

        sb.append(separadorTabla);
        sb.append(separadorFino);

        int n = 1;
        double subtotalItems = 0.0;
        for (Producto p : this.productos) {
            double totalItem = p.getPrecio() * 1.00;
            subtotalItems += totalItem;

            String nombreCorto = p.getNomProducto();
            if (nombreCorto.length() > 31) {
                nombreCorto = nombreCorto.substring(0, 31);
            }

            sb.append(String.format("| %-2d | %-8s | %-8s | %-31s | %-5.2f | %-8.2f | %-8.2f |\n",
                    n++, "UNIDAD", "P" + p.getIdProducto(), nombreCorto, 1.00, p.getPrecio(), totalItem));
        }
        sb.append(separadorFino);

        sb.append("\n\n");
        sb.append(String.format("%60s S/ %10.2f\n", "GRAVADO", getSubtotal()));
        sb.append(String.format("%60s S/ %10.2f\n", "I.G.V. 18%", getIGV()));
        sb.append(String.format("%60s S/ %10.2f\n", "TOTAL", calcularTotal()));

        return sb.toString();
    }
}
