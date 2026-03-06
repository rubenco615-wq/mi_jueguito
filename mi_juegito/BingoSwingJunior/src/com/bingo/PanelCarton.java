package com.bingo;

import javax.swing.*;
import java.awt.*;

/**
 * Este panel es el dibujo de 3x9 casillas que ves en pantalla.
 * Convierte un Carton.java (que son datos) en cuadraditos de colores.
 */
public class PanelCarton extends JPanel {

    private final boolean esMaquina; // Sirve para saber si pintarlo de rojo (Máquina) o azul (Tú)
    private JLabel labelTitulo;
    private JButton[] celdas;

    public PanelCarton(String textTitulo, Color colorTitulo, boolean esMaquina) {
        this.esMaquina = esMaquina;
        setLayout(new BorderLayout(0, 4));
        setBackground(new Color(235, 235, 250));

        labelTitulo = new JLabel(textTitulo, SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        labelTitulo.setForeground(colorTitulo);
        add(labelTitulo, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 9, 3, 3));
        grid.setBackground(new Color(235, 235, 250));
        celdas = new JButton[27];
        for (int i = 0; i < 27; i++) {
            celdas[i] = crearCelda();
            grid.add(celdas[i]);
        }
        add(grid, BorderLayout.CENTER);
    }

    public JButton[] getCeldas() {
        return celdas;
    }

    public void setTitulo(String texto) {
        labelTitulo.setText(texto);
    }

    public void actualizarCarton(Participante participante, boolean juegoActivo) {
        if (participante == null)
            return;
        Carton carton = participante.getCarton();
        int indice = 0;
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 9; col++) {
                int num = carton.getNumero(fila, col);
                boolean marcado = carton.isMarcado(fila, col);
                JButton celda = celdas[indice];

                celda.setText(num == 0 ? "" : marcado ? "X " + num : String.valueOf(num));
                celda.setEnabled(num != 0 && !marcado && !esMaquina && juegoActivo);

                if (num == 0) {
                    celda.setBackground(new Color(165, 165, 185));
                    celda.setForeground(new Color(165, 165, 185));
                } else if (marcado) {
                    celda.setBackground(esMaquina ? new Color(255, 110, 110) : new Color(255, 210, 50));
                    celda.setForeground(new Color(15, 15, 15));
                } else {
                    celda.setBackground(esMaquina ? new Color(230, 200, 200) : new Color(200, 200, 230));
                    celda.setForeground(esMaquina ? new Color(80, 20, 20) : new Color(25, 25, 55));
                }
                indice++;
            }
        }
    }

    private JButton crearCelda() {
        JButton celda = new JButton("?");
        celda.setFont(new Font("Arial", Font.BOLD, 12));
        celda.setFocusPainted(false);
        celda.setEnabled(false);
        celda.setBackground(esMaquina ? new Color(230, 200, 200) : new Color(200, 200, 230));
        celda.setForeground(esMaquina ? new Color(80, 20, 20) : new Color(25, 25, 55));
        return celda;
    }
}
