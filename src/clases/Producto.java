package clases;

public class Producto {

    private int idProducto;
    private String nomProducto;
    private String catProducto;
    private double precio;
    private int stock;

    public Producto() {
    }

    public Producto(int idProducto, String nomProducto, String catProducto, double precio, int stock) {
        this.idProducto = idProducto;
        this.nomProducto = nomProducto;
        this.catProducto = catProducto;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public String getCatProducto() {
        return catProducto;
    }

    public void setCatProducto(String catProducto) {
        this.catProducto = catProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean hayStockDisponible(int cantidad) {
        return this.stock >= cantidad;
    }

    public void reducirStock(int cantidad) {
        this.stock -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        this.stock += cantidad;
    }
}
