package clases;

public class Producto {

    private int idProducto;
    private String nomProducto;
    private String catProducto;

    public Producto() {
    }

    public Producto(int idProducto, String nomProducto, String catProducto) {
        this.idProducto = idProducto;
        this.nomProducto = nomProducto;
        this.catProducto = catProducto;
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

}
