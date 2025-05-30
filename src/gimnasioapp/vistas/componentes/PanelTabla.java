package gimnasioapp.vistas.componentes;

import gimnasioapp.controladores.ControladorMembresia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelTabla extends JPanel {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnEditar;
    private JButton btnEliminar;

    public PanelTabla(ControladorMembresia controlador) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Listado de Membresías"));

        // Definir modelo de la tabla
        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Cliente", "Plan", "Inicio", "Fin", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No permitir edición directa en tabla
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Getters para acceso desde el controlador
    public JTable getTabla() {
        return tabla;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    // Método para limpiar la tabla
    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    // ✅ Método recomendado para cargar filas con datos listos (cliente y plan con nombres)
    public void cargarMembresiasConNombres(List<Object[]> lista) {
        limpiarTabla();
        for (Object[] fila : lista) {
            modeloTabla.addRow(fila);
        }
    }

    // (Opcional) método anterior si se usara Membresia directamente
    /*
    public void cargarMembresias(List<Membresia> lista) {
        limpiarTabla();
        for (Membresia m : lista) {
            modeloTabla.addRow(new Object[]{
                m.getId(),
                m.getIdCliente(),  // solo muestra ID, no recomendable
                m.getIdPlan(),     // solo muestra ID, no recomendable
                m.getFechaInicio(),
                m.getFechaFin(),
                m.getEstadoPago()
            });
        }
    }
    */
}
