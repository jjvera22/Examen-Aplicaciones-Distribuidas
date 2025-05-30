package gimnasioapp.vistas;

import gimnasioapp.controladores.ControladorPago;
import gimnasioapp.gimnasioDAL.ConexionSQLite;
import gimnasioapp.gimnasioDAL.PagoDAL;
import gimnasioapp.vistas.componentes.PanelFormularioPago;
import gimnasioapp.vistas.componentes.PanelTablaPago;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class FrmPagos extends JPanel {

    private ControladorPago controlador;
    private Connection conn;

    public FrmPagos() {
        // Colores y fuente
        Color backgroundColor = Color.decode("#f0f9ff");
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 20);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(backgroundColor);

        // Logo
        JLabel lblLogo = new JLabel();
        ImageIcon icon = new ImageIcon("src/resources/imagenes/logo.webp");
        if (icon.getIconWidth() == -1) {
            lblLogo.setText("LOGO");
        } else {
            lblLogo.setIcon(icon);
        }
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);

        // Título
        JLabel lblTitulo = new JLabel("Gestión de Pagos");
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setForeground(new Color(30, 30, 30));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel superior con logo y título
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(backgroundColor);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);

        // Conexión a base de datos y controlador
        Connection conn = ConexionSQLite.conectar();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            //dispose();
            return;
        }

        PagoDAL pagoDAL = new PagoDAL(conn);
        controlador = new ControladorPago(pagoDAL, conn);

        // Paneles personalizados
        PanelFormularioPago panelFormulario = new PanelFormularioPago(controlador);
        PanelTablaPago panelTabla = new PanelTablaPago(controlador);

        controlador.setPanelFormulario(panelFormulario);
        controlador.setPanelTabla(panelTabla);

        // Ensamblado visual
        panelPrincipal.add(panelFormulario, BorderLayout.WEST);
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        // Agregar todo al JFrame
        add(panelSuperior, BorderLayout.NORTH);
        add(panelPrincipal, BorderLayout.CENTER);

        // Actualizar la tabla al iniciar
        controlador.actualizarTabla();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new FrmPagos().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
} 
