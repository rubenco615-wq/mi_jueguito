package com.bingo;

public class Jugador implements Participante {

    String nombre;
    Carton carton;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.carton = new Carton(); // Se crea un cartón nuevo con números aleatorios
    }

    // Marca el número en el cartón del jugador
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
