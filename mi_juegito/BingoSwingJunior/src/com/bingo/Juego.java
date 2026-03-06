package com.bingo;

import java.util.ArrayList;
import java.util.List;

public class Juego {
    private List<Participante> participantes;
    private Bombo bombo;
    private boolean partidaActiva;

    public Juego() {
        participantes = new ArrayList<>();
        partidaActiva = false;
    }

    public void iniciarPartida(String nombreJugador) {
        participantes.clear();
        participantes.add(new Jugador(nombreJugador));
        participantes.add(new Jugador("Máquina"));
        bombo = new Bombo();
        partidaActiva = true;
    }

    public void finalizarPartida() {
        partidaActiva = false;
    }

    public int extraerNumero() {
        if (!partidaActiva || bombo == null)
            return -1;
        return bombo.sacarNumero();
    }

    public boolean esNumeroExtraido(int numero) {
        return bombo != null && bombo.contieneExtraido(numero);
    }

    public Participante getJugador() {
        return participantes.isEmpty() ? null : participantes.get(0);
    }

    public Participante getMaquina() {
        return participantes.size() > 1 ? participantes.get(1) : null;
    }

    public boolean isPartidaActiva() {
        return partidaActiva;
    }
}
