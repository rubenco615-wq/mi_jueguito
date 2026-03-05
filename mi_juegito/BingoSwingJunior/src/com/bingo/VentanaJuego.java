package com.bingo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana principal del juego de bingo estilo español (3x9).
 *
 * Actúa como ORQUESTADOR: monta los paneles de la UI y gestiona
 * todos los eventos (iniciar, extraer, marcar, turno máquina, finalizar).
 *
 * La construcción visual está delegada a:
 * - PanelNorte → número extraído + controles auto
 * - PanelCarton → cartón del jugador / cartón de la máquina
 * - PanelLog → log lateral de eventos
 * - PanelBotones → botones Iniciar / Extraer / Finalizar
 */
public class VentanaJuego extends JFrame {

    // ---- Lógica del juego ----
    private Juego juego;

    // ---- Paneles de la UI ----
    private PanelNorte panelNorte;
    private PanelCarton panelCartonJugador;
    private PanelCarton panelCartonMaquina;
    private PanelLog panelLog;
    private PanelBotones panelBotones;

    // ---- Estado de la partida ----
    /** Evita avisar dos veces de la misma línea en una partida. */
    private boolean yaHayLineaEnPartida;

    /** Temporizador de extracción automática (null si está desactivado). */
    private Temporizador timerAuto;

