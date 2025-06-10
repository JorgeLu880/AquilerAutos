
package Model;

import java.sql.Date;

public class Pago {
    private int idpago;
    private Reserva reserva;
    private Date fecha;
    private double monto;
    private String metodo;
    private double IGV;
    private double total;
    private String tipoPago;

    public Pago() {
    }

    // Constructor
    public Pago(int idpago, Reserva reserva, Date fecha, double monto, 
               String metodo, double IGV, double total, String tipoPago) {
        this.idpago = idpago;
        this.reserva = reserva;
        this.fecha = fecha;
        this.monto = monto;
        this.metodo = metodo;
        this.IGV = IGV;
        this.total = total;
        this.tipoPago = tipoPago;
    }

    // Getters y Setters
    public int getIdpago() {
        return idpago;
    }

    public void setIdpago(int idpago) {
        this.idpago = idpago;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public double getIGV() {
        return IGV;
    }

    public void setIGV(double IGV) {
        this.IGV = IGV;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
}
