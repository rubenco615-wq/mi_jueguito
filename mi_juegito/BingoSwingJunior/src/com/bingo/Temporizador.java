package com.bingo;

import javax.swing.Timer;
import java.awt.event.ActionListener;

// Clase que representa el temporizador del juego
// Usa el Timer de javax.swing para ejecutar cosas en el hilo de Swing
public class Temporizador {

    // Intervalo de tiempo entre cada acción (en segundos)
    int intervaloSegundos;

    // El timer de Swing que ejecuta la acción
    Timer timer;

    // Constructor: recibe el intervalo en segundos y la acción a ejecutar
    public Temporizador(int intervaloSegundos, ActionListener accion) {
        this.intervaloSegundos = intervaloSegundos;
        // Convertimos los segundos a milisegundos para el Timer
        timer = new Timer(intervaloSegundos * 1000, accion);
    }

    // Inicia el temporizador
    public void iniciar() {
        timer.start();
    }

    // Detiene el temporizador
    public void detener() {
        timer.stop();
    }
}
