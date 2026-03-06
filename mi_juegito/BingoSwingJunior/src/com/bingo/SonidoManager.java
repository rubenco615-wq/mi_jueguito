package com.bingo;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * El encargado (Manager) de cargar ruidos (.wav) desde la carpeta resources
 * y hacerlos sonar. Maneja la música de fondo (BGM) y los efectos de sonido
 * (SFX).
 */
public class SonidoManager {
    private static final String DIR = "resources/sounds/";
    private static Clip musicaFondo;

    public static void reproducirBGM(String file) {
        detenerBGM(); // Para la anterior si la hay
        try {
            File f = new File(DIR + file);
            if (!f.exists())
                return;
            musicaFondo = AudioSystem.getClip();
            musicaFondo.open(AudioSystem.getAudioInputStream(f));
            musicaFondo.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ignored) {
        }
    }

    public static void detenerBGM() {
        if (musicaFondo != null && musicaFondo.isRunning()) {
            musicaFondo.stop();
            musicaFondo.close();
        }
    }

    private static void reproducirSFX(String file) {
        try {
            File f = new File(DIR + file);
            if (!f.exists())
                return;
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(f));
            clip.start();
        } catch (Exception ignored) {
        }
    }

    public static void reproducirLinea() {
        reproducirSFX("linea.wav");
    }

    public static void reproducirBingo() {
        detenerBGM();
        reproducirSFX("bingo.wav");
    }
}
