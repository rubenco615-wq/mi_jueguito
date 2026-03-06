package com.bingo;

import javax.swing.*;
import java.awt.*;

/**
 * Una "caja de herramientas" con métodos directos para sacar
 * ventanitas de aviso en pantalla rápido y sin escribir mucho código.
 */
public class DialogosJuego {

    private DialogosJuego() {
    }

    public static String pedirNombre(Component p) {
        String n = JOptionPane.showInputDialog(p, "¿Cómo te llamas?", "Nombre", JOptionPane.QUESTION_MESSAGE);
        return (n == null || n.trim().isEmpty()) ? "Jugador" : n.trim();
    }

    public static boolean confirmarFinalizar(Component p) {
        return JOptionPane.showConfirmDialog(p, "¿Seguro que quieres finalizar?", "Finalizar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static void mostrarLinea(Component p, String n) {
        JOptionPane.showMessageDialog(p, "🎉 ¡¡LÍNEA!! 🎉\n→ " + n, "¡Línea!", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void mostrarBingo(Component p, String n) {
        JOptionPane.showMessageDialog(p, "🎊 ¡¡BINGO!! 🎊\nGanador: " + n, "¡BINGO!", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void mostrarBomboVacio(Component p) {
        JOptionPane.showMessageDialog(p, "¡Bombo agotado!\nNadie ganó.", "Vacío", JOptionPane.INFORMATION_MESSAGE);
    }
}
