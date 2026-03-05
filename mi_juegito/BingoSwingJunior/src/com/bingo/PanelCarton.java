package com.bingo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Panel reutilizable que muestra un cartón de bingo 3x9.
 * Sirve tanto para el jugador (celdas clicables) como para la máquina (solo
 * visual).
 *
 * Colores del cartón del JUGADOR:
 * - Vacía (0): gris, desactivada
 * - Sin salir: azul, activada (clic hará flash rojo si aún no ha salido)
 * - Marcada (X): amarilla, desactivada
 *
 * Colores del cartón de la MÁQUINA:
 * - Vacía: gris
 * - Normal: rosado
 * - Marcada: rojo
 */
public class PanelCarton extends JPanel {

    private final boolean esMaquina;
    private JLabel labelTitulo;
    private JButton[] celdas;

    /**
     * @param textTitulo  Texto del título sobre el cartón.
     * @param colorTitulo Color del texto del título.
     * @param esMaquina   true = cartón de la máquina (solo visual), false =
     *                    jugador.
     */
    public PanelCarton(String textTitulo, Color colorTitulo, boolean esMaquina) {
        this.esMaquina = esMaquina;
        setLayout(new BorderLayout(0, 4));
        setBackground(new Color(235, 235, 250));

        // Título
        labelTitulo = new JLabel(textTitulo, SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        labelTitulo.setForeground(colorTitulo);
        add(labelTitulo, BorderLayout.NORTH);

        // Cuadrícula 3x9
        JPanel grid = new JPanel(new GridLayout(3, 9, 3, 3));
        grid.setBackground(new Color(235, 235, 250));
        celdas = new JButton[27];
        for (int i = 0; i < 27; i++) {
            celdas[i] = crearCelda("?");
            grid.add(celdas[i]);
        }
        add(grid, BorderLayout.CENTER);
    }

    // --- Métodos públicos ---

    /**
     * Devuelve el array de celdas (JButton[27]) para que VentanaJuego añada
     * ActionListeners.
     */
    public JButton[] getCeldas() {
        return celdas;
    }

    /**
     * Cambia el texto del título (por ejemplo al incluir el nombre del jugador).
     */
    public void setTitulo(String texto) {
        labelTitulo.setText(texto);
    }

    /**
     * Repinta el cartón según el estado actual del participante.
     *
     * @param participante El jugador o la máquina cuyo cartón se pinta.
     * @param juegoActivo  Si la partida está en curso (habilita/deshabilita
     *                     celdas).
     */
    public void actualizarCarton(Participante participante, boolean juegoActivo) {
        if (participante == null) {
            return;
        }

        Carton carton = participante.getCarton();
        int indice = 0;

        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 9; col++) {
                int numero = carton.numeros[fila][col];
                boolean marcado = carton.marcados[fila][col];
                JButton celda = celdas[indice];

                if (numero == 0) {
                    // Celda vacía: gris, siempre desactivada
                    celda.setText("");
                    celda.setBackground(new Color(165, 165, 185));
                    celda.setForeground(new Color(165, 165, 185));
                    celda.setEnabled(false);

                } else if (marcado) {
                    // Número ya marcado
                    celda.setText("X " + numero);
                    if (esMaquina) {
                        celda.setBackground(new Color(255, 110, 110)); // rojo
                    } else {
                        celda.setBackground(new Color(255, 210, 50)); // amarillo
                    }
                    celda.setForeground(new Color(15, 15, 15));
                    celda.setEnabled(false);

                } else {
                    // Número sin marcar todavía
                    celda.setText(String.valueOf(numero));
                    if (esMaquina) {
                        celda.setBackground(new Color(230, 200, 200));
                        celda.setForeground(new Color(80, 20, 20));
                    } else {
                        celda.setBackground(new Color(200, 200, 230));
                        celda.setForeground(new Color(25, 25, 55));
                    }
                    // La máquina nunca es clicable
                    celda.setEnabled(!esMaquina && juegoActivo);
                }

                indice++;
            }
        }
    }

    // --- Privado ---

    private JButton crearCelda(String texto) {
        JButton celda = new JButton(texto);
        celda.setFont(new Font("Arial", Font.BOLD, 12));
        celda.setFocusPainted(false);
        celda.setEnabled(false);
        if (esMaquina) {
            celda.setBackground(new Color(230, 200, 200));
            celda.setForeground(new Color(80, 20, 20));
        } else {
            celda.setBackground(new Color(200, 200, 230));
            celda.setForeground(new Color(25, 25, 55));
        }
        return celda;
    }
}
