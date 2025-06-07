package gimnasioapp.vistas.componentes;

import gimnasioapp.controladores.ControladorMembresia;
import gimnasioapp.modelos.Membresia;

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
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            "Listado de Membresías"
        ));
        setBackground(Color.WHITE);

        // Modelo de tabla
        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Cliente", "Plan", "Inicio", "Fin", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(24);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setBackground(Color.WHITE);

        btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnEditar.setIcon(new ImageIcon("src/resources/iconos/editar.png"));
        
        tabla.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                Membresia m = controlador.obtenerMembresiaDesdeFila(tabla);
                if (m != null) {
                    controlador.cargarMembresiaEnFormulario(m);
                }
            }
        });

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnEliminar.setIcon(new ImageIcon("src/resources/iconos/eliminar.png"));

        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    // Getters
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

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    public void cargarMembresiasConNombres(List<Object[]> lista) {
        limpiarTabla();
        for (Object[] fila : lista) {
            modeloTabla.addRow(fila);
        }
    }
}
