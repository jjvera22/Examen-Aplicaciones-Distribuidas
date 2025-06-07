package gimnasioapp.vistas.componentes;

import gimnasioapp.controladores.ControladorMembresia;
import gimnasioapp.modelos.Cliente;
import gimnasioapp.modelos.Membresia;
import gimnasioapp.modelos.Plan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelFormulario extends JPanel {

    private JComboBox<Cliente> comboClientes;
    private JComboBox<Plan> comboPlanes;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JComboBox<String> comboEstadoPago;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    public PanelFormulario(ControladorMembresia controlador) {
        setLayout(new GridBagLayout());
        setBackground(Color.decode("#eafaf1"));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Formulario de Membresía"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuente = new Font("Segoe UI", Font.PLAIN, 16);

        // Fila 1 - Cliente
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(fuente);
        add(lblCliente, gbc);

        gbc.gridx = 1;
        comboClientes = new JComboBox<>();
        comboClientes.setFont(fuente);
        add(comboClientes, gbc);

        // Fila 2 - Plan
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblPlan = new JLabel("Plan:");
        lblPlan.setFont(fuente);
        add(lblPlan, gbc);

        gbc.gridx = 1;
        comboPlanes = new JComboBox<>();
        comboPlanes.setFont(fuente);
        add(comboPlanes, gbc);

        // Fila 3 - Fecha Inicio
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblInicio = new JLabel("Fecha Inicio (YYYY-MM-DD):");
        lblInicio.setFont(fuente);
        add(lblInicio, gbc);

        gbc.gridx = 1;
        txtFechaInicio = new JTextField(10);
        txtFechaInicio.setFont(fuente);
        add(txtFechaInicio, gbc);

        // Fila 4 - Fecha Fin
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblFin = new JLabel("Fecha Fin (YYYY-MM-DD):");
        lblFin.setFont(fuente);
        add(lblFin, gbc);

        gbc.gridx = 1;
        txtFechaFin = new JTextField(10);
        txtFechaFin.setFont(fuente);
        add(txtFechaFin, gbc);

        // Fila 5 - Estado de Pago
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblEstado = new JLabel("Estado de Pago:");
        lblEstado.setFont(fuente);
        add(lblEstado, gbc);

        gbc.gridx = 1;
        comboEstadoPago = new JComboBox<>(new String[]{"Pagado", "Pendiente"});
        comboEstadoPago.setFont(fuente);
        add(comboEstadoPago, gbc);

        // Fila 6 - Botones
        gbc.gridy++;
        gbc.gridx = 0;
        btnGuardar = new JButton("Guardar");
        btnGuardar.setIcon(new ImageIcon("C:\\Users\\nayde\\Pictures\\JAM\\ITB\\aplicaciones\\EXAMEN\\GimnasioApp\\src\\resources\\iconos\\guardar.png"));
        btnGuardar.setFont(fuente);
        add(btnGuardar, gbc);

        gbc.gridx = 1;
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setIcon(new ImageIcon("C:\\Users\\nayde\\Pictures\\JAM\\ITB\\aplicaciones\\EXAMEN\\GimnasioApp\\src\\resources\\iconos\\limpiar.png"));
        btnLimpiar.setFont(fuente);
        add(btnLimpiar, gbc);
    }

    // Cargar clientes
    public void cargarClientes(List<Cliente> clientes) {
        comboClientes.removeAllItems();
        for (Cliente c : clientes) {
            comboClientes.addItem(c);
        }
    }

    // Cargar planes
    public void cargarPlanes(List<Plan> planes) {
        comboPlanes.removeAllItems();
        for (Plan p : planes) {
            comboPlanes.addItem(p);
        }
    }

    // Getters
    public JComboBox<Cliente> getComboClientes() {
        return comboClientes;
    }

    public JComboBox<Plan> getComboPlanes() {
        return comboPlanes;
    }

    public JTextField getTxtFechaInicio() {
        return txtFechaInicio;
    }

    public JTextField getTxtFechaFin() {
        return txtFechaFin;
    }

    public JComboBox<String> getComboEstadoPago() {
        return comboEstadoPago;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void limpiarFormulario() {
        comboClientes.setSelectedIndex(-1);
        comboPlanes.setSelectedIndex(-1);
        txtFechaInicio.setText("");
        txtFechaFin.setText("");
        comboEstadoPago.setSelectedIndex(0);
    }
	
    public void cargarMembresiaFormulario(Membresia membresia) {
        seleccionarClientePorId(membresia.getIdCliente(), comboClientes);
        seleccionarPlanPorId(membresia.getIdPlan(), comboPlanes);
        txtFechaInicio.setText(membresia.getFechaInicio());
        txtFechaFin.setText(String.valueOf(membresia.getFechaFin()));
        comboEstadoPago.setSelectedItem(membresia.getEstadoPago());
    }

    public void seleccionarClientePorId(int id, JComboBox<Cliente> combo) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            Cliente c = combo.getItemAt(i);
            if (c.getId() == id) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    public void seleccionarPlanPorId(int id, JComboBox<Plan> combo) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            Plan p = combo.getItemAt(i);
            if (p.getId() == id) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }
}

