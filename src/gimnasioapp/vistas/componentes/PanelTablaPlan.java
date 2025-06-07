package gimnasioapp.vistas.componentes;

import gimnasioapp.controladores.ControladorPlan;
import gimnasioapp.modelos.Plan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelTablaPlan extends JPanel {

    private final JTable tabla;
    private final DefaultTableModel modelo;
    private final JButton btnEditar = new JButton("✏️ Editar");
    private final JButton btnEliminar = new JButton("🗑️ Eliminar");

    private final ControladorPlan controlador;

    public PanelTablaPlan(ControladorPlan controlador) {
        this.controlador = controlador;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Lista de Planes"));

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Duración (días)"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                Plan p = obtenerPlanDeFila(fila);
                controlador.actualizarPlan(p);
            }
        });
        
        tabla.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    Plan p = obtenerPlanDeFila(fila);
                    if (p != null) {
                        controlador.cargarPlanEnFormulario(p);
                    }
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                int id = (int) modelo.getValueAt(fila, 0);
                controlador.eliminarPlan(id);
            }
        });

        // ✅ Se coloca al final para evitar que el modelo sea null al actualizar la tabla
        controlador.setPanelTabla(this);
    }

    public void actualizarTabla(List<Plan> planes) {
        modelo.setRowCount(0);
        for (Plan p : planes) {
            modelo.addRow(new Object[]{p.getId(), p.getNombre(), p.getPrecio(), p.getDuracionDias()});
        }
    }

    private Plan obtenerPlanDeFila(int fila) {
        Plan p = new Plan();
        p.setId((int) modelo.getValueAt(fila, 0));
        p.setNombre((String) modelo.getValueAt(fila, 1));
        p.setPrecio((double) modelo.getValueAt(fila, 2));
        p.setDuracionDias((int) modelo.getValueAt(fila, 3));
        return p;
    }
}