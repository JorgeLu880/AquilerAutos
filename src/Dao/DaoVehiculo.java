package Dao;

import Model.Categoria;
import Model.Vehiculo;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoVehiculo {

    public List<Vehiculo> listar() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT v.*, c.tipoVehiculo FROM Vehiculo v INNER JOIN Categoria c ON v.idcategoria = c.idcategoria";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria cat = new Categoria(rs.getInt("idcategoria"), rs.getString("tipoVehiculo"));
                Vehiculo v = new Vehiculo(
                        rs.getInt("idvehiculo"),
                        cat,
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("descripcion"),
                        rs.getString("modelo"),
                        rs.getDouble("tarifaDiaria"),
                        rs.getBoolean("disponibilidad")
                );
                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar vehículos: " + e.getMessage());
        }

        return lista;
    }

    public boolean agregar(Vehiculo v) {
        String sql = "INSERT INTO Vehiculo(idcategoria, placa, marca, descripcion, modelo, tarifaDiaria, disponibilidad) VALUES(?,?,?,?,?,?,?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, v.getCategoria().getIdcategoria());
            ps.setString(2, v.getPlaca());
            ps.setString(3, v.getMarca());
            ps.setString(4, v.getDescripcion());
            ps.setString(5, v.getModelo());
            ps.setDouble(6, v.getTarifaDiaria());
            ps.setBoolean(7, v.isDisponibilidad());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar vehículo: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Vehiculo v) {
        String sql = "UPDATE Vehiculo SET idcategoria=?, placa=?, marca=?, descripcion=?, modelo=?, tarifaDiaria=?, disponibilidad=? WHERE idvehiculo=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, v.getCategoria().getIdcategoria());
            ps.setString(2, v.getPlaca());
            ps.setString(3, v.getMarca());
            ps.setString(4, v.getDescripcion());
            ps.setString(5, v.getModelo());
            ps.setDouble(6, v.getTarifaDiaria());
            ps.setBoolean(7, v.isDisponibilidad());
            ps.setInt(8, v.getIdvehiculo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar vehículo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idvehiculo) {
        String sql = "DELETE FROM Vehiculo WHERE idvehiculo=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idvehiculo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar vehículo: " + e.getMessage());
            return false;
        }
    }
}

