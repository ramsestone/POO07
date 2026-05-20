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
    // private ArrayList<NaveEnemiga> enemigos;
    private Entrada gameInput;
    private ArrayList<Proyectil> proyectilesEnPantalla;

    public Juego() {
        this.mainLienzo = new Lienzo();
        this.gameInput = new Entrada(mainLienzo);
        this.proyectilesEnPantalla = new ArrayList<>();

        this.mainLienzo.ponTamanioLienzo(ANCHO_PANTALLA, ALTO_PANTALLA);
        this.mainLienzo.ponEscalaX(0, ANCHO_PANTALLA);
        this.mainLienzo.ponEscalaY(0, ALTO_PANTALLA);

        jugador = new Jugador(NAVE_REBELDE_SPRITE, ANCHO_PANTALLA, ALTO_PANTALLA, gameInput);
        enemigo = new AveDePresa(AVE_DE_PRESA_SPRITE, ANCHO_PANTALLA, ALTO_PANTALLA);
        jugador.aparecer(ANCHO_PANTALLA / 2, 12);

        enemigo.aparecer(ANCHO_PANTALLA / 2, ALTO_PANTALLA - 72);
    }

    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.startGameLoop();
    }

    private void actualizarProyectiles(ArrayList<Proyectil> proyectiles, double deltaTime) {
        Iterator<Proyectil> iterador = proyectiles.iterator();
        while (iterador.hasNext()) {
            Proyectil proyectil = iterador.next();
            proyectil.actualizar(deltaTime);
            proyectil.renderizar(mainLienzo);
            if (!proyectil.esVisible) {
                iterador.remove();
            }

            //DEBUG
            System.out.println("" + proyectiles.size());

        }
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

            // =======================================================================================
            // Procesar Inputs
            // =======================================================================================
            if (gameInput.disparoPres()) {
                Proyectil laser = jugador.disparar();
                if (laser != null) {
                    proyectilesEnPantalla.add(laser);                
                }
            }

            jugador.actualizar(deltaTime);

            // =======================================================================================
            // Actualizacion de los proyectiles
            // =======================================================================================
            actualizarProyectiles(proyectilesEnPantalla, deltaTime);

            // =======================================================================================
            // Actualizacion del jugador
            // =======================================================================================
            jugador.renderizar(mainLienzo);
            enemigo.renderizar(mainLienzo);

            mainLienzo.mostrar(16);
        }
    }

}
