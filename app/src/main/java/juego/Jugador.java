package juego;

import edu.epromero.util.Destruible;
import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class Jugador extends ElementoGrafico implements GeneradorDeProyectiles, Dispara, Destruible {

    // Segundos que se tarda en ir de extremo a extremo de la pantalla
    // private final int SEGUNDOS_RECORRIDO = 5;

    // Definimos offsets para delimitar los bordes del sprite
    private double X_OFFSET;
    private double Y_OFFSET;
    private double velocidadJugador;

    private Entrada gameInput;

    private SistemaDeArmamento sistArmamento;
    private boolean disparando;

    public boolean isDisparando() {
        return disparando;
    }

    // BPS = Balas Por Segundo
    private final double BPS = 3;

    public Jugador(Imagen sprite, double anchoPantalla, double altoPantalla, Entrada gameInput) {
        super(sprite, anchoPantalla, altoPantalla);
        // this.velocidadJugador = (anchoPantalla / this.SEGUNDOS_RECORRIDO);
        this.velocidadJugador = 500;
        this.gameInput = gameInput;
        this.sistArmamento = new SistemaDeArmamento(BPS);
    }

    @Override
    public boolean recibirDanio() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getVidasActuales() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void aparecer(double posInicialX, double posInicialY) {
        super.aparecer(posInicialX, posInicialY);
    }

    @Override
    public Proyectil crearProyectil() {
        if (sistArmamento.puedeDisparar()) {
            sistArmamento.reiniciarEnfriamiento();
            ProyectilAzul proyectil = new ProyectilAzul(anchoPantalla, altoPantalla);
            proyectil.aparecer(posX, posY);
            return proyectil;
        }
        return null;
    }

    public void actualizar(double deltaTime) {
        if (!esVisible)
            return;

        // Calcular la distancia para el frame actual
        double distanciaFrame = this.velocidadJugador * deltaTime;
        sistArmamento.actualizar(deltaTime);

        // Gestionar movimiento
        if (gameInput.izquierdaPres()) {
            this.posX -= distanciaFrame;
        }

        if (gameInput.derechaPres()) {
            this.posX += distanciaFrame;
        }

        this.disparando = gameInput.disparoPres() ? true : false;

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
        return this.X_OFFSET;
    }

    public double getY_OFFSET() {
        return this.Y_OFFSET;
    }

    public double getVelocidadJugador() {
        return velocidadJugador;
    }

    public void setVelocidadJugador(double velocidadJugador) {
        this.velocidadJugador = velocidadJugador;
    }
}
