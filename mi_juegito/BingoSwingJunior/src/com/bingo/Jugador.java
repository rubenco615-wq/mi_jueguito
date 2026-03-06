package com.bingo;

/**
 * Representa a un jugador de la partida (ya sea humano o la máquina).
 * Implementa la interfaz Participante para poder agruparlos fácilmente.
 */
public class Jugador implements Participante {
    private String nombre; // Ej: "Juan" o "Máquina"
    private Carton carton; // El cartón de bingo que le toca

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.carton = new Carton(); // Al crear al jugador, se le da un cartón nuevo
    }

    /** Busca el número en su cartón y lo marca si lo tiene */
    @Override
    public void marcarNumero(int numero) {
        carton.marcarNumero(numero);
    }

    @Override
    public Carton getCarton() {
        return carton;
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}
