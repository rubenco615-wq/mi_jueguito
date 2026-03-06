package com.bingo;

import javax.swing.*;
import java.awt.*;

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
        add(new JScrollPane(areaLog), BorderLayout.CENTER);
    }

    public void escribirLog(String msj) {
        areaLog.append(msj + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    public void limpiarLog() {
        areaLog.setText("");
    }
}
