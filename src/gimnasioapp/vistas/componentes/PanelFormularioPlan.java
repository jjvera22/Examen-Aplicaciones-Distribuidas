package gimnasioapp.vistas.componentes;

import gimnasioapp.controladores.ControladorPlan;
import gimnasioapp.modelos.Plan;

import javax.swing.*;
import java.awt.*;

public class PanelFormularioPlan extends JPanel {

    private final JTextField txtNombre = new JTextField(10);
    private final JTextField txtPrecio = new JTextField(10);
    private final JTextField txtDuracion = new JTextField(10);
    private final JButton btnGuardar = new JButton("Guardar");

    private int idPlanActual = 0;

    public PanelFormularioPlan(ControladorPlan controlador) {
        controlador.setPanelFormulario(this);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Formulario Plan"));
        setBackground(Color.decode("#eafaf1"));

        Font fuente = new Font("Segoe UI", Font.PLAIN, 16);

        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCampos.setBackground(getBackground());

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(fuente);
        txtNombre.setFont(fuente);
        panelCampos.add(lblNombre);
        panelCampos.add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(fuente);
        txtPrecio.setFont(fuente);
        panelCampos.add(lblPrecio);
        panelCampos.add(txtPrecio);

        JLabel lblDuracion = new JLabel("Duración (días):");
        lblDuracion.setFont(fuente);
        txtDuracion.setFont(fuente);
        panelCampos.add(lblDuracion);
        panelCampos.add(txtDuracion);

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(getBackground());

        btnGuardar.setFont(fuente);
        btnGuardar.setIcon(new ImageIcon("C:\\Users\\nayde\\Pictures\\JAM\\ITB\\aplicaciones\\EXAMEN\\GimnasioApp\\src\\resources\\iconos\\guardar.png"));
        btnGuardar.addActionListener(e -> controlador.guardarPlan());

        panelBoton.add(btnGuardar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
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
