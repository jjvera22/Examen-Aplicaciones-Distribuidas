package gimnasioapp.vistas;

import gimnasioapp.controladores.ControladorCliente;
import gimnasioapp.gimnasioDAL.ConexionSQLite;
import gimnasioapp.vistas.componentes.PanelFormularioCliente;
import gimnasioapp.vistas.componentes.PanelTablaCliente;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class FrmClientes extends JPanel {

    private ControladorCliente controlador;

    public FrmClientes() {
        //setTitle("Gestión de Clientes");
        //setSize(1100, 650);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);

        // Colores y fuente
        Color backgroundColor = Color.decode("#eafaf1");
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 20);

        // Panel principal con fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(backgroundColor);

        // Logo y título superior
        JLabel lblLogo = new JLabel();
        lblLogo.setIcon(new ImageIcon("src/resources/imagenes/logo.webp"));
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel lblTitulo = new JLabel("Gestión de Clientes");
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setForeground(new Color(40, 40, 40));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(backgroundColor);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);

        // Conexión y controlador
        Connection conn = ConexionSQLite.conectar();
        controlador = new ControladorCliente(conn);

        // Paneles personalizados
        PanelFormularioCliente panelFormulario = new PanelFormularioCliente(controlador);
        PanelTablaCliente panelTabla = new PanelTablaCliente(controlador);

        controlador.setPanelFormulario(panelFormulario);
        controlador.setPanelTabla(panelTabla);

        // Ensamblar interfaz
        panelPrincipal.add(panelFormulario, BorderLayout.WEST);
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelPrincipal, BorderLayout.CENTER);
    }
}
