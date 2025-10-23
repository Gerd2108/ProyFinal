package clases;

import java.util.ArrayList;

public class Inventario {

    private ArrayList<Producto> listaProductos = new ArrayList<>();

    public void mostrarInventario() {
        for (Producto p : listaProductos) {
            System.out.println("ID: " + p.getIdProducto() + " | Nombre: " + p.getNomProducto() + " | Categoría: " + p.getCatProducto());
        }
    }

    public void agregarProducto(Producto p) {
        listaProductos.add(p);
    }

    public void eliminarProducto(int id) {
        listaProductos.removeIf(p -> p.getIdProducto() == id);
    }

    public Producto buscarProducto(int id) {
        for (Producto p : listaProductos) {
            if (p.getIdProducto() == id) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }
}
