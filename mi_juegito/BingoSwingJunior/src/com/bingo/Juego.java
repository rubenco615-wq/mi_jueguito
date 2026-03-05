package com.bingo;

import java.util.ArrayList;

// Aquí se gestiona la partida, los jugadores, el bombo y el temporizador
public class Juego {

    ArrayList<Participante> participantes;
    Bombo bombo;
    Temporizador temporizador;
    boolean partidaActiva;
    int ultimoNumero;

    public Juego() {
        participantes = new ArrayList<Participante>();
        bombo = new Bombo();
        partidaActiva = false;
        ultimoNumero = 0;
    }

    // Inicia la partida: añade jugador y máquina, activa la partida
    public void iniciarPartida(String nombreJugador) {
        // Limpiamos la lista por si hubiera una partida anterior
        participantes.clear();

        // Creamos el bombo nuevo
        bombo = new Bombo();

        // Creamos el jugador con el nombre dado
        Jugador jugador = new Jugador(nombreJugador);
        participantes.add(jugador);

        // Creamos la máquina
        Maquina maquina = new Maquina("Máquina");
        participantes.add(maquina);

        // Marcamos la partida como activa
        partidaActiva = true;
        ultimoNumero = 0;
    }

    // Extrae un número del bombo.
    // La MÁQUINA marca automáticamente.
    // El JUGADOR tiene que marcar a mano haciendo clic en su cartón.
    // Devuelve el número extraído, o -1 si no quedan números
    public int extraerNumero() {
        if (!partidaActiva || !bombo.quedanNumeros()) {
            return -1;
        }

        // Sacamos el número del bombo
        int numero = bombo.extraerNumero();
        ultimoNumero = numero;

        return numero;
    }

    // Comprueba si un número ya ha sido extraído del bombo
    public boolean esNumeroExtraido(int numero) {
        return bombo.numerosExtraidos.contains(numero);
    }

    // Comprueba si algún participante ha ganado (línea o bingo)
    // Devuelve el participante ganador o null si nadie ha ganado
    public Participante comprobarVictoria() {
        for (int i = 0; i < participantes.size(); i++) {
            Participante p = participantes.get(i);
            if (p.getCarton().comprobarBingo()) {
                return p;
            }
        }
        return null;
    }

    // Comprueba si algún participante tiene línea
    // Devuelve el participante con línea o null
    public Participante comprobarLinea() {
        for (int i = 0; i < participantes.size(); i++) {
            Participante p = participantes.get(i);
            if (p.getCarton().comprobarLinea()) {
                return p;
            }
        }
        return null;
    }

    // Finaliza la partida
    public void finalizarPartida() {
        partidaActiva = false;
        if (temporizador != null) {
            temporizador.detener();
        }
    }

    // Devuelve el jugador humano
    public Participante getJugador() {
        if (participantes.size() > 0) {
            return participantes.get(0);
        }
        return null;
    }

    // Devuelve la máquina
    public Participante getMaquina() {
        if (participantes.size() > 1) {
            return participantes.get(1);
        }
        return null;
    }

    // Comprueba si la partida está activa
    public boolean isPartidaActiva() {
        return partidaActiva;
    }
}
