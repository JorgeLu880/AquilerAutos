package Dao;

import Model.Persona;
import Model.Cliente;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoCliente {

    private Connection connection;

    public DaoCliente() {
        connection = Conexion.getConnection();
    }

    // Método para crear un nuevo cliente (CREATE)
    public int crearCliente(Cliente cliente) {
        String sqlPersona = "INSERT INTO Persona (tipoDocumento, numeroDocumento, nombre, "
                + "apellido, telefono, email, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        String sqlCliente = "INSERT INTO Cliente (idpersona, fecha_registro) VALUES (?, ?)";

        try {
            // Iniciar transacción
            connection.setAutoCommit(false);

            // Insertar en Persona y obtener el ID generado
            PreparedStatement psPersona = connection.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            psPersona.setString(1, cliente.getTipoDocumento());
            psPersona.setString(2, cliente.getNumeroDocumento());
            psPersona.setString(3, cliente.getNombre());
            psPersona.setString(4, cliente.getApellido());
            psPersona.setString(5, cliente.getTelefono());
            psPersona.setString(6, cliente.getEmail());
            psPersona.setDate(7, new java.sql.Date(cliente.getFechaNacimiento().getTime()));

            int filasPersona = psPersona.executeUpdate();

            if (filasPersona <= 0) {
                connection.rollback();
                return -1;
            }

            // Obtener el ID de persona generado
            int idPersonaGenerado = -1;
            ResultSet rs = psPersona.getGeneratedKeys();
            if (rs.next()) {
                idPersonaGenerado = rs.getInt(1);
            } else {
                connection.rollback();
                return -1;
            }

            // Insertar en Cliente
            PreparedStatement psCliente = connection.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
            psCliente.setInt(1, idPersonaGenerado);
            psCliente.setDate(2, new java.sql.Date(cliente.getFechaRegistro().getTime()));

            int filasCliente = psCliente.executeUpdate();

            if (filasCliente <= 0) {
                connection.rollback();
                return -1;
            }

            // Obtener el ID de cliente generado
            int idClienteGenerado = -1;
            rs = psCliente.getGeneratedKeys();
            if (rs.next()) {
                idClienteGenerado = rs.getInt(1);
            } else {
                connection.rollback();
                return -1;
            }

            connection.commit();
            return idClienteGenerado;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.out.println("Error al crear cliente: " + e.getMessage());
            return -1;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    // Método para obtener un cliente por ID (READ)
    public Cliente obtenerClientePorId(int idcliente) {
        String sql = "SELECT c.*, p.* FROM Cliente c "
                + "INNER JOIN Persona p ON c.idpersona = p.idpersona "
                + "WHERE c.idcliente = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idcliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearCliente(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener cliente: " + e.getMessage());
        }

        return null;
    }

    // Método para obtener todos los clientes (READ ALL)
    public List<Cliente> obtenerTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT c.*, p.* FROM Cliente c "
                + "INNER JOIN Persona p ON c.idpersona = p.idpersona";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener todos los clientes: " + e.getMessage());
        }

        return clientes;
    }

    // Método para actualizar un cliente (UPDATE)
    public boolean actualizarCliente(Cliente cliente) {
        String sqlPersona = "UPDATE Persona SET tipoDocumento=?, numeroDocumento=?, nombre=?, "
                + "apellido=?, telefono=?, email=?, fecha_nacimiento=? WHERE idpersona=?";

        String sqlCliente = "UPDATE Cliente SET fecha_registro=? WHERE idcliente=?";

        try {
            // Iniciar transacción
            connection.setAutoCommit(false);

            // Actualizar Persona
            PreparedStatement psPersona = connection.prepareStatement(sqlPersona);
            psPersona.setString(1, cliente.getTipoDocumento());
            psPersona.setString(2, cliente.getNumeroDocumento());
            psPersona.setString(3, cliente.getNombre());
            psPersona.setString(4, cliente.getApellido());
            psPersona.setString(5, cliente.getTelefono());
            psPersona.setString(6, cliente.getEmail());
            psPersona.setDate(7, new java.sql.Date(cliente.getFechaNacimiento().getTime()));
            psPersona.setInt(8, cliente.getIdpersona());

            int filasPersona = psPersona.executeUpdate();

            if (filasPersona <= 0) {
                connection.rollback();
                return false;
            }

            // Actualizar Cliente
            PreparedStatement psCliente = connection.prepareStatement(sqlCliente);
            psCliente.setDate(1, new java.sql.Date(cliente.getFechaRegistro().getTime()));
            psCliente.setInt(2, cliente.getIdcliente());

            int filasCliente = psCliente.executeUpdate();

            if (filasCliente <= 0) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    // Método para eliminar un cliente (DELETE)
    public boolean eliminarCliente(int idcliente) {
        // No necesitamos eliminar explícitamente de Persona por el ON DELETE CASCADE
        String sql = "DELETE FROM Cliente WHERE idcliente=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idcliente);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    // Método auxiliar para mapear ResultSet a objeto Cliente
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        // Convertir a java.sql.Date explícitamente
        java.sql.Date fechaNacimiento = rs.getDate("fecha_nacimiento");
        java.sql.Date fechaRegistro = rs.getDate("fecha_registro");

        // Datos de Persona
        Persona persona = new Persona(
                rs.getInt("idpersona"),
                rs.getString("tipoDocumento"),
                rs.getString("numeroDocumento"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("telefono"),
                rs.getString("email"),
                fechaNacimiento
        );

        // Datos de Cliente
        Cliente cliente = new Cliente(
                persona.getIdpersona(),
                persona.getTipoDocumento(),
                persona.getNumeroDocumento(),
                persona.getNombre(),
                persona.getApellido(),
                persona.getTelefono(),
                persona.getEmail(),
                persona.getFechaNacimiento(),
                rs.getInt("idcliente"),
                fechaRegistro
        );

        return cliente;
    }
}
