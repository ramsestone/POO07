package juego;

import java.util.ArrayList;
import java.util.Random;

public class GeneradorDeEnemigos {

    private double tiempoAcumulado;
    private double intervaloGeneracion; // Segundos entre cada enemigo
    private Random random;
    private ArrayList<NaveEnemiga> lsitaEnemigos;
    private ArrayList<ElementoGrafico> lsitaElementos;

    /**
     * @param intervalo El tiempo en segundos para generar un nuevo enemigo.
     * @param listaEnemigos La lista donde el generador inyectará las nuevas
     *        naves.
     */
    public GeneradorDeEnemigos(double intervalo,
            ArrayList<NaveEnemiga> listaEnemigos,
            ArrayList<ElementoGrafico> listaElementos) {
        this.tiempoAcumulado = 0.0;
        this.intervaloGeneracion = intervalo;
        this.random = new Random();
        this.lsitaEnemigos = listaEnemigos;
        this.lsitaElementos = listaElementos;
    }

    public void actualizar(double deltaTime) {
        tiempoAcumulado += deltaTime;

        if (tiempoAcumulado >= intervaloGeneracion) {
            generarEnemigo();
            tiempoAcumulado = 0.0; // Reiniciamos el reloj
        }
    }

    private void generarEnemigo() {
        NaveEnemiga nuevoEnemigo;
        int tipo = random.nextInt(4); // 0, 1, 2, 3

        // Variables locales para almacenar la posición calculada según el tipo
        double posX = 0.0;
        double posY = 0.0;

        int anchoPantalla = Juego.getAnchoPantalla();
        int altoPantalla = Juego.getAltoPantalla();

        // Regla Y: Rango matemático entre (altoPantalla / 2) y (altoPantalla -
        // 75)
        double limiteMinimoY = altoPantalla / 2.0;
        double limiteMaximoY = altoPantalla - 75.0;
        double alturaDeseada = limiteMinimoY
                + (random.nextDouble() * (limiteMaximoY - limiteMinimoY));

        switch (tipo) {
            case 0: // Ave de Presa
                nuevoEnemigo = new AveDePresa();

                // Regla X: Determinar aleatoriamente si aparece por la
                // izquierda o derecha
                if (random.nextBoolean()) {
                    posX = -50.0; // Fuera de la pantalla por la izquierda
                } else {
                    posX = anchoPantalla + 50.0; // Fuera de la pantalla por la
                                                 // derecha
                }

                posY = limiteMinimoY + (random.nextDouble()
                        * (limiteMaximoY - limiteMinimoY));
                break;

            case 1: // Destructor
                nuevoEnemigo = new Destructor();
                nuevoEnemigo.setAlturaDeseada(alturaDeseada);

                // Regla X: Dentro del rango visible de la pantalla
                posX = random.nextDouble() * anchoPantalla;

                // Regla Y: Fuera de la pantalla (solo arriba en el plano
                // cartesiano)
                posY = altoPantalla + 50.0;
                break;

            case 2: // Pulverizador
                nuevoEnemigo = new Pulverizador();

                // Regla X: Determinar aleatoriamente si aparece por la
                // izquierda o derecha
                if (random.nextBoolean()) {
                    posX = -50.0; // Fuera de la pantalla por la izquierda
                } else {
                    posX = anchoPantalla + 50.0; // Fuera de la pantalla por la
                                                 // derecha
                }

                posY = limiteMinimoY + (random.nextDouble()
                        * (limiteMaximoY - limiteMinimoY));
                break;

            default: // LanzaMinas
                nuevoEnemigo = new LanzaMinas();
                nuevoEnemigo.setAlturaDeseada(alturaDeseada);

                // Regla X e Y: Mismo comportamiento geométrico que el
                // Destructor
                posX = random.nextDouble() * anchoPantalla;
                posY = altoPantalla + 50.0;
                break;
        }

        // Inyección de las coordenadas calculadas en la instancia
        // correspondiente
        nuevoEnemigo.setPosInicialX(posX);
        nuevoEnemigo.setPosInicialY(posY);

        nuevoEnemigo.aparecer();
        lsitaEnemigos.add(nuevoEnemigo);
        lsitaElementos.add(nuevoEnemigo);
    }
}