    // ================================================================
    // Constructor
    // ================================================================
    public VentanaJuego() {
        setTitle("BingoSwingJunior - ¡A jugar!");
        setSize(1250, 590);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        juego = new Juego();
        yaHayLineaEnPartida = false;
        timerAuto = null;

        // Crear paneles
        panelNorte = new PanelNorte();
        panelLog = new PanelLog();
        panelBotones = new PanelBotones();

        panelCartonJugador = new PanelCarton(
                "Tu Cartón  [haz clic para marcar]",
                new Color(25, 25, 55),
                false);

        panelCartonMaquina = new PanelCarton(
                "Cartón Máquina  [automático]",
                new Color(140, 30, 30),
                true);

        // Panel central con los dos cartones apilados
        JPanel panelCartones = new JPanel(new GridLayout(2, 1, 0, 12));
        panelCartones.setBackground(new Color(235, 235, 250));
        panelCartones.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 8));
        panelCartones.add(panelCartonJugador);
        panelCartones.add(panelCartonMaquina);

        // Añadir todo al JFrame
        add(panelNorte, BorderLayout.NORTH);
        add(panelCartones, BorderLayout.CENTER);
        add(panelLog, BorderLayout.EAST);
        add(panelBotones, BorderLayout.SOUTH);

        // Registrar eventos
        registrarEventoIniciar();
        registrarEventoExtraer();
        registrarEventoFinalizar();
        registrarEventoAuto();
        registrarEventosCeldasJugador();
    }

    // ================================================================
    // Registro de eventos de los botones y controles
    // ================================================================

    private void registrarEventoIniciar() {
        panelBotones.getBotonIniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = DialogosJuego.pedirNombre(VentanaJuego.this);

                pararTimerAuto();
                panelNorte.getChkAutoExtraccion().setSelected(false);

                juego.iniciarPartida(nombre.trim());
                yaHayLineaEnPartida = false;

                panelCartonJugador.setTitulo(
                        "Tu Cartón  [" + nombre.trim() + "] — haz clic para marcar");
                actualizarCartones();

                // Habilitar / deshabilitar controles
                panelBotones.getBotonIniciar().setEnabled(false);
                panelBotones.getBotonExtraer().setEnabled(true);
                panelBotones.getBotonFinalizar().setEnabled(true);
                panelNorte.getChkAutoExtraccion().setEnabled(true);
                panelNorte.getSpinnerVelocidad().setEnabled(true);

                panelLog.limpiarLog();
                panelNorte.resetNumero();
                panelLog.escribirLog("Partida iniciada!");
                panelLog.escribirLog("Jugador: " + nombre.trim());
                panelLog.escribirLog("¡Buena suerte!");
                panelLog.escribirLog("---------------");
            }
        });
    }

    private void registrarEventoExtraer() {
        panelBotones.getBotonExtraer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarExtraccion();
            }
        });
    }

    private void registrarEventoFinalizar() {
        panelBotones.getBotonFinalizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (DialogosJuego.confirmarFinalizar(VentanaJuego.this)) {
                    finalizarJuego("Partida finalizada manualmente.");
                }
            }
        });
    }

    private void registrarEventoAuto() {
        panelNorte.getChkAutoExtraccion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelNorte.getChkAutoExtraccion().isSelected()) {
                    int segundos = (Integer) panelNorte.getSpinnerVelocidad().getValue();
                    timerAuto = new Temporizador(segundos, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ev) {
                            procesarExtraccion();
                        }
                    });
                    timerAuto.iniciar();
                    panelBotones.getBotonExtraer().setEnabled(false);
                    panelLog.escribirLog("Auto ON [" + segundos + "s]");
                } else {
                    pararTimerAuto();
                    panelBotones.getBotonExtraer().setEnabled(true);
                    panelLog.escribirLog("Auto OFF");
                }
            }
        });
    }

    /** Añade un ActionListener a cada celda del cartón del jugador. */
    private void registrarEventosCeldasJugador() {
        JButton[] celdas = panelCartonJugador.getCeldas();
        for (int i = 0; i < celdas.length; i++) {
            final int indice = i;
            celdas[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    marcarCeldaJugador(indice);
                }
            });
        }
    }

    // ================================================================
    // Lógica de juego
    // ================================================================

    /**
     * El jugador hace clic en una celda de su cartón.
     * Solo se puede marcar si el número ya ha sido extraído.
     * Si no ha salido → flash rojo de aviso.
     */
    private void marcarCeldaJugador(int indice) {
        if (juego.getJugador() == null || !juego.isPartidaActiva()) {
            return;
        }

        Carton carton = juego.getJugador().getCarton();
        int fila = indice / 9;
        int col = indice % 9;
        int numero = carton.numeros[fila][col];

        // Celda vacía o ya marcada → ignorar
        if (numero == 0 || carton.marcados[fila][col]) {
            return;
        }

        // ¿Ha sido extraído ese número?
        if (!juego.esNumeroExtraido(numero)) {
            // Flash rojo de aviso: aún no ha salido
            JButton[] celdas = panelCartonJugador.getCeldas();
            celdas[indice].setBackground(new Color(255, 70, 70));
            Timer timerFlash = new Timer(350, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    panelCartonJugador.actualizarCarton(juego.getJugador(), juego.isPartidaActiva());
                    ((Timer) ev.getSource()).stop();
                }
            });
            timerFlash.start();
            return;
        }

        // Marcamos el número
        carton.marcarNumero(numero);
        panelLog.escribirLog("Marcas el " + numero);
        panelCartonJugador.actualizarCarton(juego.getJugador(), juego.isPartidaActiva());

        // ¿Línea? (solo avisamos una vez por partida)
        if (!yaHayLineaEnPartida && carton.comprobarLinea()) {
            yaHayLineaEnPartida = true;
            panelLog.escribirLog("¡LÍNEA! " + juego.getJugador().getNombre());
            SonidoManager.reproducirLinea();
            DialogosJuego.mostrarLinea(this, juego.getJugador().getNombre());
        }

        // ¿Bingo?
        if (carton.comprobarBingo()) {
            panelLog.escribirLog("¡BINGO! " + juego.getJugador().getNombre());
            SonidoManager.reproducirBingo();
            DialogosJuego.mostrarBingo(this, juego.getJugador().getNombre());
            finalizarJuego("BINGO de " + juego.getJugador().getNombre() + "!");
        }
    }

    /**
     * Extrae un número del bombo (manual o automático).
     * La máquina marcará con un retraso aleatorio (retraso humano).
     */
    private void procesarExtraccion() {
        if (!juego.isPartidaActiva()) {
            return;
        }

        int numero = juego.extraerNumero();

        if (numero == -1) {
            DialogosJuego.mostrarBomboVacio(VentanaJuego.this);
            finalizarJuego("Bombo vacío. Fin.");
            return;
        }

        panelNorte.setNumero(String.valueOf(numero));
        panelLog.escribirLog("Nº " + numero + " ← ¡márcalo!");
        SonidoManager.reproducirNumero();

        actualizarCartones();

        // Lanzamos el turno de la máquina con retraso aleatorio (2-5 s) para parecer
        // más humana
        int retrasoMs = 2000 + (int) (Math.random() * 3000);
        Timer timerMaquina = new Timer(retrasoMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Timer) e.getSource()).stop();
                turnoMaquina(numero);
            }
        });
        timerMaquina.start();
    }

    /** La máquina intenta marcar el número extraído en su cartón. */
    private void turnoMaquina(int numero) {
        if (!juego.isPartidaActiva() || juego.getMaquina() == null) {
            return;
        }

        Carton cartonMaquina = juego.getMaquina().getCarton();

        if (cartonMaquina.contieneNumero(numero)) {
            juego.getMaquina().marcarNumero(numero);
            panelCartonMaquina.actualizarCarton(juego.getMaquina(), true);

            // ¿Línea de la máquina? (solo una vez)
            if (!yaHayLineaEnPartida && cartonMaquina.comprobarLinea()) {
                yaHayLineaEnPartida = true;
                panelLog.escribirLog("¡LÍNEA! Máquina");
                SonidoManager.reproducirLinea();
                DialogosJuego.mostrarLinea(VentanaJuego.this, "Máquina");
            }

            // ¿Bingo de la máquina?
            if (cartonMaquina.comprobarBingo()) {
                panelLog.escribirLog("¡BINGO! Máquina");
                SonidoManager.reproducirBingo();
                DialogosJuego.mostrarBingo(VentanaJuego.this, "Máquina");
                finalizarJuego("BINGO de Máquina!");
            }
        }
    }

    // ================================================================
    // Helpers
    // ================================================================

    /** Para el temporizador automático si está en marcha. */
    private void pararTimerAuto() {
        if (timerAuto != null) {
            timerAuto.detener();
            timerAuto = null;
        }
    }

    /** Finaliza la partida y deja la UI en estado "listo para iniciar". */
    private void finalizarJuego(String mensajeLog) {
        juego.finalizarPartida();
        pararTimerAuto();
        actualizarCartones();

        panelNorte.getChkAutoExtraccion().setSelected(false);
        panelNorte.getChkAutoExtraccion().setEnabled(false);
        panelNorte.getSpinnerVelocidad().setEnabled(false);
        panelNorte.resetNumero();

        panelBotones.getBotonExtraer().setEnabled(false);
        panelBotones.getBotonFinalizar().setEnabled(false);
        panelBotones.getBotonIniciar().setEnabled(true);

        panelLog.escribirLog(mensajeLog);
    }

    /** Refresca los dos cartones a la vez. */
    private void actualizarCartones() {
        panelCartonJugador.actualizarCarton(juego.getJugador(), juego.isPartidaActiva());
        panelCartonMaquina.actualizarCarton(juego.getMaquina(), juego.isPartidaActiva());
    }
}
