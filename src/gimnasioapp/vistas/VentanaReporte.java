package gimnasioapp.vistas;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.JTableHeader;

public class VentanaReporte extends JDialog {

    public VentanaReporte(Frame parent, List<String> inscritos, List<String> retirados, List<String> faltaPago) {
        super(parent, "Reporte de Clientes", true);
        setSize(700, 450);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(33, 37, 41));
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabs.setForeground(Color.WHITE);
        tabs.setBackground(new Color(52, 58, 64));
        tabs.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                highlight = new Color(40, 167, 69); 
                lightHighlight = new Color(40, 167, 69);
                shadow = Color.DARK_GRAY;
                darkShadow = Color.BLACK;
            }
        });

        tabs.addTab("Clientes Inscritos", crearPanelTabla(inscritos, "Nombre"));
        tabs.addTab("Clientes Retirados", crearPanelTabla(retirados, "Nombre"));
        tabs.addTab("Pago Pendiente", crearPanelTabla(faltaPago, "Nombre"));

        add(tabs, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBackground(new Color(220, 53, 69));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(33, 37, 41));
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);
    }

    private JScrollPane crearPanelTabla(List<String> datos, String tituloColumna) {
        String[] columnas = {tituloColumna};
        String[][] filas;

        if (datos == null || datos.isEmpty()) {
            filas = new String[][]{{"No hay datos disponibles"}};
            columnas = new String[]{"Mensaje"};
        } else {
            filas = new String[datos.size()][1];
            for (int i = 0; i < datos.size(); i++) {
                filas[i][0] = datos.get(i);
            }
        }

        JTable tabla = new JTable(filas, columnas);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(28);
        tabla.setForeground(Color.WHITE);
        tabla.setBackground(new Color(52, 58, 64));
        tabla.setGridColor(new Color(70, 75, 80));

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(new Color(40, 167, 69)); 
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(new Color(33, 37, 41));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return scrollPane;
    }
}
