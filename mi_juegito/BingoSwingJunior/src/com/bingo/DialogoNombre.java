package com.bingo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Una ventanita personalizada que te frena antes de empezar
 * para pedirte tu nombre.
 */
public class DialogoNombre extends JDialog {
    private String nombre = "Jugador";
    private JTextField txtNombre;

    public DialogoNombre(Frame p) {
        super(p, "Nuevo Jugador", true);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(40, 45, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("¿Cómo te llamas?", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        txtNombre = new JTextField(15);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtNombre.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridy = 1;
        panel.add(txtNombre, gbc);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEntrar.setBackground(new Color(80, 200, 120));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.addActionListener(e -> aceptar());

        txtNombre.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    aceptar();
            }
        });

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        panel.add(btnEntrar, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(p);
    }

    private void aceptar() {
        if (!txtNombre.getText().trim().isEmpty())
            nombre = txtNombre.getText().trim();
        dispose();
    }

    public String getNombre() {
        return nombre;
    }
}
