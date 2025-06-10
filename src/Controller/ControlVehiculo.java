package Controller;

import Dao.DaoVehiculo;
import Dao.DaoCategoria;
import Model.Categoria;
import Model.Vehiculo;
import java.util.List;

public class ControlVehiculo {

    DaoVehiculo daoVehiculo = new DaoVehiculo();
    DaoCategoria daoCategoria = new DaoCategoria();

    // Listar categorías para JComboBox
    public List<Categoria> obtenerCategorias() {
        return daoCategoria.listar();
    }

    // Agregar nuevo vehículo
    public boolean agregarVehiculo(Vehiculo v) {
        return daoVehiculo.agregar(v);
    }

    // Actualizar vehículo existente
    public boolean actualizarVehiculo(Vehiculo v) {
        return daoVehiculo.actualizar(v);
    }

    // Eliminar vehículo
    public boolean eliminarVehiculo(int id) {
        return daoVehiculo.eliminar(id);
    }

    // Listar todos los vehículos
    public List<Vehiculo> listarVehiculos() {
        return daoVehiculo.listar();
    }
}

