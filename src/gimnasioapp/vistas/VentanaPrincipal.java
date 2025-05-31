package gimnasioapp.vistas;

import gimnasioapp.vistas.componentes.PanelCarruselInicio;
import gimnasioapp.gimnasioDAL.ClienteDAL;
import gimnasioapp.modelos.Cliente;  // la clase Cliente que usas en DAL
import gimnasioapp.gimnasioDAL.ConexionSQLite; // tu clase para obtener conexión
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaPrincipal extends JFrame {

    private JPanel panelContenido;
    private JPanel panelMenu;
    private JButton botonActivo = null;

    public VentanaPrincipal() {
        setTitle("Sistema de Gestión del Gimnasio");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelContenido = new JPanel(new BorderLayout());
        add(panelContenido, BorderLayout.CENTER);

        // Mostrar carrusel de bienvenida
        mostrarCarruselInicio();
    }

    private void mostrarCarruselInicio() {
        PanelCarruselInicio panelCarrusel = new PanelCarruselInicio(e -> {
            getContentPane().removeAll();

            add(crearPanelMenu(), BorderLayout.NORTH); // agregar menú
            panelContenido = new JPanel(new GridBagLayout());
            add(panelContenido, BorderLayout.CENTER);

            mostrarVistaConFade(new FrmClientes());
            marcarBotonActivo((JButton) panelMenu.getComponent(0));

            revalidate();
            repaint();
        });

        panelContenido.removeAll();
        panelContenido.setLayout(new BorderLayout());
        panelContenido.add(panelCarrusel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JPanel crearPanelMenu() {
        panelMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(33, 37, 41), 0, getHeight(), new Color(22, 27, 32)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelMenu.setPreferredSize(new Dimension(0, 60));
        panelMenu.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

        JButton btnClientes = crearBotonMenu("Clientes");
        JButton btnMembresias = crearBotonMenu("Membresías");
        JButton btnPagos = crearBotonMenu("Pagos");
        JButton btnPlanes = crearBotonMenu("Planes");
        JButton btnReporte = crearBotonMenu("Reporte");  // NUEVO botón

        panelMenu.add(btnClientes);
        panelMenu.add(btnMembresias);
        panelMenu.add(btnPagos);
        panelMenu.add(btnPlanes);
        panelMenu.add(btnReporte);  // agregar botón al panel

        btnClientes.addActionListener(e -> {
            mostrarVistaConFade(new FrmClientes());
            marcarBotonActivo(btnClientes);
        });
        btnMembresias.addActionListener(e -> {
            mostrarVistaConFade(new FrmMembresias());
            marcarBotonActivo(btnMembresias);
        });
        btnPagos.addActionListener(e -> {
            mostrarVistaConFade(new FrmPagos());
            marcarBotonActivo(btnPagos);
        });
        btnPlanes.addActionListener(e -> {
            mostrarVistaConFade(new FrmPlanes());
            marcarBotonActivo(btnPlanes);
        });

        btnReporte.addActionListener(e -> {
            try (Connection conn = ConexionSQLite.conectar()) {
                ClienteDAL clienteDAL = new ClienteDAL(conn);

                List<String> inscritos = clienteDAL.obtenerClientesInscritos()
                        .stream()
                        .map(Cliente::getNombre)
                        .collect(Collectors.toList());

                List<String> retirados = clienteDAL.obtenerClientesRetirados()
                        .stream()
                        .map(Cliente::getNombre)
                        .collect(Collectors.toList());

                List<String> faltaPago = clienteDAL.obtenerClientesConFaltaPago()
                        .stream()
                        .map(Cliente::getNombre)
                        .collect(Collectors.toList());

                VentanaReporte reporte = new VentanaReporte(this, inscritos, retirados, faltaPago);
                reporte.setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error al obtener datos de reporte: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panelMenu;
    }

    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(new EmptyBorder(0, 10, 0, 10));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (boton != botonActivo)
                    boton.setForeground(new Color(100, 200, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (boton != botonActivo)
                    boton.setForeground(Color.WHITE);
            }
        });

        return boton;
    }

    private void mostrarVistaConFade(JPanel vista) {
        panelContenido.removeAll();
        vista.setOpaque(false);
        panelContenido.add(vista, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void marcarBotonActivo(JButton btn) {
        if (botonActivo != null) {
            botonActivo.setForeground(Color.WHITE);
        }
        botonActivo = btn;
        botonActivo.setForeground(new Color(40, 167, 69)); // verde activo
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
