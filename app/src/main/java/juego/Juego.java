package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.LienzoStd;

public class Juego {

    private static Jugador jugador;
    private static final Imagen FONDO = new Imagen("app/src/main/resources/background_blue.png");
    private static final int ANCHO_PANTALLA = 800;
    private static final int ALTO_PANTALLA = 600;

    public static void main(String[] args) {
        initJuego();
        startGameLoop();
    }

    private static void renderizarFrame() {
        // El fondo se tiene que renderizar de esta forma puesto que se usó una escala para el lienzo
        LienzoStd.dibujo(ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2, FONDO);
        jugador.renderizar();
    }
    
    private static void initJuego() {
        
        jugador = new Jugador(ANCHO_PANTALLA);
        jugador.aparecer();
        
        LienzoStd.ponTamanioLienzo(ANCHO_PANTALLA, ALTO_PANTALLA);
        LienzoStd.ponEscalaX(0, ANCHO_PANTALLA);
        LienzoStd.ponEscalaY(0, ALTO_PANTALLA);
    }

    private static void startGameLoop() {
        boolean isRunning = true;
        // Implementación de deltatime para manejo correcto de frames
        long lastTime = System.currentTimeMillis();
        Entrada gameInput = new Entrada();

        while (isRunning) {
            long currentTime = System.currentTimeMillis();
            double deltaTime = (currentTime - lastTime) / 1000.0;
            lastTime = currentTime;

            // Prepara pantalla para siguiente frame
            LienzoStd.limpia();

            // Procesar Inputs
            jugador.actualizarMovimiento(gameInput, deltaTime);

            // Actualizar logica de juego
            // Renderizar Gráficos
            renderizarFrame();

            LienzoStd.mostrar(16);
        }
    }

}
