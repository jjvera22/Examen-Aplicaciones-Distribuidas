package gimnasioapp.gimnasioDAL;

import gimnasioapp.modelos.Membresia;
import gimnasioapp.modelos.Plan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembresiaDAL {

    private Connection conn;

    public MembresiaDAL(Connection conn) {
        this.conn = conn;
    }

    public boolean insertarMembresia(Membresia m) {
        String sql = "INSERT INTO membresias (id_cliente, id_plan, fecha_inicio, fecha_fin, estado_pago) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, m.getIdCliente());
            stmt.setInt(2, m.getIdPlan());
            stmt.setString(3, m.getFechaInicio());
            stmt.setString(4, m.getFechaFin());
            stmt.setString(5, m.getEstadoPago());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insertando membresía: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarMembresia(Membresia m) {
        String sql = "UPDATE membresias SET id_cliente = ?, id_plan = ?, fecha_inicio = ?, fecha_fin = ?, estado_pago = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, m.getIdCliente());
            stmt.setInt(2, m.getIdPlan());
            stmt.setString(3, m.getFechaInicio());
            stmt.setString(4, m.getFechaFin());
            stmt.setString(5, m.getEstadoPago());
            stmt.setInt(6, m.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando membresía: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarMembresia(int id) {
        String sql = "DELETE FROM membresias WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando membresía: " + e.getMessage());
            return false;
        }
    }

    public List<Membresia> obtenerTodas() {
        List<Membresia> lista = new ArrayList<>();
        String sql = "SELECT * FROM membresias";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Membresia m = new Membresia();
                m.setId(rs.getInt("id"));
                m.setIdCliente(rs.getInt("id_cliente"));
                m.setIdPlan(rs.getInt("id_plan"));
                m.setFechaInicio(rs.getString("fecha_inicio"));
                m.setFechaFin(rs.getString("fecha_fin"));
                m.setEstadoPago(rs.getString("estado_pago"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo membresías: " + e.getMessage());
        }
        return lista;
    }

    public Membresia obtenerPorId(int id) {
        String sql = "SELECT * FROM membresias WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Membresia m = new Membresia();
                m.setId(rs.getInt("id"));
                m.setIdCliente(rs.getInt("id_cliente"));
                m.setIdPlan(rs.getInt("id_plan"));
                m.setFechaInicio(rs.getString("fecha_inicio"));
                m.setFechaFin(rs.getString("fecha_fin"));
                m.setEstadoPago(rs.getString("estado_pago"));
                return m;
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo membresía por ID: " + e.getMessage());
        }
        return null;
    }
	
	public List<Object[]> obtenerMembresiasConNombres() {
    List<Object[]> lista = new ArrayList<>();
    String sql = "SELECT m.id, c.nombre AS cliente, p.nombre AS plan, m.fecha_inicio, m.fecha_fin, m.estado_pago " +
                 "FROM membresias m " +
                 "JOIN clientes c ON m.id_cliente = c.id " +
                 "JOIN planes p ON m.id_plan = p.id";
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            Object[] fila = new Object[]{
                rs.getInt("id"),
                rs.getString("cliente"),
                rs.getString("plan"),
                rs.getString("fecha_inicio"),
                rs.getString("fecha_fin"),
                rs.getString("estado_pago")
            };
            lista.add(fila);
        }
    } catch (SQLException e) {
        System.err.println("Error obteniendo membresías con nombres: " + e.getMessage());
    }
    return lista;
}

	
    // Método corregido con nombre esperado por el controlador
    public Plan obtenerPlanActualPorCliente(int idCliente) {
        Plan plan = null;
        String sql = "SELECT p.id, p.nombre, p.precio, p.duracion_dias " +
                     "FROM membresias m " +
                     "JOIN planes p ON m.id_plan = p.id " +
                     "WHERE m.id_cliente = ? " +
                     "ORDER BY m.fecha_inicio DESC LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    plan = new Plan(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("duracion_dias")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo plan actual del cliente: " + e.getMessage());
        }

        return plan;
    }

    // Métodos no implementados aún
    public boolean insertar(Membresia m) {
		String sql = "INSERT INTO membresias (id_cliente, id_plan, fecha_inicio, fecha_fin, estado_pago) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, m.getIdCliente());
			stmt.setInt(2, m.getIdPlan());
			stmt.setString(3, m.getFechaInicio());
			stmt.setString(4, m.getFechaFin());
			stmt.setString(5, m.getEstadoPago());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error insertando membresía: " + e.getMessage());
			return false;
    }
}


    public boolean actualizar(Membresia m) {
		String sql = "UPDATE membresias SET id_cliente = ?, id_plan = ?, fecha_inicio = ?, fecha_fin = ?, estado_pago = ? WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, m.getIdCliente());
			stmt.setInt(2, m.getIdPlan());
			stmt.setString(3, m.getFechaInicio());
			stmt.setString(4, m.getFechaFin());
			stmt.setString(5, m.getEstadoPago());
			stmt.setInt(6, m.getId());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error actualizando membresía: " + e.getMessage());
			return false;
    }
}


    public boolean eliminar(int id) {
		String sql = "DELETE FROM membresias WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error eliminando membresía: " + e.getMessage());
			return false;
		}
    }
    
    public List<String> obtenerMembresiaDataComboBox() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT m.id, c.nombre AS nombre_cliente, p.nombre AS nombre_plan " +
                    "FROM membresias m " +
                    "JOIN clientes c ON m.id_cliente = c.id " +
                    "JOIN planes p ON m.id_plan = p.id";

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String cliente = rs.getString("nombre_cliente");
                String plan = rs.getString("nombre_plan");

                lista.add(id + " - " + cliente + " - " + plan);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener membresías: " + e.getMessage());
        }

        return lista;
    }

}
