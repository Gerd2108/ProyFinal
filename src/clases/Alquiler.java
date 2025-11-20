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

    public Alquiler(int idAlquiler, Cliente cliente, List<Producto> productos, double precioPorDia, int dias) {
        this.idAlquiler = idAlquiler;
        this.cliente = cliente;
        this.productos = productos;
        this.precioPorDia = precioPorDia;
        this.dias = dias;
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
        String fechaActual = sdf.format(new Date());

        java.util.Calendar cal = java.util.Calendar.getInstance();
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

        sb.append(String.format("%-15s %-30s %-18s %s\n", "DOCUMENTO", ": " + this.cliente.getDni(), "FECHA EMISIÓN", ": " + fechaActual));
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

            sb.append(String.format("| %-2d | %-8s | %-8s | %-31s | %-5.2f | %-8.2f | %-8.2f |\n",
                    n++,
                    "UNIDAD",
                    "P" + p.getIdProducto(),
                    p.getNomProducto(),
                    1.00,
                    p.getPrecio(),
                    totalItem
            ));
        }
        sb.append(separadorFino);

        double gravado = getSubtotal();
        double igv = getIGV();
        double totalFinal = calcularTotal();

        sb.append("\n\n");

        sb.append(String.format("SON: (TOTAL EN LETRAS AQUÍ)\n\n"));

        sb.append(String.format("%60s S/ %10.2f\n", "GRAVADO", gravado));
        sb.append(String.format("%60s S/ %10.2f\n", "I.G.V. 18%", igv));
        sb.append(String.format("%60s S/ %10.2f\n", "TOTAL", totalFinal));

        return sb.toString();
    }
}
