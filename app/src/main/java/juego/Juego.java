package juego;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;

import edu.epromero.util.Lienzo;

public class Juego {

    // Definición de los estados posibles del juego
    private enum Estado {
        MENU, JUGANDO, GAME_OVER
    }

    // Bandera para evitar el "Input Bleed" entre pantallas
    private boolean teclaLiberada = false;
    private GestorMusica musicaFondo;
    private Estado estadoActual = Estado.MENU; // Iniciamos en el menú

    private static Heroe heroe;
    NaveEnemiga enemigo;

    private static final int ANCHO_PANTALLA = 1000;
    private static final int ALTO_PANTALLA = 600;
    private Lienzo mainLienzo;

    private ArrayList<ElementoGrafico> elementosGraficos;
    private ArrayList<NaveEnemiga> enemigos;
    private static Entrada gameInput;
    private FondoDinamico fondoAnimado;

    private GeneradorDeEnemigos generador;


    public Juego() {
        // TODO Implementar registro de puntuación alta
        // TODO Implementar musica de game over
        // TODO Implementar efectos de sonido generales
        // TODO Implementar easter egg
        // TODO Sacar 10 en el evaluador
        this.mainLienzo = new Lienzo();
        this.mainLienzo.ponTamanioLienzo(ANCHO_PANTALLA, ALTO_PANTALLA);
        this.mainLienzo.ponEscalaX(0, ANCHO_PANTALLA);
        this.mainLienzo.ponEscalaY(0, ALTO_PANTALLA);
        this.fondoAnimado = new FondoDinamico();
        mainLienzo.ponColorLapiz(Color.YELLOW);
        mainLienzo.ponFuente(new Font("DialogInput", Font.BOLD, 20));

        this.musicaFondo = new GestorMusica(Assets.MUSICA);

        gameInput = new Entrada(mainLienzo);

        this.elementosGraficos = new ArrayList<>();
        this.enemigos = new ArrayList<>();

        heroe = new Heroe();
        heroe.setGameInput(gameInput);
        heroe.aparecer();
        elementosGraficos.add(heroe);

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

            // Definición de los estados posibles del juego
            switch (estadoActual) {
                case MENU:
                    musicaFondo.reproducirEnBucle();
                    actualizarMenu();
                    dibujarMenu();
                    break;

                case JUGANDO:
                    // Actualizar y dibujar el fondo PRIMERO (Capa más profunda)
                    fondoAnimado.actualizar(deltaTime);
                    fondoAnimado.renderizar(mainLienzo);
                    // =======================================================================================
                    // Procesar inputs del jugador
                    // =======================================================================================
                    if (heroe.isDisparando()) {
                        ArrayList<Proyectil> proyectiles = heroe.crearProyectiles();
                        if (proyectiles != null && !proyectiles.isEmpty()) {
                            for (Proyectil proyectil : proyectiles) {
                                if (proyectil != null) {
                                    proyectil.aparecer();
                                    elementosGraficos.add(proyectil);
                                }
                            }
                        }
                    }

                    if (heroe.getVidasActuales() <= 0) {
                        estadoActual = Estado.GAME_OVER;
                    }

                    // =======================================================================================
                    // Actualizacion de elementos graficos.
                    // =======================================================================================
                    generador.actualizar(deltaTime);
                    crearProyectilesEnemigos(deltaTime);
                    verificarColisiones(deltaTime);

                    actualizarElementosGraficos(deltaTime);
                    dibujarUi();
                    break;

                case GAME_OVER:
                    dibujarGameOver();
                    actualizarGameOver();
                    musicaFondo.detener();
                    break;
            }
            mainLienzo.mostrar(16);
        }
    }

    private void dibujarMenu() {
        // Dibujamos el fondo
        mainLienzo.dibujo(ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2, Assets.FONDO);

        // Título Principal
        mainLienzo.ponColorLapiz(Color.YELLOW);
        // Usamos una fuente más grande para el título
        mainLienzo.ponFuente(new Font("DialogInput", Font.BOLD, 60));
        mainLienzo.texto(ANCHO_PANTALLA / 2, ALTO_PANTALLA * 0.7, "INVASIÓN GALÁCTICA");

        // Instrucciones
        mainLienzo.ponColorLapiz(Color.WHITE);
        mainLienzo.ponFuente(new Font("DialogInput", Font.PLAIN, 20));
        mainLienzo.texto(ANCHO_PANTALLA / 2, ALTO_PANTALLA * 0.4,
                "PRESIONA [ESPACIO] PARA COMENZAR");
        mainLienzo.texto(ANCHO_PANTALLA / 2, ALTO_PANTALLA * 0.3,
                "USA LAS FLECHAS PARA MOVERTE Y ESPACIO PARA DISPARAR");
    }

    private void actualizarMenu() {
        // 1. Verificamos si el jugador ya levantó el dedo del teclado
        if (!gameInput.disparoPres()) {
            this.teclaLiberada = true;
        }

        // 2. Solo cambiamos de estado si presiona Y la tecla estaba libre
        if (gameInput.disparoPres() && this.teclaLiberada) {
            this.teclaLiberada = false; // Bloqueamos inmediatamente
            reiniciarJuego(); // Limpiamos la memoria
            estadoActual = Estado.JUGANDO; // Iniciamos la acción
        }
    }

    private void dibujarGameOver() {
        mainLienzo.dibujo(ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2, Assets.FONDO);
        mainLienzo.ponColorLapiz(Color.RED);
        mainLienzo.ponFuente(new Font("DialogInput", Font.BOLD, 80));
        mainLienzo.texto(ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2, "¡DERROTA!");

        mainLienzo.ponFuente(new Font("DialogInput", Font.PLAIN, 20));
        mainLienzo.ponColorLapiz(Color.WHITE);
        mainLienzo.texto(ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2 - 100,
                "PRESIONA [ESPACIO] PARA VOLVER AL MENÚ");
    }

    private void actualizarGameOver() {
        // 1. Verificamos si el jugador ya levantó el dedo del teclado
        if (!gameInput.disparoPres()) {
            this.teclaLiberada = true;
        }

        // 2. Solo cambiamos de estado si presiona Y la tecla estaba libre
        if (gameInput.disparoPres() && this.teclaLiberada) {
            this.teclaLiberada = false; // Bloqueamos inmediatamente
            estadoActual = Estado.MENU; // Volvemos a la pantalla de título
        }
    }

    /**
     * Restablece el estado inicial del juego, limpiando los elementos gráficos
     * activos y re-inicializando los personajes principales.
     */
    private void reiniciarJuego() {
        // 1. Limpiar por completo las listas para liberar memoria y evitar duplicados
        this.elementosGraficos.clear();
        this.enemigos.clear();

        // 2. Re-instanciar al héroe (esto reinicia automáticamente sus vidas a 3 y
        // puntos a 0)
        heroe = new Heroe();
        heroe.setGameInput(gameInput);
        heroe.aparecer();
        this.elementosGraficos.add(heroe);

        // Generar un enemigo nuevo cada 2.5 segundos
        this.generador = new GeneradorDeEnemigos(2.5, enemigos, elementosGraficos);
    }

    private void dibujarUi() {
        String puntosJugador = "Puntaje: " + String.valueOf(heroe.getPuntosGanados());
        String vidasJugador = "Vidas: " + String.valueOf(heroe.getVidas());

        mainLienzo.textoIzquierda(0, -20, puntosJugador);
        mainLienzo.texto(ANCHO_PANTALLA / 2, -20, vidasJugador);
    }

    /**
     * Actualiza todos los elementos gráficos.
     * 
     * @param deltaTime El tiempo transcurrido (para futuras implementaciones
     *        físicas si es necesario).
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
            ArrayList<Proyectil> proyectiles = nave.crearProyectiles();
            if (proyectiles != null && !proyectiles.isEmpty()) {
                for (Proyectil proyectil : proyectiles) {
                    if (proyectil != null) {
                        proyectil.aparecer();
                        elementosGraficos.add(proyectil);
                    }
                }
            }
        }
    }

    /**
     * Verifica las colisiones entre los proyectiles y las naves enemigas.
     * 
     * @param deltaTime El tiempo transcurrido (para futuras implementaciones
     *        físicas si es necesario).
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
                                heroe.setPuntosGanados(heroe.getPuntosGanados() + nave.getPuntos());
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
        // NUEVO BLOQUE: Verificamos si algún proyectil enemigo golpea al Héroe
        for (ElementoGrafico elemento : elementosGraficos) {

            // 1. Filtrado estricto: Solo evaluamos amenazas reales para el héroe
            if (elemento instanceof ProyectilRojo || elemento instanceof ProyectilNaranja
                    || elemento instanceof ProyectilVerde) {

                Proyectil proyectilEnemigo = (Proyectil) elemento;

                // 2. Solo calculamos matemáticas si ambos objetos siguen activos en pantalla
                if (proyectilEnemigo.isVisible() && heroe.isVisible()) {

                    // 3. Ejecutamos la prueba de geometría
                    if (heroe.hayColision(proyectilEnemigo)) {

                        // Aplicamos el daño y desaparecemos la bala
                        heroe.recibirDanio();
                        proyectilEnemigo.setIsVisible(false);

                        // Nota técnica: Aquí no hacemos un 'return' temprano porque
                        // el héroe podría recibir el impacto de dos balas en el mismo frame.
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
