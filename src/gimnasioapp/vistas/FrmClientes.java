package gimnasioapp.vistas;

import gimnasioapp.controladores.ControladorCliente;
import gimnasioapp.gimnasioDAL.ConexionSQLite;
import gimnasioapp.vistas.componentes.PanelFormularioCliente;
import gimnasioapp.vistas.componentes.PanelTablaCliente;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;

public class FrmClientes extends JPanel {

    private ControladorCliente controlador;

    public FrmClientes() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Conexión y controlador
        Connection conn = ConexionSQLite.conectar();
        controlador = new ControladorCliente(conn);

        // HEADER igual que en FrmMembresias
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

        JLabel lblTitulo = new JLabel("Gestión de Clientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(33, 37, 41));

        headerPanel.add(lblLogo);
        headerPanel.add(lblTitulo);

        add(headerPanel, BorderLayout.NORTH);

        // Panel formulario cliente
        PanelFormularioCliente panelFormulario = new PanelFormularioCliente(controlador);
        panelFormulario.setBackground(Color.WHITE);  // Igualar fondo

        // Panel tabla cliente
        PanelTablaCliente panelTabla = new PanelTablaCliente(controlador);
        controlador.setPanelFormulario(panelFormulario);
        controlador.setPanelTabla(panelTabla);

        JScrollPane scrollTabla = new JScrollPane(panelTabla);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Dividir el espacio horizontalmente con JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelFormulario, scrollTabla);
        splitPane.setResizeWeight(0.5); // Divide 50%-50%
        splitPane.setDividerSize(8);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);
    }
}
