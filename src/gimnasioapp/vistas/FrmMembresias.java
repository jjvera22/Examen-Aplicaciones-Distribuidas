package gimnasioapp.vistas;

import gimnasioapp.controladores.ControladorMembresia;
import gimnasioapp.gimnasioDAL.ConexionSQLite;
import gimnasioapp.vistas.componentes.PanelFormulario;
import gimnasioapp.vistas.componentes.PanelTabla;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class FrmMembresias extends JPanel {

    private ControladorMembresia controlador;

    public FrmMembresias() {
        //setTitle("Gestión de Membresías");
        //setSize(1100, 650);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);
        //getContentPane().setBackground(Color.decode("#eafaf1"));
        setLayout(new BorderLayout(10, 10));

        // Establecer conexión
        Connection conn = ConexionSQLite.conectar();

        // Inicializar controlador
        controlador = new ControladorMembresia(conn);

        // Panel de encabezado con logo y título
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(234, 250, 241));

        // Logo (verifica que el path esté correcto)
        JLabel lblLogo = new JLabel(new ImageIcon("C:\\Users\\nayde\\Pictures\\JAM\\ITB\\aplicaciones\\EXAMEN\\GimnasioApp\\src\\resources\\imagenes\\logo.png"));
        JLabel lblTitulo = new JLabel("Gestión de Membresías");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));

        headerPanel.add(lblLogo);
        headerPanel.add(lblTitulo);
        add(headerPanel, BorderLayout.NORTH);

        // Crear paneles personalizados
        PanelFormulario panelFormulario = new PanelFormulario(controlador);
        PanelTabla panelTabla = new PanelTabla(controlador);

        // Asociar los paneles al controlador
        controlador.setPanelFormulario(panelFormulario);
        controlador.setPanelTabla(panelTabla);

        // ✅ Cargar datos en los combos
        controlador.cargarClientes();
        controlador.cargarPlanes();

        // Estilo de los paneles
        panelFormulario.setBackground(Color.decode("#eafaf1"));
        panelTabla.setBackground(Color.WHITE);

        // Scroll para tabla
        JScrollPane scrollTabla = new JScrollPane(panelTabla);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Contenedor central con padding
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.decode("#eafaf1"));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(scrollTabla, BorderLayout.CENTER);

        // Agregar al contenedor principal
        add(panelFormulario, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmMembresias().setVisible(true));
    }
}
