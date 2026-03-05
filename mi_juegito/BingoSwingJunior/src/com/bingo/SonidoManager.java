package com.bingo;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SonidoManager {

    // Ruta base donde están los sonidos
    private static final String CARPETA_SONIDOS = "resources/sounds/";

    // Reproduce un archivo .wav dado su nombre
    public static void reproducir(String nombreArchivo) {
        try {
            // Buscamos el archivo en la carpeta de sonidos
            File archivo = new File(CARPETA_SONIDOS + nombreArchivo);

            if (!archivo.exists()) {
                // Si no existe el archivo, no pasa nada (el juego sigue)
                System.out.println("Aviso: no se encontró el sonido " + nombreArchivo);
                return;
            }

            // Cargamos el audio y lo reproducimos
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivo);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // reproducir sin bloquear el hilo

        } catch (Exception e) {
            // Si hay cualquier error con el sonido, no interrumpimos el juego
            System.out.println("Aviso: error al reproducir " + nombreArchivo + " - " + e.getMessage());
        }
    }

    // Reproduce el sonido al extraer un número
    public static void reproducirNumero() {
        reproducir("numero.wav");
    }

    // Reproduce el sonido al conseguir línea
    public static void reproducirLinea() {
        reproducir("linea.wav");
    }

    // Reproduce el sonido al conseguir bingo
    public static void reproducirBingo() {
        reproducir("bingo.wav");
    }
}
