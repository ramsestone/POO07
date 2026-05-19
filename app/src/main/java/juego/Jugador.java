package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class Jugador extends ElementoGrafico {
    private Imagen sprite;
    // Offset del sprite para delimitar sus bordes. El sprite debe ser de 72 x 72
    private final double X_OFFSET = 36;

    // Offset para que el sprite concuerde visualmente con la parte inferior de la
    // pantalla
    private final double POS_INICIAL_Y = 12;
    private double anchoPantalla;
    private Lienzo lienzo;
    private double velocidadJugador;

    public Jugador(Lienzo lienzo) {
        super();
        this.lienzo = lienzo;
        final String SPRITE_PATH = "app/src/main/resources/jugador_00.png";
        this.sprite = new Imagen(SPRITE_PATH);
        this.anchoPantalla = this.lienzo.pideLimiteXMax();
        this.velocidadJugador = anchoPantalla / 5;
    }

    public void aparecer() {
        this.esVisible = true;
        this.posX = anchoPantalla / 2;
        this.posY = POS_INICIAL_Y;
    }

    public void actualizarMovimiento(Entrada gameInput, double deltatime) {
        if (!esVisible) {
            return;
        }

        // Calcular la distancia para el frame actual
        double distanciaFrame = this.velocidadJugador * deltatime;

        // Gestionar movimiento
        if (gameInput.izquierdaPres()) {
            this.posX -= distanciaFrame;
        }

        if (gameInput.derechaPres()) {
            this.posX += distanciaFrame;
        }

        if (gameInput.disparoPres()) {
            // disparar();
        }

        // Gestionar limites de la pantalla
        if (this.posX <= 0) {
            this.posX = 0;
        }
        if (this.posX >= anchoPantalla - X_OFFSET) {
            this.posX = anchoPantalla - X_OFFSET;
        }
    }

    public void renderizar() {
        if (esVisible) {
            this.lienzo.dibujo(posX, posY, sprite);
        }
    }
}
