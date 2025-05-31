package gimnasioapp.vistas.componentes;

import gimnasioapp.controladores.ControladorCliente;
import gimnasioapp.modelos.Cliente;

import javax.swing.*;
import java.awt.*;

public class PanelFormularioCliente extends JPanel {

    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtCedula = new JTextField(15);
    private final JTextField txtTelefono = new JTextField(15);
    private final JTextField txtCorreo = new JTextField(15);
    private final JTextField txtDireccion = new JTextField(15);
    private final JButton btnGuardar = new JButton("Guardar");

    private int idClienteActual = 0;
    private final ControladorCliente controlador;

    public PanelFormularioCliente(ControladorCliente controlador) {
        this.controlador = controlador;
        controlador.setPanelFormulario(this);

        setLayout(new GridBagLayout());
        setBackground(Color.decode("#eafaf1"));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Formulario Cliente"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuente = new Font("Segoe UI", Font.PLAIN, 16);

        // Fila 1 - Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(fuente);
        add(lblNombre, gbc);

        gbc.gridx = 1;
        txtNombre.setFont(fuente);
        add(txtNombre, gbc);

        // Fila 2 - Cédula
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setFont(fuente);
        add(lblCedula, gbc);

        gbc.gridx = 1;
        txtCedula.setFont(fuente);
        add(txtCedula, gbc);

        // Fila 3 - Teléfono
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(fuente);
        add(lblTelefono, gbc);

        gbc.gridx = 1;
        txtTelefono.setFont(fuente);
        add(txtTelefono, gbc);

        // Fila 4 - Correo
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setFont(fuente);
        add(lblCorreo, gbc);

        gbc.gridx = 1;
        txtCorreo.setFont(fuente);
        add(txtCorreo, gbc);

        // Fila 5 - Dirección
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setFont(fuente);
        add(lblDireccion, gbc);

        gbc.gridx = 1;
        txtDireccion.setFont(fuente);
        add(txtDireccion, gbc);

        // Fila 6 - Botón Guardar
        gbc.gridx = 0; gbc.gridy++;
        btnGuardar.setIcon(new ImageIcon("C:\\Users\\nayde\\Pictures\\JAM\\ITB\\aplicaciones\\EXAMEN\\GimnasioApp\\src\\resources\\iconos\\guardar.png"));
        btnGuardar.setFont(fuente);
        add(btnGuardar, gbc);

        // Celda vacía para alineación
        gbc.gridx = 1;
        add(Box.createGlue(), gbc);

        // Evento de Guardar con verificación
        btnGuardar.addActionListener(e -> {
            String cedula = txtCedula.getText().trim();

            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa una cédula.", "Campo obligatorio", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Cliente clienteExistente = controlador.buscarPorCedula(cedula);

            if (clienteExistente != null) {
                int opcion = JOptionPane.showOptionDialog(
                    this,
                    "Este cliente ya está registrado.\n¿Deseas agregar otra membresía o cambiar de plan?",
                    "Cliente existente",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Agregar Membresía", "Cambiar Plan"},
                    "Agregar Membresía"
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(this, "Redirigiendo a Membresías...");
					
                } else if (opcion == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "Redirigiendo a Cambiar Plan...");
                    
                }

            } else {
                controlador.guardarCliente();
                JOptionPane.showMessageDialog(this, "Cliente registrado correctamente. Procede con la membresía.");
            }
        });
    }

    public Cliente obtenerClienteFormulario() {
        Cliente c = new Cliente();
        c.setId(idClienteActual);
        c.setNombre(txtNombre.getText().trim());
        c.setCedula(txtCedula.getText().trim());
        c.setTelefono(txtTelefono.getText().trim());
        c.setCorreo(txtCorreo.getText().trim());
        c.setDireccion(txtDireccion.getText().trim());
        return c;
    }

    public void cargarClienteFormulario(Cliente c) {
        idClienteActual = c.getId();
        txtNombre.setText(c.getNombre());
        txtCedula.setText(c.getCedula());
        txtTelefono.setText(c.getTelefono());
        txtCorreo.setText(c.getCorreo());
        txtDireccion.setText(c.getDireccion());
    }

    public void limpiarFormulario() {
        idClienteActual = 0;
        txtNombre.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
    }
}
