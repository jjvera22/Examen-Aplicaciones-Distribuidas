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
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Conexión y controlador
        Connection conn = ConexionSQLite.conectar();
        controlador = new ControladorMembresia(conn);

        // HEADER
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setPreferredSize(new Dimension(100, 60));

        JLabel lblLogo = new JLabel(new ImageIcon("src/resources/imagenes/logo_web.avif"));
        JLabel lblTitulo = new JLabel("Gestión de Membresías");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(33, 37, 41));

        headerPanel.add(lblLogo);
        headerPanel.add(lblTitulo);

        add(headerPanel, BorderLayout.NORTH);

        // FORMULARIO Y TABLA
        PanelFormulario panelFormulario = new PanelFormulario(controlador);
        PanelTabla panelTabla = new PanelTabla(controlador);

        controlador.setPanelFormulario(panelFormulario);
        controlador.setPanelTabla(panelTabla);
        controlador.cargarClientes();
        controlador.cargarPlanes();

        panelFormulario.setBackground(Color.WHITE);
        //panelFormulario.setPreferredSize(new Dimension(400, 500)); // anchura mínima
        //panelFormulario.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));

        JScrollPane tablaScroll = new JScrollPane(panelTabla);
        tablaScroll.setBackground(Color.WHITE);
        tablaScroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // PANEL CENTRAL que reparte el espacio horizontalmente
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelFormulario, tablaScroll);
		splitPane.setResizeWeight(0.5); // 50% - 50%
		splitPane.setDividerSize(8); // más delgado el divisor
		splitPane.setContinuousLayout(true);
		splitPane.setBorder(null);

		add(splitPane, BorderLayout.CENTER);

    }
}

