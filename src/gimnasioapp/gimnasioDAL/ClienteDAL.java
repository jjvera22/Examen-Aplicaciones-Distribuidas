package gimnasioapp.gimnasioDAL;

import gimnasioapp.modelos.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAL {
    private final Connection conn;

    public ClienteDAL(Connection conn) {
        this.conn = conn;
    }

    public boolean insertar(Cliente c) {
        String sql = "INSERT INTO clientes(nombre, cedula, telefono, correo, direccion) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getCedula());
            stmt.setString(3, c.getTelefono());
            stmt.setString(4, c.getCorreo());
            stmt.setString(5, c.getDireccion());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Cliente c) {
        String sql = "UPDATE clientes SET nombre=?, cedula=?, telefono=?, correo=?, direccion=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getCedula());
            stmt.setString(3, c.getTelefono());
            stmt.setString(4, c.getCorreo());
            stmt.setString(5, c.getDireccion());
            stmt.setInt(6, c.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        }
        return lista;
    }

    public Cliente obtenerPorCedula(String cedula) {
        String sql = "SELECT * FROM clientes WHERE cedula = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por cédula: " + e.getMessage());
        }
        return null;
    }

    // Reportes

    public List<Cliente> obtenerClientesInscritos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT c.* FROM clientes c " +
                     "JOIN membresias m ON c.id = m.id_cliente " +
                     "WHERE DATE(m.fecha_fin) >= DATE('now')";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes inscritos: " + e.getMessage());
        }
        return lista;
    }

    public List<Cliente> obtenerClientesRetirados() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT c.* FROM clientes c " +
                     "JOIN membresias m ON c.id = m.id_cliente " +
                     "WHERE DATE(m.fecha_fin) < DATE('now')";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes retirados: " + e.getMessage());
        }
        return lista;
    }

    public List<Cliente> obtenerClientesConFaltaPago() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT c.* FROM clientes c " +
                     "JOIN membresias m ON c.id = m.id_cliente " +
                     "WHERE m.estado_pago = 'Pendiente'";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes con falta de pago: " + e.getMessage());
        }
        return lista;
    }

    // Método auxiliar para mapear un Cliente desde el ResultSet
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setCedula(rs.getString("cedula"));
        c.setTelefono(rs.getString("telefono"));
        c.setCorreo(rs.getString("correo"));
        c.setDireccion(rs.getString("direccion"));
        return c;
    }
}
