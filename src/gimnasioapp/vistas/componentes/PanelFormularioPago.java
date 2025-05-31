package gimnasioapp.vistas.componentes;

import gimnasioapp.controladores.ControladorPago;
import gimnasioapp.gimnasioDAL.PlanDAL;
import gimnasioapp.modelos.Cliente;
import gimnasioapp.modelos.Pago;
import gimnasioapp.modelos.Membresia;
import gimnasioapp.modelos.Plan;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelFormularioPago extends JPanel {
    private JComboBox<String> comboMembresias;
    private JTextField txtFechaPago;
    private JTextField txtMonto;
    private JTextField txtMetodoPago;
    private JTextField txtObservaciones;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    private ControladorPago controlador;

    public PanelFormularioPago(ControladorPago controlador) {
        this.controlador = controlador;

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(350, 400));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "Registrar Pago",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(60, 60, 60)
        ));
        setBackground(Color.decode("#f9f9f9"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Membresía
        gbc.gridx = 0; gbc.gridy = 0;
        add(crearLabel("Membresía:"), gbc);
        gbc.gridx = 1;
        
        comboMembresias = new JComboBox<>();
        comboMembresias.setPreferredSize(new Dimension(180, 30));
        add(comboMembresias, gbc);

        // Fecha de pago
        gbc.gridx = 0; gbc.gridy = 1;
        add(crearLabel("Fecha de Pago:"), gbc);
        gbc.gridx = 1;
        txtFechaPago = new JTextField();
        txtFechaPago.setPreferredSize(new Dimension(180, 30));
        add(txtFechaPago, gbc);

        // Monto
        gbc.gridx = 0; gbc.gridy = 2;
        add(crearLabel("Monto ($):"), gbc);
        gbc.gridx = 1;
        txtMonto = new JTextField();
        txtMonto.setPreferredSize(new Dimension(180, 30));
        add(txtMonto, gbc);
        
        // Método de pago
        gbc.gridx = 0; gbc.gridy = 3;
        add(crearLabel("Método de pago: "), gbc);
        gbc.gridx = 1;
        txtMetodoPago = new JTextField();
        txtMetodoPago.setPreferredSize(new Dimension(180, 30));
        add(txtMetodoPago, gbc);
        
        // Observaciones
        gbc.gridx = 0; gbc.gridy = 4;
        add(crearLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        txtObservaciones = new JTextField();
        txtObservaciones.setPreferredSize(new Dimension(180, 30));
        add(txtObservaciones, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = 5;
        btnGuardar = crearBoton("Guardar");
        add(btnGuardar, gbc);

        gbc.gridx = 1;
        btnLimpiar = crearBoton("Limpiar");
        add(btnLimpiar, gbc);

        // Eventos
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPago();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    private void guardarPago() {
        try {
            String seleccion = (String) comboMembresias.getSelectedItem();
            String fecha = txtFechaPago.getText().trim();
            String montoTexto = txtMonto.getText().trim();
            String metodoPago = txtMetodoPago.getText().trim();
            String observaciones = txtObservaciones.getText().trim();

            if (seleccion == null || fecha.isEmpty() || montoTexto.isEmpty()) {
                mostrarMensaje("Todos los campos son obligatorios.");
                return;
            }

            double monto = Double.parseDouble(montoTexto);

            String[] partes = seleccion.split(" - ");
            if (partes.length < 1) {
                mostrarMensaje("Formato de membresía inválido.");
                return;
            }

            int membresiaId = Integer.parseInt(partes[0].trim());

            Membresia membresia = new Membresia();
            membresia.setId(membresiaId);

            Pago pago = new Pago();
            pago.setIdMembresia(membresiaId);
            pago.setMembresia(membresia);
            pago.setFechaPago(fecha);
            pago.setMonto(monto);
            pago.setMetodoPago(metodoPago);
            pago.setObservaciones(observaciones);

            controlador.guardarPago(pago);

            limpiarCampos();

        } catch (NumberFormatException ex) {
            mostrarMensaje("El monto debe ser un número válido.");
        } catch (Exception e) {
            mostrarMensaje("Error al guardar el pago: " + e.getMessage());
        }
    }


    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(40, 40, 40));
        return label;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(0, 123, 255));
        boton.setForeground(Color.WHITE);
        boton.setPreferredSize(new Dimension(100, 35));
        return boton;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        comboMembresias.setSelectedIndex(0);
        txtFechaPago.setText("");
        txtMonto.setText("");
        txtMetodoPago.setText("");
        txtObservaciones.setText("");
    }

    public void setDatosPago(Pago pago) {
        comboMembresias.setSelectedItem(pago.getMembresia().getTipo());
        txtFechaPago.setText(pago.getFechaPago());
        txtMonto.setText(String.valueOf(pago.getMonto()));
    }

    public JTextField getTxtFechaPago() {
        return txtFechaPago;
    }

    public JTextField getTxtMonto() {
        return txtMonto;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
    
    public void cargarMembresias(List<String> membresias) {
        comboMembresias.removeAllItems();
        for (String membresia : membresias) {
            comboMembresias.addItem(membresia);
        }
    }
    
    public JComboBox<String> getComboMembresias() {
        return comboMembresias;
    }

}
