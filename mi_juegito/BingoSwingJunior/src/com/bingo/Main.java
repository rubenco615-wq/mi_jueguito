package com.bingo;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaInicio().setVisible(true));
    }
}
