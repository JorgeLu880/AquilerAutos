package Model;

public class Vehiculo {

    private int idvehiculo;
    private Categoria categoria;
    private String placa;
    private String marca;
    private String descripcion;
    private String modelo;
    private double tarifaDiaria;
    private boolean disponibilidad;

    public Vehiculo() {
    }
    

    // Constructor
    public Vehiculo(int idvehiculo, Categoria categoria, String placa, String marca,
            String descripcion, String modelo, double tarifaDiaria, boolean disponibilidad) {
        this.idvehiculo = idvehiculo;
        this.categoria = categoria;
        this.placa = placa;
        this.marca = marca;
        this.descripcion = descripcion;
        this.modelo = modelo;
        this.tarifaDiaria = tarifaDiaria;
        this.disponibilidad = disponibilidad;
    }

    // Getters y Setters
    public int getIdvehiculo() {
        return idvehiculo;
    }

    public void setIdvehiculo(int idvehiculo) {
        this.idvehiculo = idvehiculo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public void setTarifaDiaria(double tarifaDiaria) {
        this.tarifaDiaria = tarifaDiaria;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
