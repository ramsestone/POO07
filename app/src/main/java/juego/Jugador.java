package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class Jugador extends ElementoGrafico {
    private Imagen sprite;
    // Offset del sprite para delimitar sus bordes. El sprite debe ser de 72 x 72
    // Offset del sprite para delimitar sus bordes. El sprite debe ser de 72 x 72
    private final double X_OFFSET = 36;
    private final double Y_OFFSET = 72;

    // Offset para que el sprite concuerde visualmente con la parte inferior de la
    // pantalla. Se usa una constante porque el jugador nunca sube ni baja
    private final double POS_Y = 12;
    private double anchoPantalla;
    private double velocidadJugador;

    // Atributos de control
    private double tiempoEntreDisparos;
    private double temporizadorDisparo;

    public Jugador(double anchoPantalla) {
        super();
        final String SPRITE_PATH = "app/src/main/resources/jugador_00.png";
        this.sprite = new Imagen(SPRITE_PATH);
        this.anchoPantalla = anchoPantalla;
        this.velocidadJugador = anchoPantalla / 5;

        this.tiempoEntreDisparos = 0.5;
        // Lo iniciamos igual al tiempo límite para que pueda disparar inmediatamente al iniciar el juego
        this.temporizadorDisparo = this.tiempoEntreDisparos;
    }

    @Override
    public void aparecer() {
        this.esVisible = true;
        this.posX = anchoPantalla / 2;
        this.posY = POS_Y;
    }

    /**
     * Genera una nueva bala con posicion relativa a la de la nave rebelde
     * 
     * @return Un nuevo objeto instanciado de Proyectil.
     */
    public Proyectil dispara() {
        if (this.temporizadorDisparo >= this.tiempoEntreDisparos) {
            // Reiniciamos el reloj a cero
            this.temporizadorDisparo = 0.0;
            Proyectil bala = new Proyectil(this.posX, this.posY + Y_OFFSET);
            return bala;         
        }
        return null;
    }

    public void actualziarMovimiento(Entrada gameInput, double deltaTime) {
        if (!esVisible) {
            return;
        }

        this.temporizadorDisparo += deltaTime;

        // Calcular la distancia para el frame actual
        double distanciaFrame = this.velocidadJugador * deltaTime;

        // Gestionar movimiento
        if (gameInput.izquierdaPres()) {
            this.posX -= distanciaFrame;
        }

        if (gameInput.derechaPres()) {
            this.posX += distanciaFrame;
        }

        // Gestionar limites de la pantalla
        if (this.posX <= 0) {
            this.posX = 0;
        }
        if (this.posX >= anchoPantalla - X_OFFSET) {
            this.posX = anchoPantalla - X_OFFSET;
        }
    }

    public void renderizar(Lienzo lienzo) {
        if (esVisible) {
            lienzo.dibujo(posX, posY, sprite);
        }
    }
}
