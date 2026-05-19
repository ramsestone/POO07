package juego;

import java.util.ArrayList;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class Juego {

    private static Jugador jugador;
    private static final Imagen FONDO = new Imagen("app/src/main/resources/background_blue.png");
    private static final int ANCHO_PANTALLA = 800;
    private static final int ALTO_PANTALLA = 600;
    private Lienzo mainLienzo;
    private ArrayList<Proyectil> balasActivas;

    public Juego() {
        this.mainLienzo = new Lienzo();
        this.mainLienzo.ponTamanioLienzo(ANCHO_PANTALLA, ALTO_PANTALLA);
        this.mainLienzo.ponEscalaX(0, ANCHO_PANTALLA);
        this.mainLienzo.ponEscalaY(0, ALTO_PANTALLA);
        this.balasActivas = new ArrayList<>();

        jugador = new Jugador(ANCHO_PANTALLA);
        jugador.aparecer();
    }

    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.startGameLoop();
    }

    private void startGameLoop() {
        boolean isRunning = true;
        // Implementación de deltatime para manejo correcto de frames
        long lastTime = System.currentTimeMillis();
        Entrada gameInput = new Entrada(mainLienzo);

        while (isRunning) {
            long currentTime = System.currentTimeMillis();
            double deltaTime = (currentTime - lastTime) / 1000.0;
            lastTime = currentTime;

            // Prepara pantalla para siguiente frame
            mainLienzo.limpia();
            mainLienzo.dibujo(ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2, FONDO);

            // Procesar Inputs
            if (gameInput.disparoPres()) {
                Proyectil nuevaBala = jugador.dispara();
                if (nuevaBala != null) {
                    nuevaBala.aparecer();                    
                    balasActivas.add(nuevaBala);
                }
            }

            for (Proyectil bala : balasActivas) {
                bala.actualizarMovimiento(deltaTime);
            }
            for (Proyectil bala : balasActivas) {
                bala.renderizar(mainLienzo);
            }
            jugador.actualziarMovimiento(gameInput, deltaTime);


            // Actualizar logica de juego
            // Renderizar Gráficos
            jugador.renderizar(mainLienzo);

            mainLienzo.mostrar(16);
        }
    }

}
