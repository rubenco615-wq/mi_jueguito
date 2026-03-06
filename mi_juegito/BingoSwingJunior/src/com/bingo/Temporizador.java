package com.bingo;

import javax.swing.Timer;
import java.awt.event.ActionListener;

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
}
