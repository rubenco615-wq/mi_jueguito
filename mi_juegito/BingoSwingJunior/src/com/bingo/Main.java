package com.bingo;

import javax.swing.SwingUtilities;

/**
 * Clase principal: El punto de entrada del programa.
 */
public class Main {
    public static void main(String[] args) {
        // Ejecutamos la ventana de inicio dentro del hilo especial de Swing
        // Esto evita cuelgues al arrancar la interfaz gráfica.
        SwingUtilities.invokeLater(() -> new PantallaInicio().setVisible(true));
    }
}
