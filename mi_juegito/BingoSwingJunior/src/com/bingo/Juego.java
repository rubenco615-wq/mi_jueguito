package com.bingo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la lógica principal de la partida.
 * Conoce quiénes juegan (humanos y máquina) y controla de dónde salen las bolas
 * (el bombo).
 */
public class Juego {
    private List<Participante> participantes; // Lista con el Humano y la Máquina
    private Bombo bombo; // El bombo que reparte los números
    private boolean partidaActiva; // True si estamos jugando, False si no

    public Juego() {
        participantes = new ArrayList<>();
        partidaActiva = false;
    }

    /** Prepara y arranca una partida nueva */
    public void iniciarPartida(String nombreJugador) {
        participantes.clear();

        // Siempre juegan 2 participantes: tú y el ordenador
        participantes.add(new Jugador(nombreJugador));
        participantes.add(new Jugador("Máquina"));

        // Creamos un bombo nuevo y mezclado
        bombo = new Bombo();
        partidaActiva = true;
    }

    /** Marca la partida como terminada (impide seguir sacando o marcando bolas) */
    public void finalizarPartida() {
        partidaActiva = false;
    }

    /** Saca el siguiente número del bombo. Devuelve -1 si hay error o está vacío */
    public int extraerNumero() {
        if (!partidaActiva || bombo == null)
            return -1;
        return bombo.sacarNumero();
    }

    /** Devuelve true si un número específico ya salió del bombo en esta partida */
    public boolean esNumeroExtraido(int numero) {
        return bombo != null && bombo.contieneExtraido(numero);
    }

    // --- Getters de ayuda para saber quién es quién ---

    /** Obtiene al participante [0] (siempre es el humano) */
    public Participante getJugador() {
        return participantes.isEmpty() ? null : participantes.get(0);
    }

    /** Obtiene al participante [1] (siempre es la CPU) */
    public Participante getMaquina() {
        return participantes.size() > 1 ? participantes.get(1) : null;
    }

    public boolean isPartidaActiva() {
        return partidaActiva;
    }
}
