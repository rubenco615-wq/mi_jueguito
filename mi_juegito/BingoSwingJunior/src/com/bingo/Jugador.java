package com.bingo;

public class Jugador implements Participante {
    private String nombre;
    private Carton carton;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.carton = new Carton();
    }

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
