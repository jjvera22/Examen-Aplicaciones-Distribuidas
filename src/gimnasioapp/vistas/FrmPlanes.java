package gimnasioapp.vistas;

import gimnasioapp.controladores.ControladorPlan;
import gimnasioapp.gimnasioDAL.ConexionSQLite;
import gimnasioapp.vistas.componentes.PanelFormularioPlan;
import gimnasioapp.vistas.componentes.PanelTablaPlan;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class FrmPlanes extends JPanel {

    private ControladorPlan controlador;

    public FrmPlanes() {
        //setTitle("Gestión de Planes");
        //setSize(1100, 650);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);

        // Estilo
        Color fondo = Color.decode("#eafaf1");
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 20);

        // Panel superior con logo y título
        JLabel lblLogo = new JLabel(new ImageIcon("C:\\Users\\nayde\\Pictures\\JAM\\ITB\\aplicaciones\\EXAMEN\\GimnasioApp\\src\\resources\\imagenes\\logo.png"));
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel lblTitulo = new JLabel("Gestión de Planes");
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(40, 40, 40));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(fondo);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);

        // Conexión y controlador
        Connection conn = ConexionSQLite.conectar();
        controlador = new ControladorPlan(conn);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(fondo);

        // Componentes personalizados
        PanelFormularioPlan panelFormulario = new PanelFormularioPlan(controlador);
        PanelTablaPlan panelTabla = new PanelTablaPlan(controlador);

        controlador.setPanelFormulario(panelFormulario);
        controlador.setPanelTabla(panelTabla);
        controlador.cargarPlanes();

        // Agregar a la interfaz
        panelPrincipal.add(panelFormulario, BorderLayout.WEST);
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelPrincipal, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmPlanes().setVisible(true));
    }
}
