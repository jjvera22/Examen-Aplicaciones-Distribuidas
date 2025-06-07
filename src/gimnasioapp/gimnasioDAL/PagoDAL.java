package gimnasioapp.gimnasioDAL;

import gimnasioapp.modelos.Cliente;
import gimnasioapp.modelos.Membresia;
import gimnasioapp.modelos.Pago;
import gimnasioapp.modelos.Plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAL {

    private final Connection conn;

    public PagoDAL(Connection conn) {
        this.conn = conn;
    }

    // Inserta un nuevo pago
    public boolean insertar(Pago pago) {
        String sql = "INSERT INTO pagos (id_membresia, fecha_pago, monto, metodo_pago, observaciones) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pago.getIdMembresia());
            stmt.setString(2, pago.getFechaPago());
            stmt.setDouble(3, pago.getMonto());
            stmt.setString(4, pago.getMetodoPago());
            stmt.setString(5, pago.getObservaciones());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR - insertar] " + e.getMessage());
            return false;
        }
    }

    // Actualiza un pago existente
    public boolean actualizar(Pago pago) {
        String sql = "UPDATE pagos SET id_membresia = ?, fecha_pago = ?, monto = ?, metodo_pago = ?, observaciones = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pago.getIdMembresia());
            stmt.setString(2, pago.getFechaPago());
            stmt.setDouble(3, pago.getMonto());
            stmt.setString(4, pago.getMetodoPago());
            stmt.setString(5, pago.getObservaciones());
            stmt.setInt(6, pago.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR - actualizar] " + e.getMessage());
            return false;
        }
    }

    // Elimina un pago por ID
    public boolean eliminar(int id) {
        String sql = "DELETE FROM pagos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR - eliminar] " + e.getMessage());
            return false;
        }
    }

    // Devuelve una lista con todos los pagos
    public List<Pago> obtenerTodos() {
        List<Pago> lista = new ArrayList<>();
        String sql = """
            SELECT p.id, p.fecha_pago, p.monto, p.metodo_pago, p.observaciones,
                   m.id AS id_membresia,
                   c.nombre AS cliente_nombre,
                   pl.nombre AS plan_nombre
            FROM pagos p
            JOIN membresias m ON p.id_membresia = m.id
            JOIN clientes c ON m.id_cliente = c.id
            JOIN planes pl ON m.id_plan = pl.id
        """;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearPago(rs));
            }

        } catch (SQLException e) {
            System.err.println("[ERROR - obtenerTodos] " + e.getMessage());
        }
        return lista;
    }

    // Devuelve un pago por su ID
    public Pago obtenerPorId(int id) {
        String sql = "SELECT * FROM pagos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPago(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR - obtenerPorId] " + e.getMessage());
        }
        return null;
    }

    // Método auxiliar para mapear datos del ResultSet a un objeto Pago
    private Pago mapearPago(ResultSet rs) throws SQLException {
        // Crear cliente
        Cliente cliente = new Cliente();
        cliente.setNombre(rs.getString("cliente_nombre"));

        // Crear plan
        Plan plan = new Plan();
        plan.setNombre(rs.getString("plan_nombre"));

        // Crear membresía
        Membresia membresia = new Membresia();
        membresia.setId(rs.getInt("id_membresia"));
        membresia.setCliente(cliente);
        membresia.setPlan(plan);

        // Crear pago
        Pago pago = new Pago();
        pago.setId(rs.getInt("id"));
        pago.setFechaPago(rs.getString("fecha_pago"));
        pago.setMonto(rs.getDouble("monto"));
        pago.setMetodoPago(rs.getString("metodo_pago"));
        pago.setObservaciones(rs.getString("observaciones"));
        pago.setMembresia(membresia); // ✅ aquí ya no será null

        return pago;
    }

    
    public List<Object[]> obtenerPagosParaTabla() {
         List<Object[]> filas = new ArrayList<>();

        try {
            List<Pago> pagos = obtenerTodos();

            for (Pago pago : pagos) {
                Membresia membresia = pago.getMembresia();
                Cliente cliente = membresia.getCliente();
                Plan plan = membresia.getPlan();

                String descripcionMembresia = cliente.getNombre() + " - " + plan.getNombre();

                Object[] fila = new Object[] {
                    pago.getId(),
                    descripcionMembresia,
                    pago.getFechaPago(),
                    pago.getMonto(),
                    pago.getMetodoPago(),
                    pago.getObservaciones(),
                };

                filas.add(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filas;
    }
    
    public int obtenerIdMembresiaPorPagoId(int pagoId) {
        String sql = "SELECT id_membresia FROM pagos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pagoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_membresia");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener id_membresia: " + e.getMessage());
        }
        return -1; // o lanzar excepción si prefieres manejarlo así
    }
}
