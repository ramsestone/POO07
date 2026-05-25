package juego;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GestorMusica {

    private Clip clipFondo;
    // Bandera de control por software para evitar la latencia del hardware
    private boolean sonando;

    public GestorMusica(String rutaAudio) {
        this.sonando = false; // Inicialmente el gestor está apagado
        try {
            InputStream flujoAudio = getClass().getResourceAsStream(rutaAudio);

            if (flujoAudio != null) {
                InputStream flujoBuffer = new BufferedInputStream(flujoAudio);
                AudioInputStream flujoAudioDecodificado =
                        AudioSystem.getAudioInputStream(flujoBuffer);

                this.clipFondo = AudioSystem.getClip();
                this.clipFondo.open(flujoAudioDecodificado);
            } else {
                System.err.println(
                        "No se pudo localizar la pista musical: " + rutaAudio);
            }

        } catch (Exception e) {
            System.err.println("Error al decodificar la música de fondo.");
            e.printStackTrace();
        }
    }

    /**
     * Inicia la reproducción en bucle infinito de forma segura.
     */
    public void reproducirEnBucle() {
        // Evaluamos nuestra propia bandera de software
        if (this.clipFondo != null && !this.sonando) {
            this.sonando = true; // Bloqueamos inmediatamente el if para los
                                 // siguientes fotogramas

            this.clipFondo.setFramePosition(0);
            this.clipFondo.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Detiene la música por completo y restablece el estado de control.
     */
    public void detener() {
        if (this.clipFondo != null) {
            this.clipFondo.stop();
            this.sonando = false; // Liberamos la bandera
        }
    }
}