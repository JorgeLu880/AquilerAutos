package Controller;

import Dao.DaoTrabajador;
import Model.Trabajador;
import java.util.List;

public class ControlTrabajador {
    private DaoTrabajador daoTrabajador;
    
    public ControlTrabajador() {
        daoTrabajador = new DaoTrabajador();
    }
    
    // Métodos para la lógica de negocio
    
    public int registrarTrabajador(Trabajador trabajador) {
        // Validaciones adicionales podrían ir aquí antes de llamar al DAO
        return daoTrabajador.crearTrabajador(trabajador);
    }
    
    public boolean actualizarTrabajador(Trabajador trabajador) {
        // Validar que el trabajador exista antes de actualizar
        if (daoTrabajador.obtenerTrabajadorPorId(trabajador.getIdtrabajador()) == null) {
            return false;
        }
        return daoTrabajador.actualizarTrabajador(trabajador);
    }
    
    public boolean eliminarTrabajador(int idTrabajador) {
        // Verificar existencia antes de eliminar
        if (daoTrabajador.obtenerTrabajadorPorId(idTrabajador) == null) {
            return false;
        }
        return daoTrabajador.eliminarTrabajador(idTrabajador);
    }
    
    public Trabajador buscarTrabajador(int idTrabajador) {
        return daoTrabajador.obtenerTrabajadorPorId(idTrabajador);
    }
    
    public Trabajador buscarTrabajadorPorDocumento(String numeroDocumento) {
        // Implementación adicional si se necesita buscar por documento
        // Esto requeriría un nuevo método en el DAO
        return null;
    }
    
    public List<Trabajador> listarTrabajadores() {
        return daoTrabajador.obtenerTodosTrabajadores();
    }
    
    public List<Trabajador> listarTrabajadoresActivos() {
        // Implementación adicional para filtrar solo trabajadores activos
        // Esto requeriría un nuevo método en el DAO
        return null;
    }
    
    public Trabajador autenticar(String usuario, String contraseña) {
        // Validaciones adicionales de formato podrían ir aquí
        if (usuario == null || usuario.trim().isEmpty() || 
            contraseña == null || contraseña.trim().isEmpty()) {
            return null;
        }
        return daoTrabajador.autenticar(usuario, contraseña);
    }
    
    public boolean cambiarEstadoTrabajador(int idTrabajador, String nuevoEstado) {
        // Implementación para cambiar solo el estado de un trabajador
        Trabajador trabajador = daoTrabajador.obtenerTrabajadorPorId(idTrabajador);
        if (trabajador != null) {
            trabajador.setEstado(nuevoEstado);
            return daoTrabajador.actualizarTrabajador(trabajador);
        }
        return false;
    }
    
    public boolean cambiarContraseña(int idTrabajador, String nuevaContraseña) {
        // Implementación para cambiar solo la contraseña
        Trabajador trabajador = daoTrabajador.obtenerTrabajadorPorId(idTrabajador);
        if (trabajador != null) {
            trabajador.setContraseña(nuevaContraseña);
            return daoTrabajador.actualizarTrabajador(trabajador);
        }
        return false;
    }
}