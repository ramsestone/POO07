package juego;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GestorMusica {

    private Clip clipFondo;

    public GestorMusica(String rutaAudio) {
        try {
            // 1. Obtenemos el flujo de datos desde el empaquetado (Assets)
            InputStream flujoAudio = getClass().getResourceAsStream(rutaAudio);

            if (flujoAudio != null) {
                // 2. Preparamos el buffer y el decodificador de Java
                InputStream flujoBuffer = new BufferedInputStream(flujoAudio);
                AudioInputStream flujoAudioDecodificado =
                        AudioSystem.getAudioInputStream(flujoBuffer);

                // 3. Solicitamos el motor de audio al sistema operativo
                this.clipFondo = AudioSystem.getClip();
                this.clipFondo.open(flujoAudioDecodificado);
            } else {
                System.err.println("No se pudo localizar la pista musical: " + rutaAudio);
            }

        } catch (Exception e) {
            System.err.println("Error al decodificar la música de fondo.");
            e.printStackTrace();
        }
    }

    /**
     * Inicia la reproducción en bucle infinito.
     */
    public void reproducirEnBucle() {
        if (this.clipFondo != null && !this.clipFondo.isRunning()) {
            // Reiniciamos el cabezal de lectura al inicio (fotograma 0)
            this.clipFondo.setFramePosition(0);

            // Invocamos la constante nativa para el loop eterno
            this.clipFondo.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Detiene la música (ideal para cuando cambias al estado GAME_OVER).
     */
    public void detener() {
        if (this.clipFondo != null && this.clipFondo.isRunning()) {
            this.clipFondo.stop();
        }
    }
}
