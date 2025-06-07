package gimnasioapp.vistas.componentes;

import gimnasioapp.controladores.ControladorCliente;
import gimnasioapp.modelos.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelTablaCliente extends JPanel {

    private final JTable tabla;
    private final JButton btnEditar = new JButton("✏️ Editar");
    private final JButton btnEliminar = new JButton("🗑️ Eliminar");
    private final ControladorCliente controlador;

    public PanelTablaCliente(ControladorCliente controlador) {
        this.controlador = controlador;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));

        // Configurar tabla
        tabla = new JTable(new DefaultTableModel(new Object[]{"ID", "Nombre", "Cédula", "Teléfono", "Correo", "Dirección"}, 0));
        JScrollPane scrollPane = new JScrollPane(tabla);

        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos
        controlador.cargarClientesEnTabla(tabla);

        // Eventos
        btnEditar.addActionListener(e -> {
            Cliente c = controlador.obtenerClienteDesdeFila(tabla);
            if (c != null) {
                controlador.actualizarCliente(c);
            }
        });
        
        tabla.getSelectionModel().addListSelectionListener(event -> {
            // Solo actuar cuando la selección haya finalizado
            if (!event.getValueIsAdjusting()) {
                Cliente c = controlador.obtenerClienteDesdeFila(tabla);
                if (c != null) {
                    controlador.cargarClienteEnFormulario(c);
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            Cliente c = controlador.obtenerClienteDesdeFila(tabla);
            if (c != null) {
                controlador.eliminarCliente(c.getId());
                controlador.cargarClientesEnTabla(tabla);
            }
        });
    }

    public JTable getTabla() {
        return tabla;
    }
}
