package com.bingo;

import javax.swing.*;
import java.awt.*;

/**
 * Panel superior de la ventana de juego.
 * Muestra el número extraído del bombo (grande, en dorado)
 * y los controles de extracción automática (checkbox + spinner).
 */
public class PanelNorte extends JPanel {

    private JLabel labelNumero;
    private JCheckBox chkAutoExtraccion;
    private JSpinner spinnerVelocidad;

    public PanelNorte() {
        setLayout(new BorderLayout());
        setBackground(new Color(25, 25, 55));
        setBorder(BorderFactory.createEmptyBorder(8, 15, 4, 15));

        // Número extraído en grande
        labelNumero = new JLabel("--");
        labelNumero.setFont(new Font("Arial", Font.BOLD, 72));
        labelNumero.setForeground(new Color(255, 215, 0));
        labelNumero.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelNumero, BorderLayout.CENTER);

        // Panel con controles de auto-extracción y leyenda de colores
        add(crearPanelAuto(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelAuto() {
        JPanel panelAuto = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 2));
        panelAuto.setBackground(new Color(25, 25, 55));

        chkAutoExtraccion = new JCheckBox("⏱ Auto");
        chkAutoExtraccion.setFont(new Font("Arial", Font.BOLD, 13));
        chkAutoExtraccion.setBackground(new Color(25, 25, 55));
        chkAutoExtraccion.setForeground(Color.WHITE);
        chkAutoExtraccion.setEnabled(false);

        JLabel labelVelocidad = new JLabel("Velocidad:");
        labelVelocidad.setForeground(new Color(200, 200, 200));
        labelVelocidad.setFont(new Font("Arial", Font.PLAIN, 13));

        SpinnerNumberModel modeloSpinner = new SpinnerNumberModel(3, 1, 15, 1);
        spinnerVelocidad = new JSpinner(modeloSpinner);
        spinnerVelocidad.setFont(new Font("Arial", Font.BOLD, 13));
        spinnerVelocidad.setPreferredSize(new Dimension(55, 26));
        spinnerVelocidad.setEnabled(false);

        JLabel labelSeg = new JLabel("seg    |");
        labelSeg.setForeground(new Color(160, 160, 200));
        labelSeg.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel leyenda = new JLabel("🟦 Columnas   🟨 Marcado   🔴 Error");
        leyenda.setFont(new Font("Arial", Font.PLAIN, 12));
        leyenda.setForeground(new Color(170, 170, 210));

        panelAuto.add(chkAutoExtraccion);
        panelAuto.add(labelVelocidad);
        panelAuto.add(spinnerVelocidad);
        panelAuto.add(labelSeg);
        panelAuto.add(leyenda);

        return panelAuto;
    }

    // --- Métodos públicos ---

    /** Actualiza el número grande mostrado en pantalla. */
    public void setNumero(String texto) {
        labelNumero.setText(texto);
    }

    /** Resetea el número grande a "--". */
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
