package juego;

import java.util.ArrayList;
import java.util.Iterator;

import edu.epromero.util.Lienzo;

public class Juego {

    private static Heroe heroe;
    NaveEnemiga enemigo;

    // Definimos los sprites que vamos a usar
    // private static final Imagen FONDO = new
    // Imagen("../resources/background_blue.png");
    // private static final Imagen NAVE_REBELDE_SPRITE = new
    // Imagen("../resources/nave_rebelde.png");
    // private static final Imagen AVE_DE_PRESA_SPRITE = new
    // Imagen("../resources/ave_de_presa.png");

    private static final int ANCHO_PANTALLA = 1000;

    private static final int ALTO_PANTALLA = 600;
    private Lienzo mainLienzo;
    private ArrayList<ElementoGrafico> elementosGraficos;
    private ArrayList<NaveEnemiga> enemigos;
    private static Entrada gameInput;

    public Juego() {
        this.mainLienzo = new Lienzo();
        this.mainLienzo.ponTamanioLienzo(ANCHO_PANTALLA, ALTO_PANTALLA);
        this.mainLienzo.ponEscalaX(0, ANCHO_PANTALLA);
        this.mainLienzo.ponEscalaY(0, ALTO_PANTALLA);
        gameInput = new Entrada(mainLienzo);

        this.elementosGraficos = new ArrayList<>();
        this.enemigos = new ArrayList<>();

        heroe = new Heroe();
        heroe.setGameInput(gameInput);
        heroe.aparecer();
        elementosGraficos.add(heroe);
        
        enemigo = new Destructor();
        enemigo.aparecer();
        enemigos.add(enemigo);
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
            mainLienzo.dibujo(ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2, Assets.FONDO);

            // =======================================================================================
            // Procesar inputs del jugador
            // =======================================================================================
            if (heroe.isDisparando()) {
                Proyectil proyectil = heroe.crearProyectil();
                if (proyectil != null) {
                    proyectil.aparecer();
                    elementosGraficos.add(proyectil);
                }
            }

            // =======================================================================================
            // Actualizacion de elementos graficos.
            // =======================================================================================
            crearProyectilesEnemigos(deltaTime);
            verificarColisiones(deltaTime);

            actualizarElementosGraficos(deltaTime);
            mainLienzo.mostrar(16);
        }
    }

    /**
     * Actualiza todos los elementos gráficos.
     * 
     * @param deltaTime El tiempo transcurrido (para futuras implementaciones físicas si es
     *        necesario).
     */
    private void actualizarElementosGraficos(double deltaTime) {
        Iterator<ElementoGrafico> iterator = elementosGraficos.iterator();
        while (iterator.hasNext()) {
            ElementoGrafico elemento = iterator.next();
            elemento.actualizar(deltaTime);
            if (!elemento.isVisible()) {
                iterator.remove();
            } else {
                elemento.renderizar(mainLienzo);
            }
        }
    }

    /**
     * Gestiona la creacion de proyectiles de todas las naves enemigas
     */
    private void crearProyectilesEnemigos(double deltaTime) {
        Iterator<NaveEnemiga> iterator = enemigos.iterator();
        while (iterator.hasNext()) {
            NaveEnemiga nave = iterator.next();
            Proyectil proyectil = nave.crearProyectil();
            if (proyectil != null) {
                proyectil.aparecer();
                elementosGraficos.add(proyectil);
            }
        }
    }

    /**
     * Verifica las colisiones entre los proyectiles y las naves enemigas.
     * 
     * @param deltaTime El tiempo transcurrido (para futuras implementaciones físicas si es
     *        necesario).
     */
    private void verificarColisiones(double deltaTime) {

        for (ElementoGrafico elementoGrafico1 : elementosGraficos) {

            // 1. Encontramos un proyectil en la lista
            if (elementoGrafico1 instanceof ProyectilAzul) {
                ProyectilAzul proyectil = (ProyectilAzul) elementoGrafico1;

                for (ElementoGrafico elementoGrafico2 : elementosGraficos) {

                    // 2. CORRECCIÓN: Evaluamos el elemento 2, no el 1
                    if (elementoGrafico2 instanceof NaveEnemiga) {
                        NaveEnemiga nave = (NaveEnemiga) elementoGrafico2;

                        // 3. Comprobamos la superposición (AABB)
                        if (nave.hayColision(proyectil)) {

                            // ¡Impacto confirmado!
                            if (nave.recibirDanio()) {
                                nave.setIsVisible(false);
                            }

                            // MEJORA 1: La bala también debe desaparecer al impactar
                            proyectil.setIsVisible(false);
                            return;
                        }
                    }
                }
            }
        }
    }

    public static int getAnchoPantalla() {
      return ANCHO_PANTALLA;
    }

    public static int getAltoPantalla() {
      return ALTO_PANTALLA;
    }

    public static Entrada getGameInput() {
        return gameInput;
    }
}
