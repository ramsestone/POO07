package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.epromero.util.FabricaAudio;
import edu.epromero.util.Lienzo;

public class Juego {

    // Definición de los estados posibles del juego
    private enum Estado {
        MENU, JUGANDO, GAME_OVER, EASTER_EGG
    }

    // Bandera para evitar el "Input Bleed" entre pantallas
    private boolean teclaLiberada = false;
    private GestorMusica musicaFondo;
    private GestorMusica musicaGameover;
    private Estado estadoActual = Estado.MENU; // Iniciamos en el menú

    private static Heroe heroe;
    private String nombreJugador = "";
    NaveEnemiga enemigo;
    private GestorPuntaje gestorPuntaje;

    private static final int ANCHO_PANTALLA = 1000;
    private static final int ALTO_PANTALLA = 600;
    private Lienzo mainLienzo;

    private ArrayList<ElementoGrafico> elementosGraficos;
    private ArrayList<NaveEnemiga> enemigos;
    private static Entrada gameInput;
    private double tiempoEnGameover;
    private double tiempoEnJuego;
    private FondoDinamico fondoAnimado;

    private GeneradorDeEnemigos generadorEnemigos;
    String cadenaEasterEgg;


    public Juego() {
        // TODO Implementar easter egg
        this.mainLienzo = new Lienzo();
        this.mainLienzo.ponTamanioLienzo(ANCHO_PANTALLA, ALTO_PANTALLA);
        this.mainLienzo.ponEscalaX(0, ANCHO_PANTALLA);
        this.mainLienzo.ponEscalaY(0, ALTO_PANTALLA);
        this.fondoAnimado = new FondoDinamico();
        mainLienzo.ponColorLapiz(Color.YELLOW);
        mainLienzo.ponFuente(new Font("DialogInput", Font.BOLD, 20));

        this.gestorPuntaje = new GestorPuntaje();

        this.musicaFondo = new GestorMusica(Assets.MUSICA);
        this.musicaGameover = new GestorMusica(Assets.GAME_OVER_SOUND);
        this.tiempoEnGameover = 0;
        this.tiempoEnJuego = 0;

        gameInput = new Entrada(mainLienzo);

        this.elementosGraficos = new ArrayList<>();
        this.enemigos = new ArrayList<>();

        this.cadenaEasterEgg = "";
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
                    musicaGameover.detener();
                    actualizarMenu();
                    dibujarMenu(deltaTime);
                    break;

                case JUGANDO:
                    // Actualizar y dibujar el fondo PRIMERO (Capa más profunda)
                    fondoAnimado.actualizar(deltaTime);
                    fondoAnimado.renderizar(mainLienzo);
                    // ========================================================
                    // Procesar inputs del jugador
                    // ========================================================
                    if (heroe.isDisparando() && tiempoEnJuego > 0.5) {
                        ArrayList<Proyectil> proyectiles =
                                heroe.crearProyectiles();
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
                        this.tiempoEnGameover = 0.0;
                        // Limpiamos el buffer de teclas
                        while (mainLienzo.existenMasTeclasPulsadas()) {
                            mainLienzo.siguienteTeclaPulsada();
                        }
                    }

                    // ========================================================
                    // Actualizacion de elementos graficos.
                    // ========================================================
                    this.tiempoEnJuego += deltaTime;
                    generadorEnemigos.actualizar(deltaTime);
                    crearProyectilesEnemigos(deltaTime);
                    verificarColisiones(deltaTime);

                    actualizarElementosGraficos(deltaTime);
                    dibujarUi();
                    break;

                case GAME_OVER:
                    dibujarGameOver();
                    actualizarGameOver(deltaTime);
                    musicaFondo.detener();
                    musicaGameover.reproducirEnBucle();
                    break;

                case EASTER_EGG:
                    dibujarEasterEgg(deltaTime);
                    actualziarEasterEgg();
                    break;


                default:
                    break;
            }
            mainLienzo.mostrar(16);
        }
    }

    private void dibujarMenu(double deltatime) {
        // Dibujamos el fondo
        fondoAnimado.actualizar(deltatime);
        fondoAnimado.renderizar(mainLienzo);

        // Título Principal
        mainLienzo.ponColorLapiz(Color.YELLOW);
        // Usamos una fuente más grande para el título
        mainLienzo.ponFuente(new Font("DialogInput", Font.BOLD, 60));
        mainLienzo.texto(ANCHO_PANTALLA / 2, ALTO_PANTALLA * 0.7,
                "INVASIÓN GALÁCTICA");

        // Instrucciones
        mainLienzo.ponColorLapiz(Color.WHITE);
        mainLienzo.ponFuente(new Font("DialogInput", Font.PLAIN, 20));
        mainLienzo.ponColorLapiz(Color.GREEN);
        mainLienzo.texto(ANCHO_PANTALLA / 2, ALTO_PANTALLA * 0.4,
                "PRESIONA [ESPACIO] PARA COMENZAR");
        mainLienzo.ponColorLapiz(Color.WHITE);
        mainLienzo.texto(ANCHO_PANTALLA / 2, ALTO_PANTALLA * 0.3,
                "USA LAS FLECHAS o [A] [D] PARA MOVERTE Y ESPACIO PARA DISPARAR");
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
        registraEasterEgg();
        if (cadenaEasterEgg.compareTo("wwssadadba") == 0) {
            estadoActual = Estado.EASTER_EGG;
        }
        System.out.println(cadenaEasterEgg);
    }

    private void dibujarGameOver() {
        String puntosJugador =
                "Puntaje: " + String.valueOf(heroe.getPuntosGanados());
        double yGameOverLabel = ALTO_PANTALLA / 2 + 200;
        double xGameOverLabel = ALTO_PANTALLA / 2 + 200;
        double yResetGameLabel = 0;


        mainLienzo.dibujo(ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2, Assets.FONDO);
        mainLienzo.ponColorLapiz(Color.RED);
        mainLienzo.ponFuente(new Font("DialogInput", Font.BOLD, 80));
        mainLienzo.texto(xGameOverLabel, yGameOverLabel, "¡GAME OVER!");

        mainLienzo.ponFuente(new Font("DialogInput", Font.BOLD, 20));
        mainLienzo.ponColorLapiz(Color.WHITE);

        mainLienzo.ponColorLapiz(Color.GREEN);
        mainLienzo.texto(xGameOverLabel, yResetGameLabel + 50 / 2,
                "Piloto: " + nombreJugador);

        mainLienzo.texto(xGameOverLabel, yGameOverLabel - 50, puntosJugador);

        mainLienzo.texto(xGameOverLabel, yResetGameLabel,
                "PRESIONA [ESPACIO] PARA VOLVER AL MENÚ");
        dibujaTopPuntajes(xGameOverLabel, yGameOverLabel - 100);
    }

    private void registraNombre() {
        while (mainLienzo.existenMasTeclasPulsadas()) {
            // Extraemos el carácter exacto que el usuario tecleó
            char tecla = mainLienzo.siguienteTeclaPulsada();

            // Evaluamos qué hacer con la tecla
            if (tecla == '\n' || tecla == '\r') {
                // Presionó ENTER: Finaliza la captura y cambia de estado
                gestorPuntaje.agregarPuntaje(nombreJugador,
                        heroe.getPuntosGanados());
                nombreJugador = "";
                estadoActual = Estado.MENU;

            } else if (tecla == '\b') {
                // Presionó BACKSPACE (Borrar): Quitamos el último carácter de
                // la cadena
                if (nombreJugador.length() > 0) {
                    nombreJugador = nombreJugador.substring(0,
                            nombreJugador.length() - 1);
                }

            } else {
                // Es un carácter normal: Lo concatenamos a nuestro String (con
                // un límite opcional)
                if (nombreJugador.length() < 10) {
                    nombreJugador += tecla;
                }
            }
        }
    }

    private void dibujarEasterEgg(double deltaTime) {
        fondoAnimado.actualizar(deltaTime);
        fondoAnimado.renderizar(mainLienzo);

        mainLienzo.ponColorLapiz(Color.green);
        mainLienzo.ponFuente(new Font("DialogInput", Font.BOLD, 60));
        mainLienzo.texto(ALTO_PANTALLA / 2 + 200, ALTO_PANTALLA / 2 + 300,
                "Invasión Galáctica");

        mainLienzo.ponFuente(new Font("DialogInput", Font.BOLD, 30));
        mainLienzo.texto(ALTO_PANTALLA / 2 + 200, ALTO_PANTALLA / 2 + 200,
                "¡Gracias por jugar!");
        mainLienzo.dibujo(ANCHO_PANTALLA / 2, ALTO_PANTALLA / 3,
                Assets.EASTER_EGG);
    }

    private void actualziarEasterEgg() {
        if (!gameInput.disparoPres()) {
            this.teclaLiberada = true;
        }

        // 2. Solo cambiamos de estado si presiona Y la tecla estaba libre
        if (gameInput.disparoPres() && this.teclaLiberada) {
            this.teclaLiberada = false; // Bloqueamos inmediatamente
            estadoActual = Estado.MENU; // Volvemos a la pantalla de título
        }
    }

    private void registraEasterEgg() {
        while (mainLienzo.existenMasTeclasPulsadas()) {
            // Extraemos el carácter exacto que el usuario tecleó
            char tecla = mainLienzo.siguienteTeclaPulsada();

            if (cadenaEasterEgg.length() < 10) {
                cadenaEasterEgg += tecla;
            } else {
                FabricaAudio audio = new FabricaAudio();
                if (tecla != ' ') {
                    audio.reproducir(Assets.WRONG_SOUND);
                }
                cadenaEasterEgg = "";
            }
        }
    }

    private void dibujaTopPuntajes(double posX, double posY) {
        mainLienzo.ponColorLapiz(Color.WHITE);
        mainLienzo.texto(posX, posY, "--- TOP MEJORES PILOTOS ---");

        // Iteramos la lista y bajamos en el eje Y por cada registro
        int offsetY = 35;
        List<GestorPuntaje.Puntuacion> top = gestorPuntaje.getTopPuntajes();

        for (int i = 0; i < top.size(); i++) {
            GestorPuntaje.Puntuacion p = top.get(i);
            String textoFila = (i + 1) + ".- " + p.nombre + " : " + p.puntos;

            mainLienzo.textoIzquierda(posX - (posX / 3), posY - offsetY,
                    textoFila);
            offsetY += 25; // Distancia en píxeles entre cada línea de texto
        }
    }

    private void actualizarGameOver(double deltaTime) {
        // 1. Verificamos si el jugador ya levantó el dedo del teclado
        this.tiempoEnGameover += deltaTime;
        if (!gameInput.disparoPres()) {
            this.teclaLiberada = true;
        }

        // 2. Solo cambiamos de estado si presiona Y la tecla estaba libre
        if (gameInput.disparoPres() && this.teclaLiberada) {
            this.teclaLiberada = false; // Bloqueamos inmediatamente
            estadoActual = Estado.MENU; // Volvemos a la pantalla de título
        }
        if (this.tiempoEnGameover > 0.5) {
            registraNombre();
        }
    }

    /**
     * Restablece el estado inicial del juego, limpiando los elementos gráficos
     * activos y re-inicializando los personajes principales.
     */
    private void reiniciarJuego() {
        this.tiempoEnJuego = 0;
        // 1. Limpiar por completo las listas para liberar memoria y evitar
        // duplicados
        this.elementosGraficos.clear();
        this.enemigos.clear();

        // 2. Re-instanciar al héroe (esto reinicia automáticamente sus vidas a
        // 3 y
        // puntos a 0)
        heroe = new Heroe();
        heroe.setGameInput(gameInput);
        heroe.aparecer();
        this.elementosGraficos.add(heroe);

        // Generar un enemigo nuevo cada 1.5 segundos
        this.generadorEnemigos =
                new GeneradorDeEnemigos(2, enemigos, elementosGraficos);
    }

    private void dibujarUi() {
        String puntosJugador =
                "Puntaje: " + String.valueOf(heroe.getPuntosGanados());
        String vidasJugador = "Vidas: " + String.valueOf(heroe.getVidas());
        mainLienzo.ponColorLapiz(Color.YELLOW);
        mainLienzo.ponFuente(new Font("DialogInput", Font.PLAIN, 20));
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
                proyectiles.get(0).playSonido();
                for (Proyectil proyectil : proyectiles) {
                    if (proyectil != null) {
                        proyectil.aparecer();
                        elementosGraficos.add(proyectil);
                    }
                }
            }
        }
    }

    private ElementoGrafico explotaNave(NaveEnemiga nave) {
        Animacion explosion = new Animacion(nave.getColumna(),
                nave.getRenglon(), Assets.FRAMES_EXPLOSION, 0.05);

        return explosion;
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
                                heroe.setPuntosGanados(heroe.getPuntosGanados()
                                        + nave.getPuntos());
                                elementosGraficos.add(explotaNave(nave));
                                nave.setIsVisible(false);
                            }

                            // MEJORA 1: La bala también debe desaparecer al
                            // impactar
                            proyectil.setIsVisible(false);
                            return;
                        }
                    }
                }
            }
        }
        // Verificamos si algún proyectil enemigo golpea al Héroe
        for (ElementoGrafico elemento : elementosGraficos) {

            // 1. Filtrado estricto: Solo evaluamos amenazas reales para el
            // héroe
            if (elemento instanceof ProyectilRojo
                    || elemento instanceof ProyectilNaranja
                    || elemento instanceof ProyectilVerde
                    || elemento instanceof ProyectilMorado) {

                Proyectil proyectilEnemigo = (Proyectil) elemento;

                // 2. Solo calculamos matemáticas si ambos objetos siguen
                // activos en pantalla
                if (proyectilEnemigo.isVisible() && heroe.isVisible()) {

                    // 3. Ejecutamos la prueba de geometría
                    if (heroe.hayColision(proyectilEnemigo)) {

                        // Aplicamos el daño y desaparecemos la bala
                        heroe.recibirDanio();
                        proyectilEnemigo.setIsVisible(false);

                        // Nota técnica: Aquí no hacemos un 'return' temprano
                        // porque
                        // el héroe podría recibir el impacto de dos balas en el
                        // mismo frame.
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

    public Lienzo getLienzo() {
        return this.mainLienzo;
    }
}
