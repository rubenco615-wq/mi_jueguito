package com.bingo;

import java.util.ArrayList;
import java.util.Collections;

// bombo del bingo
// Contiene los números del 1 al 90 y va sacándolos uno a uno
public class Bombo {
    // Lista con los números que todavía no han salido
    ArrayList<Integer> numerosDisponibles;

    // Lista con los números que ya han sido extraídos
    ArrayList<Integer> numerosExtraidos;

    // Constructor: llena el bombo con los números del 1 al 75
    public Bombo() {
        numerosDisponibles = new ArrayList<Integer>();
        numerosExtraidos = new ArrayList<Integer>();

        // Añadimos todos los números del 1 al 90 (bingo español)
        for (int i = 1; i <= 90; i++) {
            numerosDisponibles.add(i);
        }

        // Los mezclamos aleatoriamente
        Collections.shuffle(numerosDisponibles);
    }

    // Extrae un número del bombo (sin repetir)
    // Devuelve -1 si no quedan números
    public int extraerNumero() {
        if (!quedanNumeros()) {
            return -1; // No quedan números disponibles
        }

        // Sacamos el primer número de la lista y lo movemos a extraídos
        int numero = numerosDisponibles.remove(0);
        numerosExtraidos.add(numero);
        return numero;
    }

    // Comprueba si todavía quedan números por salir
    public boolean quedanNumeros() {
        return numerosDisponibles.size() > 0;
    }
}
