package com.bingo;

// Clase que representa a la máquina (jugador automático)
// Implementa la interfaz Participante igual que Jugador
public class Maquina implements Participante {

    // Nombre de la máquina
    String nombre;

    // Cartón de la máquina (se genera en el constructor)
    Carton carton;

    // Constructor: recibe el nombre y genera el cartón automáticamente
    public Maquina(String nombre) {
        this.nombre = nombre;
        this.carton = new Carton(); // Se crea un cartón nuevo con números aleatorios
    }

    // Marca el número en el cartón de la máquina
    @Override
    public void marcarNumero(int numero) {
        carton.marcarNumero(numero);
    }

    // Devuelve el cartón de la máquina
    @Override
    public Carton getCarton() {
        return carton;
    }

    // Devuelve el nombre de la máquina
    @Override
    public String getNombre() {
        return nombre;
    }
}
