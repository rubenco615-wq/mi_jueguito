package com.bingo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Ventana inicial del juego que muestra la pantalla de carga (Splash)
 * y un gran botón para comenzar.
 */
public class PantallaInicio extends JFrame {

    private Image imagenFondo;

    public PantallaInicio() {
        setTitle("BingoSwingJunior");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        File f = new File("resources/splash.png");
        if (f.exists()) {
            imagenFondo = new ImageIcon(f.getAbsolutePath()).getImage();
        }

        JPanel panelPersonalizado = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(25, 25, 55));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelPersonalizado.setLayout(null);

        JButton botonJugar = new JButton("¡JUGAR!");
        botonJugar.setBounds(250, 380, 200, 60);
        botonJugar.setFont(new Font("Arial", Font.BOLD, 28));
        botonJugar.setBackground(new Color(255, 210, 50));
        botonJugar.setForeground(new Color(25, 25, 55));
        botonJugar.setFocusPainted(false);
        botonJugar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botonJugar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botonJugar.setBackground(new Color(255, 230, 100));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonJugar.setBackground(new Color(255, 210, 50));
            }
        });

        botonJugar.addActionListener(e -> {
            setVisible(false);
            dispose();
            new VentanaJuego().setVisible(true);
        });

        panelPersonalizado.add(botonJugar);
        add(panelPersonalizado);

        // Arrancar música de la pantalla de inicio
        SonidoManager.reproducirBGM("inicio.wav");
    }
}
