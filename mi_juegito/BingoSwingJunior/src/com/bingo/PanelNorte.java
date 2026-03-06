package com.bingo;

import javax.swing.*;
import java.awt.*;

/**
 * La cabecera (arriba del todo) donde sale el Número Extraído GIGANTE
 * y donde están los controles del modo Automático y la leyenda de colores.
 */
public class PanelNorte extends JPanel {
    private JLabel labelNumero;
    private JCheckBox chkAutoExtraccion;
    private JSpinner spinnerVelocidad;

    public PanelNorte() {
        setLayout(new BorderLayout());
        setBackground(new Color(25, 25, 55));
        setBorder(BorderFactory.createEmptyBorder(8, 15, 4, 15));

        labelNumero = new JLabel("--", SwingConstants.CENTER);
        labelNumero.setFont(new Font("Arial", Font.BOLD, 72));
        labelNumero.setForeground(new Color(255, 215, 0));
        add(labelNumero, BorderLayout.CENTER);

        JPanel pAuto = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 2));
        pAuto.setBackground(new Color(25, 25, 55));

        chkAutoExtraccion = new JCheckBox("⏱ Auto");
        chkAutoExtraccion.setFont(new Font("Arial", Font.BOLD, 13));
        chkAutoExtraccion.setBackground(new Color(25, 25, 55));
        chkAutoExtraccion.setForeground(Color.WHITE);
        chkAutoExtraccion.setEnabled(false);

        spinnerVelocidad = new JSpinner(new SpinnerNumberModel(3, 1, 15, 1));
        spinnerVelocidad.setFont(new Font("Arial", Font.BOLD, 13));
        spinnerVelocidad.setPreferredSize(new Dimension(55, 26));
        spinnerVelocidad.setEnabled(false);

        JLabel lblInfo = new JLabel("Velocidad:");
        lblInfo.setForeground(Color.LIGHT_GRAY);
        JLabel lblSeg = new JLabel("seg    | 🟦 Columnas   🟨 Marcado   🔴 Error");
        lblSeg.setForeground(Color.LIGHT_GRAY);

        pAuto.add(chkAutoExtraccion);
        pAuto.add(lblInfo);
        pAuto.add(spinnerVelocidad);
        pAuto.add(lblSeg);
        add(pAuto, BorderLayout.SOUTH);
    }

    public void setNumero(String txt) {
        labelNumero.setText(txt);
    }

    public void resetNumero() {
        labelNumero.setText("--");
    }

    public JCheckBox getChkAutoExtraccion() {
        return chkAutoExtraccion;
    }

    public JSpinner getSpinnerVelocidad() {
        return spinnerVelocidad;
    }
}
