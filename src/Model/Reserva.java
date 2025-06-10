package Model;

import java.sql.Date;
import java.sql.Time;

public class Reserva {

    private int idreserva;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private Trabajador trabajador;
    private Date fechaInicio;
    private Date fechaFin;
    private double precio;
    private Time horas;
    private String estado;
    private String tipo;

    public Reserva() {
    }

    // Constructor
    public Reserva(int idreserva, Cliente cliente, Vehiculo vehiculo, Trabajador trabajador,
            Date fechaInicio, Date fechaFin, double precio, Time horas,
            String estado, String tipo) {
        this.idreserva = idreserva;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.trabajador = trabajador;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precio = precio;
        this.horas = horas;
        this.estado = estado;
        this.tipo = tipo;
    }

    // Getters y Setters
    public int getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(int idreserva) {
        this.idreserva = idreserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Time getHoras() {
        return horas;
    }

    public void setHoras(Time horas) {
        this.horas = horas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
