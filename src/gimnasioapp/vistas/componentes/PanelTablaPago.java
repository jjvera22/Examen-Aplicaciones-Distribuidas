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
    private final JButton btnEditar = new JButton("✏️ Editar");
    private final JButton btnEliminar = new JButton("🗑️ Eliminar");
    private ControladorPago controlador;

    public PanelTablaPago(ControladorPago controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Pagos Registrados"));

        modelo = new DefaultTableModel(new Object[]{"ID", "Membresía", "Fecha", "Monto", "Método", "Observaciones"}, 0);
        tablaPagos = new JTable(modelo);
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
       
        add(new JScrollPane(tablaPagos), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        btnEditar.addActionListener(e -> {
            Pago p = controlador.obtenerPagoDesdeFila(tablaPagos);
            controlador.actualizarPago(p);
        });
        
        tablaPagos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                
                int fila = tablaPagos.getSelectedRow();
              
                if (fila >= 0) {
                    Pago p = controlador.obtenerPagoDesdeFila(tablaPagos);
                    if (p != null) {
                        controlador.cargarPagoEnFormulario(p);
                    }
                }
            }
        });
                
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
