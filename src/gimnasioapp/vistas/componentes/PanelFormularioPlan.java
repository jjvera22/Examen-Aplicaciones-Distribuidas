package gimnasioapp.vistas.componentes;

import gimnasioapp.controladores.ControladorPlan;
import gimnasioapp.modelos.Plan;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PanelFormularioPlan extends JPanel {

    private final JTextField txtNombre = new JTextField();
    private final JTextField txtPrecio = new JTextField();
    private final JTextField txtDuracion = new JTextField();
    private final JButton btnGuardar = new JButton("Guardar");

    private int idPlanActual = 0;

    public PanelFormularioPlan(ControladorPlan controlador) {
        controlador.setPanelFormulario(this);

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(350, 300));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),
                "Registrar Plan",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(33, 37, 41)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        add(crearLabel("Nombre:"), gbc);
        gbc.gridy++;
        txtNombre.setPreferredSize(new Dimension(200, 30));
        add(txtNombre, gbc);

        // Precio
        gbc.gridy++;
        add(crearLabel("Precio ($):"), gbc);
        gbc.gridy++;
        txtPrecio.setPreferredSize(new Dimension(200, 30));
        add(txtPrecio, gbc);

        // Duración
        gbc.gridy++;
        add(crearLabel("Duración (días):"), gbc);
        gbc.gridy++;
        txtDuracion.setPreferredSize(new Dimension(200, 30));
        add(txtDuracion, gbc);

        // Botón
        gbc.gridy++;
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBackground(Color.WHITE);

        btnGuardar.setFocusPainted(false);
        btnGuardar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnGuardar.setBackground(new Color(0, 123, 255));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(110, 35));
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btnGuardar.setIcon(new ImageIcon("C:\\Users\\nayde\\Pictures\\JAM\\ITB\\aplicaciones\\EXAMEN\\GimnasioApp\\src\\resources\\iconos\\guardar.png"));

        btnGuardar.addActionListener(e -> controlador.guardarPlan());

        panelBoton.add(btnGuardar);
        add(panelBoton, gbc);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(40, 40, 40));
        return label;
    }

    public Plan obtenerPlanFormulario() {
        try {
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int duracion = Integer.parseInt(txtDuracion.getText().trim());

            Plan p = new Plan();
            p.setId(idPlanActual);
            p.setNombre(nombre);
            p.setPrecio(precio);
            p.setDuracionDias(duracion);
            return p;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio o duración inválidos. Verifica que sean números válidos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void cargarPlanFormulario(Plan p) {
        idPlanActual = p.getId();
        txtNombre.setText(p.getNombre());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        txtDuracion.setText(String.valueOf(p.getDuracionDias()));
    }

    public void limpiarFormulario() {
        idPlanActual = 0;
        txtNombre.setText("");
        txtPrecio.setText("");
        txtDuracion.setText("");
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }
}
