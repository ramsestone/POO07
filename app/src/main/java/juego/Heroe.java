package juego;

import java.util.ArrayList;
import edu.epromero.util.Destruible;
import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class Heroe extends ElementoGrafico implements Dispara, Destruible {

    private Entrada gameInput;
    private Imagen defaultSprite = new Imagen(Assets.HEROE);
    private Imagen damageSprite = new Imagen(Assets.HEROE_NEGATIVO);
    protected Imagen hitSprite;

    private SistemaDeArmamento sistArmamento;
    private boolean disparando;
    private boolean isTakingDamage;
    private double damageTimer;
    private final double DAMAGE_DURATION = 0.1;

    // BPS = Balas Por Segundo
    private final double BPS = 3;

    private int puntosGanados;
    private int puntosDeVida;

    public Heroe() {
        setSprite(defaultSprite);
        initHitBox();
        setGameInput(Juego.getGameInput());
        setPosInicialX(this.anchoPantalla / 2);
        setPosInicialY(this.altoSprite / 2);
        inicia();

        setVelocidadElemento(500);
        this.sistArmamento = new SistemaDeArmamento(BPS);
    }

    public void inicia() {
        setIsVisible(true);
        setVidas(3);
    }

    @Override
    protected void setSprite(Imagen sprite) {
        this.sprite = sprite;

    }

    @Override
    public boolean recibirDanio() {
        aplicarEfectoDanio(this.getDamageSprite());
        this.puntosDeVida -= 1;
        if (puntosDeVida > 0) {
            return false;
        }
        setIsVisible(false);
        return true;
    }

    public void perderVida() {
        recibirDanio();
    }

    protected void aplicarEfectoDanio(Imagen dmgSprite) {
        if (!this.isTakingDamage) {
            this.defaultSprite = this.sprite;
        }

        this.sprite = dmgSprite;
        this.hitSprite = dmgSprite;

        this.isTakingDamage = true;
        this.damageTimer = 0.0;
    }

    @Override
    public int getVidasActuales() {
        return this.puntosDeVida;
    }

    @Override
    public ArrayList<Proyectil> crearProyectiles() {
        if (sistArmamento.puedeDisparar()) {
            sistArmamento.reiniciarEnfriamiento();
            ArrayList<Proyectil> proyectiles = new ArrayList<>();
            ProyectilAzul proyectil = new ProyectilAzul();
            proyectiles.add(proyectil);
            proyectil.setPosInicialX(posX);
            proyectil.setPosInicialY(posY);
            return proyectiles;
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

        cambiarSprite(deltaTime);
    }

    protected void cambiarSprite(double deltaTime) {
        // Si la nave está en estado de daño, comenzamos a sumar el tiempo
        if (this.isTakingDamage) {

            this.damageTimer += deltaTime;

            // Evaluamos si ya pasó el tiempo límite
            if (this.damageTimer >= this.DAMAGE_DURATION) {

                // El tiempo expiró: apagamos el estado y restauramos el sprite original
                this.isTakingDamage = false;
                this.sprite = this.defaultSprite;
                this.damageTimer = 0.0;
            }
        }
    }

    /**
     * Evalúa geométricamente si un proyectil enemigo intercepta al héroe. Utiliza
     * el algoritmo de Cajas Delimitadoras Alineadas a los Ejes (AABB). * @param
     * proyectil El proyectil enemigo a evaluar.
     * 
     * @return true si los hitboxes se superponen, false en caso contrario.
     */
    public boolean hayColision(Proyectil proyectil) {
        // Límites del héroe
        double miIzquierda = this.posX - this.xOffset;
        double miDerecha = this.posX + this.xOffset;
        double miArriba = this.posY + this.yOffset;
        double miAbajo = this.posY - this.yOffset;

        // Límites del proyectil enemigo
        double suIzquierda = proyectil.posX - proyectil.xOffset;
        double suDerecha = proyectil.posX + proyectil.xOffset;
        double suArriba = proyectil.posY + proyectil.yOffset;
        double suAbajo = proyectil.posY - proyectil.yOffset;

        // Evaluación lógica de separación
        boolean estaDemasiadoALaIzquierda = miDerecha < suIzquierda;
        boolean estaDemasiadoALaDerecha = miIzquierda > suDerecha;
        boolean estaDemasiadoAbajo = miArriba < suAbajo;
        boolean estaDemasiadoArriba = miAbajo > suArriba;

        // Si hay separación en cualquier eje, la colisión es matemáticamente imposible
        if (estaDemasiadoALaIzquierda || estaDemasiadoALaDerecha || estaDemasiadoAbajo
                || estaDemasiadoArriba) {
            return false;
        }

        return true;
    }

    @Override
    public void renderizar(Lienzo lienzo) {
        super.renderizar(lienzo);
    }

    public boolean isDisparando() {
        return disparando;
    }

    public void setGameInput(Entrada gameInput) {
        this.gameInput = gameInput;
    }

    public int getPuntosGanados() {
        return puntosGanados;
    }

    public void setPuntosGanados(int puntos) {
        this.puntosGanados = puntos;
    }

    public int getVidas() {
        return puntosDeVida;
    }

    public void setVidas(int vidas) {
        this.puntosDeVida = vidas;
    }

    public Imagen getDamageSprite() {
        return damageSprite;
    }

    public int getPuntosDeVida() {
        return puntosDeVida;
    }

    public void setPuntosDeVida(int puntosDeVida) {
        this.puntosDeVida = puntosDeVida;
    }

    public Entrada getGameInput() {
        return gameInput;
    }
}
