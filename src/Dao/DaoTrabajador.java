package Dao;

import Model.Persona;
import Model.Trabajador;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoTrabajador {

    private Connection connection;

    public DaoTrabajador() {
        connection = Conexion.getConnection();
    }

    // Método para crear un nuevo trabajador (CREATE)
    public int crearTrabajador(Trabajador trabajador) {
        String sqlPersona = "INSERT INTO Persona (tipoDocumento, numeroDocumento, nombre, "
                + "apellido, telefono, email, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        String sqlTrabajador = "INSERT INTO Trabajador (idpersona, rol, usuario, "
                + "contraseña, estado, salario) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Iniciar transacción
            connection.setAutoCommit(false);

            // Insertar en Persona y obtener el ID generado
            PreparedStatement psPersona = connection.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            psPersona.setString(1, trabajador.getTipoDocumento());
            psPersona.setString(2, trabajador.getNumeroDocumento());
            psPersona.setString(3, trabajador.getNombre());
            psPersona.setString(4, trabajador.getApellido());
            psPersona.setString(5, trabajador.getTelefono());
            psPersona.setString(6, trabajador.getEmail());
            psPersona.setDate(7, new java.sql.Date(trabajador.getFechaNacimiento().getTime()));

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

            // Insertar en Trabajador
            PreparedStatement psTrabajador = connection.prepareStatement(sqlTrabajador, Statement.RETURN_GENERATED_KEYS);
            psTrabajador.setInt(1, idPersonaGenerado);
            psTrabajador.setString(2, trabajador.getRol());
            psTrabajador.setString(3, trabajador.getUsuario());
            psTrabajador.setString(4, trabajador.getContraseña());
            psTrabajador.setString(5, trabajador.getEstado());
            psTrabajador.setDouble(6, trabajador.getSalario());

            int filasTrabajador = psTrabajador.executeUpdate();

            if (filasTrabajador <= 0) {
                connection.rollback();
                return -1;
            }

            // Obtener el ID de trabajador generado
            int idTrabajadorGenerado = -1;
            rs = psTrabajador.getGeneratedKeys();
            if (rs.next()) {
                idTrabajadorGenerado = rs.getInt(1);
            } else {
                connection.rollback();
                return -1;
            }

            connection.commit();
            return idTrabajadorGenerado;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.out.println("Error al crear trabajador: " + e.getMessage());
            return -1;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    // Método para obtener un trabajador por ID (READ)
    public Trabajador obtenerTrabajadorPorId(int idtrabajador) {
        String sql = "SELECT t.*, p.* FROM Trabajador t "
                + "INNER JOIN Persona p ON t.idpersona = p.idpersona "
                + "WHERE t.idtrabajador = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idtrabajador);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearTrabajador(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener trabajador: " + e.getMessage());
        }

        return null;
    }

    // Método para obtener todos los trabajadores (READ ALL)
    public List<Trabajador> obtenerTodosTrabajadores() {
        List<Trabajador> trabajadores = new ArrayList<>();
        String sql = "SELECT t.*, p.* FROM Trabajador t "
                + "INNER JOIN Persona p ON t.idpersona = p.idpersona";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                trabajadores.add(mapearTrabajador(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener todos los trabajadores: " + e.getMessage());
        }

        return trabajadores;
    }

    // Método para actualizar un trabajador (UPDATE)
    public boolean actualizarTrabajador(Trabajador trabajador) {
        String sqlPersona = "UPDATE Persona SET tipoDocumento=?, numeroDocumento=?, nombre=?, "
                + "apellido=?, telefono=?, email=?, fecha_nacimiento=? WHERE idpersona=?";

        String sqlTrabajador = "UPDATE Trabajador SET rol=?, usuario=?, contraseña=?, "
                + "estado=?, salario=? WHERE idtrabajador=?";

        try {
            // Iniciar transacción
            connection.setAutoCommit(false);

            // Actualizar Persona
            PreparedStatement psPersona = connection.prepareStatement(sqlPersona);
            psPersona.setString(1, trabajador.getTipoDocumento());
            psPersona.setString(2, trabajador.getNumeroDocumento());
            psPersona.setString(3, trabajador.getNombre());
            psPersona.setString(4, trabajador.getApellido());
            psPersona.setString(5, trabajador.getTelefono());
            psPersona.setString(6, trabajador.getEmail());
            psPersona.setDate(7, new java.sql.Date(trabajador.getFechaNacimiento().getTime()));
            psPersona.setInt(8, trabajador.getIdpersona());

            int filasPersona = psPersona.executeUpdate();

            if (filasPersona <= 0) {
                connection.rollback();
                return false;
            }

            // Actualizar Trabajador
            PreparedStatement psTrabajador = connection.prepareStatement(sqlTrabajador);
            psTrabajador.setString(1, trabajador.getRol());
            psTrabajador.setString(2, trabajador.getUsuario());
            psTrabajador.setString(3, trabajador.getContraseña());
            psTrabajador.setString(4, trabajador.getEstado());
            psTrabajador.setDouble(5, trabajador.getSalario());
            psTrabajador.setInt(6, trabajador.getIdtrabajador());

            int filasTrabajador = psTrabajador.executeUpdate();

            if (filasTrabajador <= 0) {
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
            System.out.println("Error al actualizar trabajador: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    // Método para eliminar un trabajador (DELETE)
    public boolean eliminarTrabajador(int idtrabajador) {
        // No necesitamos eliminar explícitamente de Persona por el ON DELETE CASCADE
        String sql = "DELETE FROM Trabajador WHERE idtrabajador=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idtrabajador);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar trabajador: " + e.getMessage());
            return false;
        }
    }

    // Método para autenticar trabajador (LOGIN)
    public Trabajador autenticar(String usuario, String contraseña) {
        String sql = "SELECT t.*, p.* FROM Trabajador t "
                + "INNER JOIN Persona p ON t.idpersona = p.idpersona "
                + "WHERE t.usuario = ? AND t.contraseña = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, contraseña);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearTrabajador(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al autenticar trabajador: " + e.getMessage());
        }

        return null;
    }

    // Método auxiliar para mapear ResultSet a objeto Trabajador
    private Trabajador mapearTrabajador(ResultSet rs) throws SQLException {
        // Convertir a java.sql.Date explícitamente
        java.sql.Date fechaNacimiento = rs.getDate("fecha_nacimiento");

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

        // Datos de Trabajador
        Trabajador trabajador = new Trabajador(
                persona.getIdpersona(),
                persona.getTipoDocumento(),
                persona.getNumeroDocumento(),
                persona.getNombre(),
                persona.getApellido(),
                persona.getTelefono(),
                persona.getEmail(),
                persona.getFechaNacimiento(),
                rs.getInt("idtrabajador"),
                rs.getString("rol"),
                rs.getString("usuario"),
                rs.getString("contraseña"),
                rs.getString("estado"),
                rs.getDouble("salario")
        );

        return trabajador;
    }

}
