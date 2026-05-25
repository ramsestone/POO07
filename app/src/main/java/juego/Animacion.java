package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class Animacion extends ElementoGrafico {

    // Variables para el control de la línea de tiempo y los fotogramas
    private int fotogramaActual;
    private double tiempoAcumulado;
    private double velocidadAnimacion;
    private java.awt.List fotogramas;

    /**
     * Constructor para instanciar una nueva animación en el plano cartesiano.
     *
     * @param coordenadaX Posición en el eje X donde ocurrirá la animación.
     * @param coordenadaY Posición en el eje Y donde ocurrirá la animación.
     * @param fotogramas La lista de rutas de las imágenes (proveniente de
     *        Assets).
     * @param velocidadAnimacion Tiempo en segundos (deltaTime) que dura cada
     *        fotograma.
     */
    public Animacion(double coordenadaX, double coordenadaY,
            java.awt.List fotogramas, double velocidadAnimacion) {
        super(); // Invoca el constructor base de ElementoGrafico

        this.posX = coordenadaX;
        this.posY = coordenadaY;
        this.fotogramas = fotogramas;
        this.velocidadAnimacion = velocidadAnimacion;

        this.fotogramaActual = 0;
        this.tiempoAcumulado = 0.0;

        // Sobreescribimos el valor por defecto; la animación arranca visible
        this.esVisible = true;
    }

    @Override
    public void actualizar(double deltaTime) {
        // Sumamos el tiempo de procesamiento del último ciclo del motor
        this.tiempoAcumulado += deltaTime;

        // Si el tiempo acumulado supera nuestro umbral, avanzamos la animación
        if (this.tiempoAcumulado >= this.velocidadAnimacion) {
            this.tiempoAcumulado = 0.0; // Reiniciamos el reloj para el
                                        // siguiente fotograma
            this.fotogramaActual++;

            // Condición de limpieza: Si superamos el total de fotogramas, la
            // animación muere
            if (this.fotogramaActual >= this.fotogramas.getItemCount()) {
                this.esVisible = false;
            }
        }
    }

    @Override
    public void renderizar(Lienzo lienzo) {
        // Validación de seguridad para no dibujar elementos inactivos
        if (!this.esVisible) {
            return;
        }

        // Extraemos la ruta del fotograma correspondiente a la fracción de
        // tiempo actual
        String rutaFotograma = this.fotogramas.getItem(this.fotogramaActual);
        lienzo.dibujo(this.posX, this.posY, rutaFotograma);
    }

    @Override
    protected void setSprite(Imagen sprite) {
        // Se deja en blanco intencionalmente. Al ser un método abstracto en la
        // clase padre
        // es obligatorio declararlo, pero nuestra animación gestiona las
        // imágenes dinámicamente
        // mediante rutas String en el método renderizar, por lo que un Sprite
        // estático no es necesario.
    }
}
