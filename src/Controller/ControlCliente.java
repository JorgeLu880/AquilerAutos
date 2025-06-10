package Controller;

import Dao.DaoCliente;
import Model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ControlCliente {

    private DaoCliente daoCliente;

    public ControlCliente() {
        daoCliente = new DaoCliente();
    }

    // Métodos para la lógica de negocio
    public int registrarCliente(Cliente cliente) {
        // Validaciones adicionales podrían ir aquí antes de llamar al DAO
        return daoCliente.crearCliente(cliente);
    }

    public boolean actualizarCliente(Cliente cliente) {
        // Validar que el cliente exista antes de actualizar
        if (daoCliente.obtenerClientePorId(cliente.getIdcliente()) == null) {
            return false;
        }
        return daoCliente.actualizarCliente(cliente);
    }

    public boolean eliminarCliente(int idCliente) {
        // Verificar existencia antes de eliminar
        if (daoCliente.obtenerClientePorId(idCliente) == null) {
            return false;
        }
        return daoCliente.eliminarCliente(idCliente);
    }

    public Cliente buscarCliente(int idCliente) {
        return daoCliente.obtenerClientePorId(idCliente);
    }

    public Cliente buscarClientePorDocumento(String numeroDocumento) {
        // Implementación adicional si se necesita buscar por documento
        // Esto requeriría un nuevo método en el DAO
        // Ejemplo de implementación básica:
        List<Cliente> clientes = listarClientes();
        for (Cliente cliente : clientes) {
            if (cliente.getNumeroDocumento().equals(numeroDocumento)) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> listarClientes() {
        return daoCliente.obtenerTodosClientes();
    }

    public List<Cliente> listarClientesRecientes() {
        // Implementación adicional para filtrar clientes recientes (ej. últimos 30 días)
        // Esto requeriría un nuevo método en el DAO
        return null;
    }

    // Métodos específicos para Cliente (no existían en Trabajador)
    public List<Cliente> buscarClientesPorNombre(String nombre) {
        // Implementación para buscar clientes por nombre (parcial o completo)
        // Esto requeriría un nuevo método en el DAO
        // Ejemplo básico con filtrado en memoria:
        List<Cliente> clientes = listarClientes();
        List<Cliente> resultados = new ArrayList<>();

        for (Cliente cliente : clientes) {
            if (cliente.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(cliente);
            }
        }
        return resultados;
    }

    public List<Cliente> buscarClientesPorApellido(String apellido) {
        // Similar a buscar por nombre pero para apellido
        List<Cliente> clientes = listarClientes();
        List<Cliente> resultados = new ArrayList<>();

        for (Cliente cliente : clientes) {
            if (cliente.getApellido().toLowerCase().contains(apellido.toLowerCase())) {
                resultados.add(cliente);
            }
        }
        return resultados;
    }
}
