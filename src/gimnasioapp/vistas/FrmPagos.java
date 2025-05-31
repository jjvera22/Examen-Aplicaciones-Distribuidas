package gimnasioapp.vistas;

import gimnasioapp.controladores.ControladorPago;
import gimnasioapp.gimnasioDAL.ConexionSQLite;
import gimnasioapp.gimnasioDAL.PagoDAL;
import gimnasioapp.vistas.componentes.PanelFormularioPago;
import gimnasioapp.vistas.componentes.PanelTablaPago;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;

public class FrmPagos extends JPanel {

    private ControladorPago controlador;

    public FrmPagos() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Conexión a base de datos
        Connection conn = ConexionSQLite.conectar();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PagoDAL pagoDAL = new PagoDAL(conn);
        controlador = new ControladorPago(pagoDAL, conn);

        // HEADER iguales
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setPreferredSize(new Dimension(100, 60));

        JLabel lblLogo = new JLabel();
        URL urlLogo = getClass().getClassLoader().getResource("src/resources/imagenes/logo_web.avif");
        if (urlLogo != null) {
            lblLogo.setIcon(new ImageIcon(urlLogo));
        } else {
            System.err.println("No se encontró la imagen logo.webp");
            lblLogo.setText("Logo no encontrado");
            lblLogo.setForeground(Color.WHITE);
        }

        JLabel lblTitulo = new JLabel("Gestión de Pagos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(33, 37, 41));

        headerPanel.add(lblLogo);
        headerPanel.add(lblTitulo);

        add(headerPanel, BorderLayout.NORTH);

        // Panel formulario y tabla
        PanelFormularioPago panelFormulario = new PanelFormularioPago(controlador);
        panelFormulario.setBackground(Color.WHITE);

        PanelTablaPago panelTabla = new PanelTablaPago(controlador);

        controlador.setPanelFormulario(panelFormulario);
        controlador.setPanelTabla(panelTabla);

        JScrollPane scrollTabla = new JScrollPane(panelTabla);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Dividir con JSplitPane horizontal 50%-50%
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelFormulario, scrollTabla);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(8);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);

        // Inicializar datos en tabla
        controlador.actualizarTabla();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sistema Gimnasio - Gestión de Pagos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new FrmPagos());
            frame.setVisible(true);
        });
    }
}
