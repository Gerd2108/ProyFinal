package clases;

public class Cliente {

    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String direccion;

    public Cliente(String dni, String nombre, String apellido, String telefono, String correo, String direccion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    public Cliente(String dni, String nombre, String apellido) {
        this(dni, nombre, apellido, "", "", "Sin Dirección");
    }

    public Cliente(String dni, String nombre, String apellido, String direccion) {
        this(dni, nombre, apellido, "", "", direccion);
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
