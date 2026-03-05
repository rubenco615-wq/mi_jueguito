package com.bingo;

import javax.swing.*;
import java.awt.*;

/**
 * Panel inferior con los botones de control de la partida:
 * Iniciar, Extraer Número y Finalizar.
 */
public class PanelBotones extends JPanel {

    private JButton botonIniciar;
    private JButton botonExtraer;
    private JButton botonFinalizar;

    public PanelBotones() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 8));
        setBackground(new Color(25, 25, 55));

        botonIniciar = crearBoton("▶  Iniciar Partida", new Color(45, 170, 45));
        botonExtraer = crearBoton("🎱  Extraer Número", new Color(45, 95, 195));
        botonFinalizar = crearBoton("⏹  Finalizar", new Color(190, 45, 45));

        // Al arrancar solo "Iniciar" está disponible
        botonExtraer.setEnabled(false);
        botonFinalizar.setEnabled(false);

        add(botonIniciar);
        add(botonExtraer);
        add(botonFinalizar);
    }

    // --- Métodos públicos ---

    public JButton getBotonIniciar() {
        return botonIniciar;
    }

    public JButton getBotonExtraer() {
        return botonExtraer;
    }

    public JButton getBotonFinalizar() {
        return botonFinalizar;
    }

    // --- Privado ---

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(180, 40));
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }
}
