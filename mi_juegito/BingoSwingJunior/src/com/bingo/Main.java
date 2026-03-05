package com.bingo;

import javax.swing.SwingUtilities;

// Clase principal que arranca la aplicación
public class Main {

    public static void main(String[] args) {
        // Lanzamos la pantalla de inicio en el hilo de Swing (buena práctica)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Primero mostramos la pantalla de inicio con la imagen
                PantallaInicio pantalla = new PantallaInicio();
                pantalla.setVisible(true);
            }
        });
    }
}
