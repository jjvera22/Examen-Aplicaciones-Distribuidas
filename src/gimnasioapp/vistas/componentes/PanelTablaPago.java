package gimnasioapp.vistas.componentes;

import gimnasioapp.controladores.ControladorPago;
import gimnasioapp.modelos.Pago;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelTablaPago extends JPanel {
    private JTable tablaPagos;
    private DefaultTableModel modelo;

    public PanelTablaPago(ControladorPago controlador) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Pagos Registrados"));

        modelo = new DefaultTableModel(new Object[]{"ID", "Membresía", "Fecha", "Monto", "Método", "Observaciones"}, 0);
        tablaPagos = new JTable(modelo);
        add(new JScrollPane(tablaPagos), BorderLayout.CENTER);
    }

    public void agregarPago(Pago pago) {
        modelo.addRow(new Object[]{
                modelo.getRowCount() + 1,
                pago.getIdMembresia(),
                pago.getFechaPago(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getObservaciones()
        });
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
    }

    public void cargarPagos(List<Object[]> pagos) {
        limpiarTabla();
        for (Object[] pago : pagos) {
            modelo.addRow(pago);
        }
    }

    public JTable getTablaPagos() {
        return tablaPagos;
    }
}
