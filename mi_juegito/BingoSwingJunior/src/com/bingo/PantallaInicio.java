package com.bingo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// Pantalla de inicio del juego
// Muestra una imagen de fondo y un botón para empezar
// Pon tu imagen en: resources/splash.png
public class PantallaInicio extends JFrame {

    public PantallaInicio() {
        setTitle("BingoSwingJunior - Inicio");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrar en pantalla
        setResizable(false);

        // Añadimos el panel con imagen de fondo
        PanelFondo panelFondo = new PanelFondo();
        panelFondo.setLayout(new GridBagLayout()); // para centrar el botón

        // Creamos el botón de jugar
        JButton botonJugar = new JButton("¡ J U G A R !");
        botonJugar.setFont(new Font("Arial", Font.BOLD, 30));
        botonJugar.setBackground(new Color(255, 200, 0)); // amarillo dorado
        botonJugar.setForeground(new Color(20, 20, 20)); // texto oscuro
        botonJugar.setFocusPainted(false);
        botonJugar.setPreferredSize(new Dimension(240, 65));
        botonJugar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 140, 0), 3),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        botonJugar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover: cambia de color al pasar el ratón
        botonJugar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botonJugar.setBackground(new Color(255, 230, 50));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                botonJugar.setBackground(new Color(255, 200, 0));
            }
        });

        // Al pulsar el botón: cerramos la pantalla de inicio y abrimos el juego
        botonJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // cerramos esta ventana
                VentanaJuego ventanaJuego = new VentanaJuego();
                ventanaJuego.setVisible(true); // abrimos el juego
            }
        });

        // Añadimos el botón al panel (GridBagLayout lo centra automáticamente)
        panelFondo.add(botonJugar);
        add(panelFondo);
    }

    // ---------------------------------------------------------------
    // Panel interior que pinta la imagen de fondo
    // Si no existe resources/splash.png pintamos un fondo oscuro
    // ---------------------------------------------------------------
    class PanelFondo extends JPanel {

        Image imagen; // la imagen de fondo

        PanelFondo() {
            // Intentamos cargar la imagen desde la carpeta resources
            File archivoImagen = new File("resources/splash.png");
            if (archivoImagen.exists()) {
                imagen = new ImageIcon(archivoImagen.getAbsolutePath()).getImage();
                System.out.println("Imagen de inicio cargada correctamente.");
            } else {
                // Si no hay imagen, avisamos en consola (no es un error grave)
                System.out.println("Aviso: no se encontró resources/splash.png");
                System.out.println("Pon tu imagen ahí y reinicia. Se usará fondo por defecto.");
                imagen = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (imagen != null) {
                // Dibujamos la imagen escalada para llenar toda la ventana
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

                // Capa semitransparente oscura para que el botón resalte mejor
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 80)); // negro con 80/255 de opacidad
                g2d.fillRect(0, 0, getWidth(), getHeight());
            } else {
                // Fondo degradado oscuro por defecto si no hay imagen
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint degradado = new GradientPaint(
                        0, 0, new Color(10, 10, 50),
                        0, getHeight(), new Color(40, 10, 80));
                g2d.setPaint(degradado);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Texto de ejemplo si no hay imagen
                g2d.setColor(new Color(255, 215, 0));
                g2d.setFont(new Font("Arial", Font.BOLD, 60));
                g2d.drawString("🎱 BINGO", getWidth() / 2 - 140, getHeight() / 2 - 60);
                g2d.setColor(new Color(200, 200, 200));
                g2d.setFont(new Font("Arial", Font.PLAIN, 16));
                g2d.drawString("Pon tu imagen en: resources/splash.png", getWidth() / 2 - 160, getHeight() / 2 - 20);
            }
        }
    }
}
