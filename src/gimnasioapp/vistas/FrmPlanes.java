package gimnasioapp.vistas;

import gimnasioapp.controladores.ControladorPlan;
import gimnasioapp.gimnasioDAL.ConexionSQLite;
import gimnasioapp.vistas.componentes.PanelFormularioPlan;
import gimnasioapp.vistas.componentes.PanelTablaPlan;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;

public class FrmPlanes extends JPanel {

    private ControladorPlan controlador;

    public FrmPlanes() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Conexión
        Connection conn = ConexionSQLite.conectar();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        controlador = new ControladorPlan(conn);

        // HEADER
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(new Color(245, 245, 245));  // gris claro
        headerPanel.setPreferredSize(new Dimension(100, 60));

        JLabel lblLogo = new JLabel();
        URL urlLogo = getClass().getClassLoader().getResource("imagenes/logo_web.avif");
        if (urlLogo != null) {
            lblLogo.setIcon(new ImageIcon(urlLogo));
        } else {
            System.err.println("No se encontró la imagen logo_web.avif");
            lblLogo.setText("Logo no encontrado");
            lblLogo.setForeground(Color.WHITE);
        }

        JLabel lblTitulo = new JLabel("Gestión de Planes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(33, 37, 41)); 

        headerPanel.add(lblLogo);
        headerPanel.add(lblTitulo);

        add(headerPanel, BorderLayout.NORTH);

        PanelFormularioPlan panelFormulario = new PanelFormularioPlan(controlador);
        panelFormulario.setBackground(Color.WHITE);

        PanelTablaPlan panelTabla = new PanelTablaPlan(controlador);

        controlador.setPanelFormulario(panelFormulario);
        controlador.setPanelTabla(panelTabla);
        controlador.cargarPlanes();

        JScrollPane scrollTabla = new JScrollPane(panelTabla);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelFormulario, scrollTabla);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(8);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sistema Gimnasio - Gestión de Planes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new FrmPlanes());
            frame.setVisible(true);
        });
    }
}
