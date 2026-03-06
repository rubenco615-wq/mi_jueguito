package com.bingo;

/**
 * Molde (Interfaz) que deben seguir todos los que jueguen al bingo.
 * Garantiza que tanto tú (humano) como la máquina tengáis los mismos métodos
 * básicos.
 */
public interface Participante {

    /**
     * Tacha el número en el cartón si lo tienes.
     * 
     * @param numero El número que se ha cantado y debe ser marcado.
     */
    void marcarNumero(int numero);

    /** Devuelve el cartón que estás usando */
    Carton getCarton();

    /** Devuelve tu nombre o "Máquina" */
    String getNombre();
}
