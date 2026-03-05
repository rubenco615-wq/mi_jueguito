package com.bingo;

import javax.swing.*;
import java.awt.*;

/**
 * Centraliza todos los diálogos de la aplicación.
 * Usa JDialog internamente (a través de JOptionPane) para mantener
 * el código de VentanaJuego libre de llamadas directas a diálogos.
 */
public class DialogosJuego {

    // Construtor privado: clase de utilidad, no se instancia
    private DialogosJuego() {
    }

    /**
     * Pregunta el nombre del jugador al inicio de la partida.
     * 
     * @return nombre introducido, o "Jugador" si cancela o deja vacío.
     */
    public static String pedirNombre(Component padre) {
        String nombre = JOptionPane.showInputDialog(
                padre,
                "¿Cómo te llamas?",
                "Nombre del jugador",
                JOptionPane.QUESTION_MESSAGE);
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Jugador";
        }
        return nombre.trim();
    }

    /**
     * Pide confirmación para finalizar la partida.
     * 
     * @return true si el jugador confirma, false si cancela.
     */
    public static boolean confirmarFinalizar(Component padre) {
        int respuesta = JOptionPane.showConfirmDialog(
                padre,
                "¿Seguro que quieres finalizar la partida?",
                "Finalizar",
                JOptionPane.YES_NO_OPTION);
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Muestra el aviso de línea conseguida.
     * 
     * @param padre  Ventana padre.
     * @param nombre Nombre del ganador de la línea ("Máquina" o el nombre del
     *               jugador).
     */
    public static void mostrarLinea(Component padre, String nombre) {
        JOptionPane.showMessageDialog(
                padre,
                "🎉 ¡¡LÍNEA!! 🎉\n→ " + nombre,
                "¡Línea!", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra el aviso de bingo conseguido.
     * 
     * @param padre  Ventana padre.
     * @param nombre Nombre del ganador ("Máquina" o el nombre del jugador).
     */
    public static void mostrarBingo(Component padre, String nombre) {
        JOptionPane.showMessageDialog(
                padre,
                "🎊 ¡¡BINGO!! 🎊\nGanador: " + nombre,
                "¡BINGO!", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Avisa de que el bombo se ha quedado sin números.
     */
    public static void mostrarBomboVacio(Component padre) {
        JOptionPane.showMessageDialog(
                padre,
                "¡Se han agotado todos los números del bombo!\nNadie ganó esta vez.",
                "Bombo vacío", JOptionPane.INFORMATION_MESSAGE);
    }
}
