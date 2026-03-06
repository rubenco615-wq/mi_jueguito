package com.bingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Bombo {
    private ArrayList<Integer> numerosDisponibles;
    private HashSet<Integer> numerosExtraidos;

    public Bombo() {
        numerosDisponibles = new ArrayList<>();
        numerosExtraidos = new HashSet<>();
        for (int i = 1; i <= 90; i++) {
            numerosDisponibles.add(i);
        }
        Collections.shuffle(numerosDisponibles);
    }

    public int sacarNumero() {
        if (numerosDisponibles.isEmpty())
            return -1;
        int num = numerosDisponibles.remove(0);
        numerosExtraidos.add(num);
        return num;
    }

    public boolean contieneExtraido(int numero) {
        return numerosExtraidos.contains(numero);
    }

    public boolean isEmpty() {
        return numerosDisponibles.isEmpty();
    }
}
