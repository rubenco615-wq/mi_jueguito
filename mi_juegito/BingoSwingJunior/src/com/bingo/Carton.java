package com.bingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Carton {

    private int[][] numeros;
    private boolean[][] marcados;

    public Carton() {
        numeros = new int[3][9];
        marcados = new boolean[3][9];
        generarCarton();
    }

    public int getNumero(int fila, int col) {
        return numeros[fila][col];
    }

    public boolean isMarcado(int fila, int col) {
        return marcados[fila][col];
    }

    private void generarCarton() {
        Random random = new Random();
        int[] minCol = { 1, 10, 20, 30, 40, 50, 60, 70, 80 };
        int[] maxCol = { 9, 19, 29, 39, 49, 59, 69, 79, 90 };
        boolean[][] tieneNumero;
        boolean generacionOk;
        do {
            tieneNumero = new boolean[3][9];
            int[] usosColumna = new int[9];
            generacionOk = true;
            for (int fila = 0; fila < 3; fila++) {
                List<Integer> disponibles = new ArrayList<>();
                for (int col = 0; col < 9; col++) {
                    if (usosColumna[col] < 2)
                        disponibles.add(col);
                }
                if (disponibles.size() < 5) {
                    generacionOk = false;
                    break;
                }
                Collections.shuffle(disponibles);
                for (int i = 0; i < 5; i++) {
                    int c = disponibles.get(i);
                    tieneNumero[fila][c] = true;
                    usosColumna[c]++;
                }
            }
        } while (!generacionOk);

        List<List<Integer>> usadosPorColumna = new ArrayList<>();
        for (int col = 0; col < 9; col++)
            usadosPorColumna.add(new ArrayList<>());

        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tieneNumero[fila][col]) {
                    int numero;
                    do {
                        numero = random.nextInt(maxCol[col] - minCol[col] + 1) + minCol[col];
                    } while (usadosPorColumna.get(col).contains(numero));
                    numeros[fila][col] = numero;
                    usadosPorColumna.get(col).add(numero);
                }
            }
        }

        for (int col = 0; col < 9; col++) {
            List<Integer> valores = new ArrayList<>();
            List<Integer> filas = new ArrayList<>();
            for (int fila = 0; fila < 3; fila++) {
                if (numeros[fila][col] != 0) {
                    valores.add(numeros[fila][col]);
                    filas.add(fila);
                }
            }
            Collections.sort(valores);
            for (int i = 0; i < valores.size(); i++)
                numeros[filas.get(i)][col] = valores.get(i);
        }
    }

    public boolean contieneNumero(int numero) {
        for (int[] filaObj : numeros) {
            for (int n : filaObj) {
                if (n == numero)
                    return true;
            }
        }
        return false;
    }

    public void marcarNumero(int numero) {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 9; col++) {
                if (numeros[fila][col] == numero)
                    marcados[fila][col] = true;
            }
        }
    }

    public boolean comprobarLinea() {
        for (int fila = 0; fila < 3; fila++) {
            boolean completa = true, vacia = true;
            for (int col = 0; col < 9; col++) {
                if (numeros[fila][col] != 0) {
                    vacia = false;
                    if (!marcados[fila][col]) {
                        completa = false;
                        break;
                    }
                }
            }
            if (!vacia && completa)
                return true;
        }
        return false;
    }

    public boolean comprobarBingo() {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 9; col++) {
                if (numeros[fila][col] != 0 && !marcados[fila][col])
                    return false;
            }
        }
        return true;
    }
}
