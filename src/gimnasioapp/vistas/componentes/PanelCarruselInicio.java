package gimnasioapp.vistas.componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelCarruselInicio extends JPanel {
    private List<ImageIcon> imagenes = new ArrayList<>();
    private JLabel lblImagen;
    private JLabel lblBienvenida;
    private int indiceActual = 0;
    private JButton btnAnterior;
    private JButton btnSiguiente;
    private Timer timer;

    public PanelCarruselInicio(ActionListener listenerBotonInicio) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#1a1a1a"));

        // Cargar imágenes
        imagenes.add(new ImageIcon("src/resources/imagenes/carrusel1.jpg"));
        imagenes.add(new ImageIcon("src/resources/imagenes/carrusel2.jpg"));
        imagenes.add(new ImageIcon("src/resources/imagenes/carrusel3.jpg"));
        imagenes.add(new ImageIcon("src/resources/imagenes/carrusel4.jpg"));

        // Panel superior con botón a la derecha
        add(crearBarraSuperior(listenerBotonInicio), BorderLayout.NORTH);

        // Carrusel central
        JLayeredPane layeredPane = new JLayeredPane();
        add(layeredPane, BorderLayout.CENTER);

        lblImagen = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!imagenes.isEmpty()) {
                    ImageIcon icon = imagenes.get(indiceActual);
                    Image img = icon.getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        lblImagen.setBounds(0, 0, 1360, 400);
        layeredPane.add(lblImagen, Integer.valueOf(0));

        lblBienvenida = new JLabel("");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 56));
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setHorizontalAlignment(SwingConstants.LEFT);
        lblBienvenida.setVerticalAlignment(SwingConstants.CENTER);
        lblBienvenida.setBounds(50, 50, 800, 50);
        layeredPane.add(lblBienvenida, Integer.valueOf(1));

        layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                Dimension size = layeredPane.getSize();
                lblImagen.setBounds(0, 0, size.width, size.height);
                lblBienvenida.setBounds(50, size.height / 2 - 25, size.width - 100, 50);
            }
        });

        // Animación de texto
        String textoCompleto = "BIENVENIDOS AL GYM POWER";
        Timer animacionEscritura = new Timer(100, null);
        final int[] indiceLetra = {0};
        animacionEscritura.addActionListener(e -> {
            if (indiceLetra[0] < textoCompleto.length()) {
                lblBienvenida.setText(textoCompleto.substring(0, indiceLetra[0] + 1));
                indiceLetra[0]++;
            } else {
                animacionEscritura.stop();
            }
        });
        animacionEscritura.start();

        // Navegación del carrusel
        add(crearBotonesCarrusel(), BorderLayout.EAST);

        // Relatos motivacionales debajo del carrusel
        add(crearPanelRelatos(), BorderLayout.SOUTH);

        timer = new Timer(3000, e -> {
            indiceActual = (indiceActual + 1) % imagenes.size();
            lblImagen.repaint();
        });
        timer.start();
    }

    private JPanel crearBarraSuperior(ActionListener listenerBotonInicio) {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        barra.setBackground(Color.decode("#1a1a1a"));

        JButton btnIniciar = new JButton("Inscripcion");
        btnIniciar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnIniciar.setBackground(new Color(255, 0, 0));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIniciar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        if (listenerBotonInicio != null) {
            btnIniciar.addActionListener(listenerBotonInicio);
        }

        barra.add(btnIniciar);
        return barra;
    }

    private JPanel crearPanelRelatos() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1, 3, 20, 10));
    panel.setBackground(Color.decode("#1a1a1a"));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    // Datos de relatos: texto + ruta imagen
    String[] relatos = {
        "<html><center><b>“No se trata de ser el mejor,<br>se trata de ser mejor que ayer.”</b></center></html>",
        "<html><center><b>“La motivación te inicia,<br>el hábito te mantiene.”</b></center></html>",
        "<html><center><b>“El dolor es pasajero,<br>el orgullo es para siempre.”</b></center></html>"
    };

    String[] rutasImagenes = {
        "src/resources/imagenes/relato1.jpg",
        "src/resources/imagenes/relato2.jpg",
        "src/resources/imagenes/relato3.jpg"
    };

    for (int i = 0; i < relatos.length; i++) {
        JPanel cuadro = new JPanel();
        cuadro.setBackground(Color.decode("#2a2a2a"));
        cuadro.setLayout(new BorderLayout());
        cuadro.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.RED, 2, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Imagen
        ImageIcon icon = new ImageIcon(rutasImagenes[i]);
        // Escalar imagen a un tamaño fijo, ej. 150x100
        Image img = icon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        JLabel lblImagen = new JLabel(new ImageIcon(img));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        cuadro.add(lblImagen, BorderLayout.NORTH);

        // Texto
        JLabel lblTexto = new JLabel(relatos[i]);
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTexto.setForeground(Color.WHITE);
        lblTexto.setHorizontalAlignment(SwingConstants.CENTER);
        lblTexto.setVerticalAlignment(SwingConstants.TOP);
        cuadro.add(lblTexto, BorderLayout.CENTER);

        panel.add(cuadro);
    }

    return panel;
}


    private JPanel crearBotonesCarrusel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);

        btnAnterior = crearBotonFlecha("◀");
        btnSiguiente = crearBotonFlecha("▶");

        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(0, 0));
        panel.add(btnAnterior);
        panel.add(btnSiguiente);

        btnAnterior.setBounds(20, 150, 50, 50);
        btnSiguiente.setBounds(1290, 150, 50, 50);

        return panel;
    }

    private JButton crearBotonFlecha(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 22));
        boton.setForeground(Color.BLACK);
        boton.setBackground(new Color(255, 255, 255, 150));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setVisible(false);

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setVisible(true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setVisible(false);
            }
        });

        boton.addActionListener(e -> {
            if (timer != null) {
                timer.restart();
            }
            if (texto.equals("◀")) {
                indiceActual = (indiceActual - 1 + imagenes.size()) % imagenes.size();
            } else {
                indiceActual = (indiceActual + 1) % imagenes.size();
            }
            lblImagen.repaint();
        });

        return boton;
    }
}
