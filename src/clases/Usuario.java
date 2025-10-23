package clases;

import interfaces.Rol;

public class Usuario {

    private int idUsuario;
    private String dni;
    private String nombre;
    private String apellido;
    private String clave;
    private Rol rol;
    

    public Usuario() {}

    public Usuario(int idUsuario, String dni, String nombre, String apellido,String clave, Rol rol) {
        this.idUsuario = idUsuario;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.clave = clave;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Rol getRol() {
        return rol;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void mostrarMenu() {
        rol.mostrarMenu();
    }
}
