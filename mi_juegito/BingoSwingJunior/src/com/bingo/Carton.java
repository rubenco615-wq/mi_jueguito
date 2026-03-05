package com.bingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// 3 filas x 9 columnas, con 15 números (5 por fila, 4 celdas vacías por fila)

public class Carton {

    // Matriz con los números del cartón (0 = celda vacía)
    int[][] numeros;

    // Matriz para saber qué números están marcados
    boolean[][] marcados;

    // Constructor: genera el cartón automáticamente
    public Carton() {
        numeros = new int[3][9];
        marcados = new boolean[3][9];
        generarCarton();
    }

    // Genera el cartón: 3 filas, 9 columnas, 15 números
    // Los números de cada columna están ordenados de menor a mayor
    public void generarCarton() {
        Random random = new Random();

        // Rangos de números para cada columna
        int[] minCol = { 1, 10, 20, 30, 40, 50, 60, 70, 80 };
        int[] maxCol = { 9, 19, 29, 39, 49, 59, 69, 79, 90 };

        // Restricciones: exactamente 5 números por fila, máximo 2 por columna
        boolean[][] tieneNumero;
        boolean generacionOk;

        do {
            tieneNumero = new boolean[3][9];
            int[] usosColumna = new int[9];
            generacionOk = true;

            for (int fila = 0; fila < 3; fila++) {
                // Columnas disponibles: las que todavía no se han usado 2 veces
                ArrayList<Integer> disponibles = new ArrayList<Integer>();
                for (int col = 0; col < 9; col++) {
                    if (usosColumna[col] < 2) {
                        disponibles.add(col);
                    }
                }

                // Si no hay suficientes columnas libres, reintentamos todo
                if (disponibles.size() < 5) {
                    generacionOk = false;
                    break;
                }

                // Barajamos y elegimos las primeras 5 columnas para esta fila
                Collections.shuffle(disponibles);
                for (int i = 0; i < 5; i++) {
                    int col = disponibles.get(i);
                    tieneNumero[fila][col] = true;
                    usosColumna[col]++;
                }
            }
        } while (!generacionOk);

        // generar los números para cada celda
        // Usamos una lista de listas para evitar números repetidos en la misma columna
        ArrayList<ArrayList<Integer>> usadosPorColumna = new ArrayList<ArrayList<Integer>>();
        for (int col = 0; col < 9; col++) {
            usadosPorColumna.add(new ArrayList<Integer>());
        }

        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tieneNumero[fila][col]) {
                    // un número no repetido dentro del rango de esa columna
                    int numero;
                    do {
                        numero = random.nextInt(maxCol[col] - minCol[col] + 1) + minCol[col];
                    } while (usadosPorColumna.get(col).contains(numero));

                    numeros[fila][col] = numero;
                    usadosPorColumna.get(col).add(numero);
                } else {
                    numeros[fila][col] = 0;
                }
                marcados[fila][col] = false;
            }
        }

        // ordenar cada columna de menor a mayor
        for (int col = 0; col < 9; col++) {

            ArrayList<Integer> valores = new ArrayList<Integer>();
            ArrayList<Integer> filasConNumero = new ArrayList<Integer>();

            for (int fila = 0; fila < 3; fila++) {
                if (numeros[fila][col] != 0) {
                    valores.add(numeros[fila][col]);
                    filasConNumero.add(fila);
                }
            }

            // Ordenamos los valores de menor a mayor
            Collections.sort(valores);

            // Los volvemos a colocar en sus filas, ya ordenados
            for (int i = 0; i < valores.size(); i++) {
                numeros[filasConNumero.get(i)][col] = valores.get(i);
            }
        }
    }

    // Comprueba si el cartón contiene un número determinado
    public boolean contieneNumero(int numero) {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 9; col++) {
                if (numeros[fila][col] == numero) {
                    return true;
                }
            }
        }
        return false;
    }

    // Marca el número en el cartón si lo contiene
    public void marcarNumero(int numero) {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 9; col++) {
                if (numeros[fila][col] == numero) {
                    marcados[fila][col] = true;
                }
            }
        }
    }

    // Comprueba si hay línea: una fila completa con todos sus números marcados
    // (las celdas vacías no cuentan)
    public boolean comprobarLinea() {
        for (int fila = 0; fila < 3; fila++) {
            boolean filaCompleta = true;
            boolean filaVacia = true;

            for (int col = 0; col < 9; col++) {
                if (numeros[fila][col] != 0) {
                    filaVacia = false;
                    if (!marcados[fila][col]) {
                        filaCompleta = false;
                        break;
                    }
                }
            }

            if (!filaVacia && filaCompleta) {
                return true;
            }
        }
        return false;
    }

    // Comprueba si hay bingo: todos los 15 números marcados
    public boolean comprobarBingo() {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 9; col++) {
                // Si hay un número (no 0) y no está marcado → no hay bingo
                if (numeros[fila][col] != 0 && !marcados[fila][col]) {
                    return false;
                }
            }
        }
        return true;
    }
}
