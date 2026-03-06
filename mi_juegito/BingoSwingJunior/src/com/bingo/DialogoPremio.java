package com.bingo;

import javax.swing.*;
import java.awt.*;

/**
 * La típica ventana bonita (sin bordes del sistema operativo) que
 * salta para celebrarte cuando cantas Línea o Bingo.
 */
public class DialogoPremio extends JDialog {

    public DialogoPremio(Frame p, String title, String msg, Color bg) {
        super(p, title, true);
        setUndecorated(true);

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(bg);
        main.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

        JLabel lbl = new JLabel("<html><div style='text-align:center;'>" + msg.replace("\n", "<br>") + "</div></html>",
                SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(Color.WHITE);
        lbl.setBorder(BorderFactory.createEmptyBorder(30, 40, 10, 40));

        JButton btn = new JButton("¡GENIAL!");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(bg);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> dispose());

        JPanel pBtn = new JPanel();
        pBtn.setOpaque(false);
        pBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        pBtn.add(btn);

        main.add(lbl, BorderLayout.CENTER);
        main.add(pBtn, BorderLayout.SOUTH);
        add(main);
        pack();
        setLocationRelativeTo(p);
    }
}
