package com.bingo;

import javax.swing.*;
import java.awt.*;

/**
 * Panel lateral derecho que muestra el historial
 * de eventos de la partida (números sacados, línea, bingo…).
 */
public class PanelLog extends JPanel {

    private JTextArea areaLog;

    public PanelLog() {
        setLayout(new BorderLayout(0, 5));
        setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 12));
        setPreferredSize(new Dimension(175, 0));
        setBackground(new Color(235, 235, 250));

        JLabel titulo = new JLabel("📋 Log", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 13));
        titulo.setForeground(new Color(25, 25, 55));
        add(titulo, BorderLayout.NORTH);

        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 11));
        areaLog.setBackground(new Color(18, 18, 40));
        areaLog.setForeground(new Color(160, 255, 160));
        areaLog.setMargin(new Insets(5, 5, 5, 5));

        JScrollPane scrollLog = new JScrollPane(areaLog);
        add(scrollLog, BorderLayout.CENTER);
    }

    // --- Métodos públicos ---

    /** Añade una línea al log y hace scroll hasta el final. */
    public void escribirLog(String mensaje) {
        areaLog.append(mensaje + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    /** Borra todo el contenido del log. */
    public void limpiarLog() {
        areaLog.setText("");
    }
}
