package gimnasioapp.gimnasioDAL;

import gimnasioapp.modelos.Plan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAL {

    private final Connection conn;

    public PlanDAL(Connection conn) {
        this.conn = conn;
    }

    public boolean insertar(Plan plan) {
        String sql = "INSERT INTO planes (nombre, precio, duracion_dias) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plan.getNombre());
            stmt.setDouble(2, plan.getPrecio());
            stmt.setInt(3, plan.getDuracionDias());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar plan: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Plan plan) {
        String sql = "UPDATE planes SET nombre = ?, precio = ?, duracion_dias = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plan.getNombre());
            stmt.setDouble(2, plan.getPrecio());
            stmt.setInt(3, plan.getDuracionDias());
            stmt.setInt(4, plan.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar plan: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM planes WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar plan: " + e.getMessage());
            return false;
        }
    }

    public List<Plan> obtenerTodos() {
        List<Plan> lista = new ArrayList<>();
        String sql = "SELECT * FROM planes ORDER BY nombre ASC";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Plan p = new Plan();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDuracionDias(rs.getInt("duracion_dias"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al obtener planes: " + e.getMessage());
        }
        return lista;
    }

    public Plan obtenerPorNombre(String nombre) {
        String sql = "SELECT * FROM planes WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Plan p = new Plan();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDuracionDias(rs.getInt("duracion_dias"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al obtener plan por nombre: " + e.getMessage());
        }
        return null;
    }

}
