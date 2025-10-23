package clases;

import interfaces.Operacion;
import java.util.List;

public class Alquiler implements Operacion {

    private int idAlquiler;
    private Usuario usuario;
    private List<Producto> productos;
    private double precioPorDia;
    private int dias;

    public Alquiler(int idAlquiler, Usuario usuario, List<Producto> productos, double precioPorDia, int dias) {
        this.idAlquiler = idAlquiler;
        this.usuario = usuario;
        this.productos = productos;
        this.precioPorDia = precioPorDia;
        this.dias = dias;
    }

    @Override
    public double calcularTotal() {
        return productos.size() * precioPorDia * dias;
    }

    @Override
    public void mostrarResumen() {
        System.out.println("Alquiler de " + usuario.getNombre() + ": " + calcularTotal());
    }
}
