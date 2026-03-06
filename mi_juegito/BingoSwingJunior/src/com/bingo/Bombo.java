package com.bingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Representa el bombo físico del bingo.
 * Contiene bolas del 1 al 90 y se encarga de mezclarlas y entregarlas.
 */
public class Bombo {
    // Lista de números que todavía están dentro del bombo
    private ArrayList<Integer> numerosDisponibles;

    // Conjunto rápido (HashSet) para saber al instante si un número ya ha salido
    private HashSet<Integer> numerosExtraidos;

    public Bombo() {
        numerosDisponibles = new ArrayList<>();
        numerosExtraidos = new HashSet<>();

        // Llenamos el bombo con las bolas del 1 al 90
        for (int i = 1; i <= 90; i++) {
            numerosDisponibles.add(i);
        }
        // Mezclamos (barajamos) las bolas para que salgan al azar
        Collections.shuffle(numerosDisponibles);
    }

    /**
     * Saca la primera bola disponible de la lista y la guarda en los extraídos.
     * 
     * @return El número que ha salido, o -1 si el bombo está vacío.
     */
    public int sacarNumero() {
        if (numerosDisponibles.isEmpty())
            return -1;

        // Quitamos la primera bola
        int num = numerosDisponibles.remove(0);
        // La registramos como "ya ha salido"
        numerosExtraidos.add(num);
        return num;
    }

    /** Comprueba rápidamente si una bola ya ha salido antes */
    public boolean contieneExtraido(int numero) {
        return numerosExtraidos.contains(numero);
    }

    /** ¿Quedan bolas en el bombo? */
    public boolean isEmpty() {
        return numerosDisponibles.isEmpty();
    }
}
