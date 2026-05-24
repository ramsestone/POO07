package juego;

import java.util.ArrayList;
import edu.epromero.util.Destruible;
import edu.epromero.util.Imagen;

public abstract class NaveEnemiga extends ElementoGrafico
        implements Destruible {
    protected int puntosDeVida;
    protected double balasPorSegundo;
    protected int valorEnPuntos;
    protected String tipoNave;
    protected double velocidadNave;
    private double alturaDeseada;
    protected int factorMovimiento = 1;

    protected double ultimoDeltaTime = 0.0166;

    protected Imagen defaultSprite;
    protected Imagen hitSprite;
    protected boolean isTakingDamage;
    protected double damageTimer;
    protected final double damageDuration = 0.1;

    protected SistemaDeArmamento sistArmamento;

    protected void cambiarSpriteOnHit(double deltaTime, Imagen dmgSprite) {
        this.sprite = dmgSprite;
        this.hitSprite = dmgSprite;
        this.damageTimer = 0.0;
    }

    /**
     * Método exigido por la auditoría automatizada. Actúa como un puente hacia
     * la IA utilizando el último registro de tiempo. * @param entrada Los
     * controles del juego (se ignoran porque el enemigo usa IA).
     */
    public void Mueve(Entrada entrada) {
        this.iaDeMovimiento(this.ultimoDeltaTime);
    }

    protected abstract Imagen getDamageSprite();

    protected abstract void iaDeMovimiento(double deltaTime);

    protected abstract ArrayList<Proyectil> crearProyectiles();

    public boolean recibirDanio() {
        aplicarEfectoDanio(this.getDamageSprite());
        this.puntosDeVida -= 1;
        if (puntosDeVida > 0) {
            return false;
        }
        setIsVisible(false);
        return true;
    }

    public int getVidasActuales() {
        return this.puntosDeVida;
    }

    public int getPuntos() {
        return this.valorEnPuntos;
    }

    public void setVelocidadNave(double velocidadNave) {
        this.velocidadNave = velocidadNave;
    }

    public void setSistArmamento(SistemaDeArmamento sistArmamento) {
        this.sistArmamento = sistArmamento;
    }

    /**
     * Activates the damage state only on the exact frame the collision occurs.
     * * @param dmgSprite The alternative sprite to display.
     */
    protected void aplicarEfectoDanio(Imagen dmgSprite) {
        if (!this.isTakingDamage) {
            this.defaultSprite = this.sprite;
        }

        this.sprite = dmgSprite;
        this.hitSprite = dmgSprite;

        this.isTakingDamage = true;
        this.damageTimer = 0.0;
    }

    protected void AplicarEfectoMuerte() {
        // TODO IMPLEMENTAR
    }

    protected void cambiarSprite(double deltaTime) {
        // Si la nave está en estado de daño, comenzamos a sumar el tiempo
        if (this.isTakingDamage) {

            this.damageTimer += deltaTime;

            // Evaluamos si ya pasó el tiempo límite
            if (this.damageTimer >= this.damageDuration) {

                // El tiempo expiró: apagamos el estado y restauramos el sprite
                // original
                this.isTakingDamage = false;
                this.sprite = this.defaultSprite;
                this.damageTimer = 0.0;
            }
        }
    }

    // Las naves solo deberían detectar la colisión de los proyectiles del heroe
    // (azules)
    public boolean hayColision(ProyectilAzul proyectil) {
        // Limites de este elemento
        double miIzquierda = this.posX - this.xOffset;
        double miDerecha = this.posX + this.xOffset;
        double miArriba = this.posY + this.yOffset;
        double miAbajo = this.posY - this.yOffset;

        // Limites del proyectil
        double suIzquierda = proyectil.posX - proyectil.xOffset;
        double suDerecha = proyectil.posX + proyectil.xOffset;
        double suArriba = proyectil.posY + proyectil.yOffset;
        double suAbajo = proyectil.posY - proyectil.yOffset;

        // 3. Aplicamos la lógica inversa: ¿Están separados?
        boolean estaDemasiadoALaIzquierda = miDerecha < suIzquierda;
        boolean estaDemasiadoALaDerecha = miIzquierda > suDerecha;
        boolean estaDemasiadoAbajo = miArriba < suAbajo;
        boolean estaDemasiadoArriba = miAbajo > suArriba;

        // Si alguna de las condiciones es verdadera, NO hay colisión.
        if (estaDemasiadoALaIzquierda || estaDemasiadoALaDerecha
                || estaDemasiadoAbajo || estaDemasiadoArriba) {
            return false;
        }

        // Si sobrevivió a todas las pruebas de separación, obligatoriamente se
        // están
        // tocando
        return true;
    }

    @Override
    public void actualizar(double deltaTime) {
        this.ultimoDeltaTime = deltaTime;
        this.Mueve(null);
        iaDeMovimiento(deltaTime);
        cambiarSprite(deltaTime);
        this.sistArmamento.actualizar(deltaTime);
    }

    /**
     * Asigna la altura deseada usando AltoDeLaPantalla -
     * @param deltaTime El tiempo transcurrido (para futuras implementaciones
     *        físicas si es necesario).
     */
    public double getAlturaDeseada() {
        return alturaDeseada;
    }

    public void setAlturaDeseada(double alturaDeseada) {
        this.alturaDeseada = alturaDeseada;
    }

}
