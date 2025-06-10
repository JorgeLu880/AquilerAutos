
package Model;

import java.sql.Date;

public class Trabajador extends Persona {
    private int idtrabajador;
    private String rol;
    private String usuario;
    private String contraseña;
    private String estado;
    private double salario;
    public Trabajador() {
    }
    
    // Constructor
    public Trabajador(int idpersona, String tipoDocumento, String numeroDocumento, String nombre, 
                     String apellido, String telefono, String email, Date fechaNacimiento,
                     int idtrabajador, String rol, String usuario, String contraseña, 
                     String estado, double salario) {
        super(idpersona, tipoDocumento, numeroDocumento, nombre, apellido, telefono, email, fechaNacimiento);
        this.idtrabajador = idtrabajador;
        this.rol = rol;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.estado = estado;
        this.salario = salario;
    }

    // Getters y Setters
    public int getIdtrabajador() {
        return idtrabajador;
    }

    public void setIdtrabajador(int idtrabajador) {
        this.idtrabajador = idtrabajador;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
}