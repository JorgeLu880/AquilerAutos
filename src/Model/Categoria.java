package Model;

public class Categoria {

    private int idcategoria;
    private String tipoVehiculo;

    public Categoria() {
    }

    // Constructor
    public Categoria(int idcategoria, String tipoVehiculo) {
        this.idcategoria = idcategoria;
        this.tipoVehiculo = tipoVehiculo;
    }

    // Getters y Setters
    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    @Override
    public String toString() {
        return tipoVehiculo;
    }

}
