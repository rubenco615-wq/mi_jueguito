package com.bingo;

import javax.swing.*;
import java.awt.*;

public class VentanaJuego extends JFrame {

    private Juego juego;
    private PanelNorte panelNorte;
    private PanelCarton panelCartonJugador;
    private PanelCarton panelCartonMaquina;
    private PanelLog panelLog;
    private PanelBotones panelBotones;

    private boolean yaHayLineaEnPartida;
    private Temporizador timerAuto;

    public VentanaJuego() {
        setTitle("BingoSwingJunior - ¡A jugar!");
        setSize(1250, 590);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        juego = new Juego();

        panelNorte = new PanelNorte();
        panelLog = new PanelLog();
        panelBotones = new PanelBotones();
        panelCartonJugador = new PanelCarton("Tu Cartón  [haz clic para marcar]", new Color(25, 25, 55), false);
        panelCartonMaquina = new PanelCarton("Cartón Máquina  [automático]", new Color(140, 30, 30), true);

        JPanel panelCartones = new JPanel(new GridLayout(2, 1, 0, 12));
        panelCartones.setBackground(new Color(235, 235, 250));
        panelCartones.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 8));
        panelCartones.add(panelCartonJugador);
        panelCartones.add(panelCartonMaquina);

        add(panelNorte, BorderLayout.NORTH);
        add(panelCartones, BorderLayout.CENTER);
        add(panelLog, BorderLayout.EAST);
        add(panelBotones, BorderLayout.SOUTH);

        registrarEventos();

        // Cambiamos a la música de espera/juego nada más cargar la ventana
        SonidoManager.reproducirBGM("juego.wav");
    }

    private void registrarEventos() {
        panelBotones.getBotonIniciar().addActionListener(e -> {
            String nombre = DialogosJuego.pedirNombre(this).trim();
            if (timerAuto != null)
                timerAuto.detener();
            panelNorte.getChkAutoExtraccion().setSelected(false);

            juego.iniciarPartida(nombre);
            yaHayLineaEnPartida = false;

            panelCartonJugador.setTitulo("Tu Cartón  [" + nombre + "] — haz clic para marcar");
            actualizarCartones();

            panelBotones.getBotonIniciar().setEnabled(false);
            panelBotones.getBotonExtraer().setEnabled(true);
            panelBotones.getBotonFinalizar().setEnabled(true);
            panelNorte.getChkAutoExtraccion().setEnabled(true);
            panelNorte.getSpinnerVelocidad().setEnabled(true);

            panelLog.limpiarLog();
            panelNorte.resetNumero();
            panelLog.escribirLog("Partida iniciada!\nJugador: " + nombre + "\n¡Buena suerte!\n---------------");
        });

        panelBotones.getBotonExtraer().addActionListener(e -> procesarExtraccion());

        panelBotones.getBotonFinalizar().addActionListener(e -> {
            if (DialogosJuego.confirmarFinalizar(this))
                finalizarJuego("Partida finalizada manualmente.");
        });

        panelNorte.getChkAutoExtraccion().addActionListener(e -> {
            if (panelNorte.getChkAutoExtraccion().isSelected()) {
                int s = (Integer) panelNorte.getSpinnerVelocidad().getValue();
                timerAuto = new Temporizador(s, ev -> procesarExtraccion());
                timerAuto.iniciar();
                panelBotones.getBotonExtraer().setEnabled(false);
                panelLog.escribirLog("Auto ON [" + s + "s]");
            } else {
                if (timerAuto != null)
                    timerAuto.detener();
                panelBotones.getBotonExtraer().setEnabled(true);
                panelLog.escribirLog("Auto OFF");
            }
        });

        JButton[] celdas = panelCartonJugador.getCeldas();
        for (int i = 0; i < celdas.length; i++) {
            int indice = i;
            celdas[i].addActionListener(e -> marcarCeldaJugador(indice));
        }
    }

    private void marcarCeldaJugador(int indice) {
        if (!juego.isPartidaActiva())
            return;
        Carton c = juego.getJugador().getCarton();
        int fila = indice / 9, col = indice % 9;
        int num = c.getNumero(fila, col);

        if (num == 0 || c.isMarcado(fila, col))
            return;

        if (!juego.esNumeroExtraido(num)) {
            panelCartonJugador.getCeldas()[indice].setBackground(new Color(255, 70, 70));
            new Timer(350, ev -> {
                panelCartonJugador.actualizarCarton(juego.getJugador(), juego.isPartidaActiva());
                ((Timer) ev.getSource()).stop();
            }).start();
            return;
        }

        c.marcarNumero(num);
        panelLog.escribirLog("Marcas el " + num);
        panelCartonJugador.actualizarCarton(juego.getJugador(), true);

        verificarPremios(c, juego.getJugador().getNombre());
    }

    private void procesarExtraccion() {
        if (!juego.isPartidaActiva())
            return;
        int num = juego.extraerNumero();
        if (num == -1) {
            DialogosJuego.mostrarBomboVacio(this);
            finalizarJuego("Bombo vacío. Fin.");
            return;
        }

        panelNorte.setNumero(String.valueOf(num));
        panelLog.escribirLog("Nº " + num + " ← ¡márcalo!");
        actualizarCartones();

        new Timer(2000 + (int) (Math.random() * 3000), e -> {
            ((Timer) e.getSource()).stop();
            if (juego.isPartidaActiva() && juego.getMaquina() != null) {
                Carton cm = juego.getMaquina().getCarton();
                if (cm.contieneNumero(num)) {
                    juego.getMaquina().marcarNumero(num);
                    panelCartonMaquina.actualizarCarton(juego.getMaquina(), true);
                    verificarPremios(cm, "Máquina");
                }
            }
        }).start();
    }

    private void verificarPremios(Carton c, String nombre) {
        if (!yaHayLineaEnPartida && c.comprobarLinea()) {
            yaHayLineaEnPartida = true;
            panelLog.escribirLog("¡LÍNEA! " + nombre);
            SonidoManager.reproducirLinea();
            DialogosJuego.mostrarLinea(this, nombre);
        }
        if (c.comprobarBingo()) {
            panelLog.escribirLog("¡BINGO! " + nombre);
            SonidoManager.reproducirBingo();
            DialogosJuego.mostrarBingo(this, nombre);
            finalizarJuego("BINGO de " + nombre + "!");
        }
    }

    private void finalizarJuego(String msg) {
        juego.finalizarPartida();
        if (timerAuto != null)
            timerAuto.detener();
        actualizarCartones();

        panelNorte.getChkAutoExtraccion().setSelected(false);
        panelNorte.getChkAutoExtraccion().setEnabled(false);
        panelNorte.getSpinnerVelocidad().setEnabled(false);
        panelNorte.resetNumero();

        panelBotones.getBotonExtraer().setEnabled(false);
        panelBotones.getBotonFinalizar().setEnabled(false);
        panelBotones.getBotonIniciar().setEnabled(true);
        panelLog.escribirLog(msg);
    }

    private void actualizarCartones() {
        panelCartonJugador.actualizarCarton(juego.getJugador(), juego.isPartidaActiva());
        panelCartonMaquina.actualizarCarton(juego.getMaquina(), juego.isPartidaActiva());
    }
}
