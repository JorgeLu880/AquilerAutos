package Model;

import java.sql.Date;

public class Cliente extends Persona {

    private int idcliente;
    private Date fechaRegistro;

    public Cliente() {
    }

    // Constructor
    public Cliente(int idpersona, String tipoDocumento, String numeroDocumento, String nombre,
            String apellido, String telefono, String email, Date fechaNacimiento,
            int idcliente, Date fechaRegistro) {
        super(idpersona, tipoDocumento, numeroDocumento, nombre, apellido, telefono, email, fechaNacimiento);
        this.idcliente = idcliente;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
