package juego;

import java.util.ArrayList;
import java.util.Iterator;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class Juego {

    private static Jugador jugador;
    AveDePresa enemigo;

    // Definimos los sprites que vamos a usar
    private static final Imagen FONDO = new Imagen("app/src/main/resources/background_blue.png");
    private static final Imagen NAVE_REBELDE_SPRITE = new Imagen("app/src/main/resources/nave_rebelde.png");
    private static final Imagen AVE_DE_PRESA_SPRITE = new Imagen("app/src/main/resources/ave_de_presa.png");

    private static final int ANCHO_PANTALLA = 1000;
    private static final int ALTO_PANTALLA = 600;
    private Lienzo mainLienzo;
    private ArrayList<ElementoGrafico> elementosGraficos;
    private Entrada gameInput;

    public Juego() {
        this.mainLienzo = new Lienzo();
        this.mainLienzo.ponTamanioLienzo(ANCHO_PANTALLA, ALTO_PANTALLA);
        this.mainLienzo.ponEscalaX(0, ANCHO_PANTALLA);
        this.mainLienzo.ponEscalaY(0, ALTO_PANTALLA);
        this.gameInput = new Entrada(mainLienzo);

        this.elementosGraficos = new ArrayList<>();

        jugador = new Jugador(NAVE_REBELDE_SPRITE, ANCHO_PANTALLA, ALTO_PANTALLA, gameInput);
        enemigo = new AveDePresa(AVE_DE_PRESA_SPRITE, ANCHO_PANTALLA, ALTO_PANTALLA);
        jugador.aparecer(ANCHO_PANTALLA / 2, 12);
        elementosGraficos.add(jugador);

        enemigo.aparecer(ANCHO_PANTALLA / 2, ALTO_PANTALLA - 72);
        elementosGraficos.add(enemigo);
    }

    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.startGameLoop();
    }

    private void startGameLoop() {
        boolean isRunning = true;
        // Implementación de deltatime para manejo correcto de frames
        long lastTime = System.currentTimeMillis();

        while (isRunning) {
            long currentTime = System.currentTimeMillis();
            double deltaTime = (currentTime - lastTime) / 1000.0;
            lastTime = currentTime;

            // Prepara pantalla para siguiente frame
            mainLienzo.limpia();
            // TODO: Hacer que el fondo herede de ElementoGrafico
            mainLienzo.dibujo(ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2, FONDO);

            // =======================================================================================
            // Procesar inputs del jugador
            // =======================================================================================
            if (jugador.isDisparando()) {
                Proyectil proyectil = jugador.crearProyectil();
                if (proyectil != null) {
                    elementosGraficos.add(proyectil);
                }
            }

            // =======================================================================================
            // Actualizacion de elementos graficos.
            // =======================================================================================
            actualizarElementosGráficos(deltaTime);

            mainLienzo.mostrar(16);
        }
    }

    private void actualizarElementosGráficos(double deltaTime) {
        Iterator<ElementoGrafico> iterator = elementosGraficos.iterator();
        while (iterator.hasNext()) {
            ElementoGrafico elemento = iterator.next();
            elemento.mover(deltaTime);
            if (!elemento.isVisible()) {
                iterator.remove();
            }
            else {
                elemento.renderizar(mainLienzo);
            }
        }
    }
}
