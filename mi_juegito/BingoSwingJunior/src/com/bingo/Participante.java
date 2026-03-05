package com.bingo;

// Interfaz que deben implementar todos los participantes del bingo
// Tanto el jugador humano como la máquina deben implementarla
public interface Participante {

    // Marca un número en el cartón del participante
    void marcarNumero(int numero);

    // Devuelve el cartón del participante
    Carton getCarton();

    // Devuelve el nombre del participante
    String getNombre();
}
