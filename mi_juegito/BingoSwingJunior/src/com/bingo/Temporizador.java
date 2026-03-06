package com.bingo;

import javax.swing.Timer;
import java.awt.event.ActionListener;

/**
 * Un reloj interno que ejecuta una acción cada X segundos.
 * Lo usamos para el modo "Auto", avisándole de que saque una bola.
 */
public class Temporizador {
    private Timer timer;

    public Temporizador(int segundos, ActionListener accion) {
        timer = new Timer(segundos * 1000, accion);
    }

    public void iniciar() {
        timer.start();
    }

    public void detener() {
        timer.stop();
    }

    public void cambiarIntervalo(int segundos) {
        timer.setDelay(segundos * 1000);
        timer.setInitialDelay(segundos * 1000);
        timer.restart();
    }
}
