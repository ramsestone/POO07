package juego;

import edu.epromero.util.Destruible;
import edu.epromero.util.Lienzo;
import edu.epromero.util.Imagen;

public class Heroe extends ElementoGrafico implements GeneradorDeProyectiles, Dispara, Destruible {

    private Entrada gameInput;

    public void setGameInput(Entrada gameInput) {
      this.gameInput = gameInput;
    }

    private SistemaDeArmamento sistArmamento;
    private boolean disparando;

    public boolean isDisparando() {
        return disparando;
    }

    // BPS = Balas Por Segundo
    private final double BPS = 3;

    public Heroe() {
        setSprite(Assets.HEROE);
        initHitBox();
        setGameInput(Juego.getGameInput());
        setVelocidadElemento(500);
        this.sistArmamento = new SistemaDeArmamento(BPS);
    }

    @Override
    protected void setSprite(Imagen sprite) {
        this.sprite = sprite;
        
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
            ProyectilAzul proyectil = new ProyectilAzul();
            proyectil.aparecer(posX, posY);
            return proyectil;
        }
        return null;
    }

    @Override
    public void actualizar(double deltaTime) {
        if (!esVisible)
            return;

        // Calcular la distancia para el frame actual
        double distanciaFrame = this.velocidadElemento * deltaTime;
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
        if (this.posX >= this.anchoPantalla) {
            this.posX = this.anchoPantalla;
        }
    }

    @Override
    public void renderizar(Lienzo lienzo) {
        super.renderizar(lienzo);
    }
}
