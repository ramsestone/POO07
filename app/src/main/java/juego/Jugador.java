package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class Jugador extends ElementoGrafico implements Dispara {

    // Segundos que se tarda en ir de extremo a extremo de la pantalla
    private final int SEGUNDOS_RECORRIDO = 5;

    // Definimos offsets para delimitar los bordes del sprite
    private double X_OFFSET;
    private double Y_OFFSET;
    private double velocidadJugador;

    private Entrada gameInput;

    private SistemaDeArmamento canionLaser;

    // BPS = Balas Por Segundo
    private final double BPS = 3;

    public Jugador(Imagen sprite, double anchoPantalla, double altoPantalla, Entrada gameInput) {
        super(sprite, anchoPantalla, altoPantalla);
        this.velocidadJugador = (anchoPantalla / this.SEGUNDOS_RECORRIDO);
        this.X_OFFSET = this.getAnchoSprite() / 2;
        this.Y_OFFSET = this.getAltoSprite() / 2;
        this.gameInput = gameInput;
        this.canionLaser = new SistemaDeArmamento(BPS);
    }

    @Override
    public void aparecer(double posInicialX, double posInicialY) {
        super.aparecer(posInicialX, posInicialY);
    }

    @Override
    public ProyectilAzul disparar() {
        if (canionLaser.puedeDisparar()) {
            canionLaser.reiniciarEnfriamiento();
            ProyectilAzul laser = new ProyectilAzul(anchoPantalla, altoPantalla);
            laser.aparecer(posX, posY + Y_OFFSET);
            return laser;
        }
        return null;
    }

    public void actualizar(double deltaTime) {
        if (!esVisible)
            return;

        //DEBUG
        // System.out.println(posX);

        // Calcular la distancia para el frame actual
        double distanciaFrame = this.velocidadJugador * deltaTime;
        canionLaser.actualizar(deltaTime);

        // Gestionar movimiento
        if (gameInput.izquierdaPres()) {
            this.posX -= distanciaFrame;
        }

        if (gameInput.derechaPres()) {
            this.posX += distanciaFrame;
        }

        if (gameInput.disparoPres()) {
            disparar();
        }

        // Gestionar limites de la pantalla
        if (this.posX <= 0) {
            this.posX = 0;
        }
        if (this.posX >= anchoPantalla) {
            this.posX = anchoPantalla;
        }
    }

    @Override
    public void renderizar(Lienzo lienzo) {
        super.renderizar(lienzo);
    }

    public double getX_OFFSET() {
        return X_OFFSET;
    }

    public double getY_OFFSET() {
        return Y_OFFSET;
    }

    public double getVelocidadJugador() {
        return velocidadJugador;
    }

    public void setVelocidadJugador(double velocidadJugador) {
        this.velocidadJugador = velocidadJugador;
    }
}
