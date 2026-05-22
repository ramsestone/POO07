package juego;

import edu.epromero.util.Destruible;
import edu.epromero.util.Imagen;

public abstract class NaveEnemiga extends ElementoGrafico implements Destruible, Dispara {
    protected int puntosDeVida;
    protected int valorEnPuntos;
    protected String tipoNave;
    
    protected Imagen defaultSprite;
    protected Imagen hitSprite;
    protected boolean isTakingDamage;
    protected double damageTimer;
    protected final double DAMAGE_DURATION = 0.1;

    public NaveEnemiga(Imagen sprite, double anchoPantalla, double altoPantalla) {
        super(sprite, anchoPantalla, altoPantalla);
    }

    protected abstract void cambiarSpriteOnHit(double deltaTime, Imagen dmgSprite);
    
    protected abstract Imagen getDamageSprite();

    public boolean recibirDanio() {
        applyDamageEffect(this.getDamageSprite());
        this.puntosDeVida -= 1;
        if (puntosDeVida > 0) {
            return false;
        }
        return true;
    }

    public int getVidasActuales() {
        return this.puntosDeVida;
    }

    public int getPuntos() {
        return this.valorEnPuntos;
    }

    /**
     * Activates the damage state only on the exact frame the collision occurs.
     * * @param dmgSprite The alternative sprite to display.
     */
    protected void applyDamageEffect(Imagen dmgSprite) {
        if (!this.isTakingDamage) {
            this.defaultSprite = this.sprite;
        }

        this.sprite = dmgSprite;
        this.hitSprite = dmgSprite;

        this.isTakingDamage = true;
        this.damageTimer = 0.0;
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

    // Las naves solo deberían detectar la colisión de los proyectiles del heroe
    // (azules)
    public boolean hayColision(ProyectilAzul proyectil) {
        // Limites de este elemento
        double miIzquierda = this.posX - this.X_OFFSET;
        double miDerecha = this.posX + this.X_OFFSET;
        double miArriba = this.posY + this.Y_OFFSET;
        double miAbajo = this.posY - this.Y_OFFSET;

        // Limites del proyectil
        double suIzquierda = proyectil.posX - proyectil.X_OFFSET;
        double suDerecha = proyectil.posX + proyectil.X_OFFSET;
        double suArriba = proyectil.posY + proyectil.Y_OFFSET;
        double suAbajo = proyectil.posY - proyectil.Y_OFFSET;

        // 3. Aplicamos la lógica inversa: ¿Están separados?
        boolean estaDemasiadoALaIzquierda = miDerecha < suIzquierda;
        boolean estaDemasiadoALaDerecha = miIzquierda > suDerecha;
        boolean estaDemasiadoAbajo = miArriba < suAbajo;
        boolean estaDemasiadoArriba = miAbajo > suArriba;

        // Si alguna de las condiciones es verdadera, NO hay colisión.
        if (estaDemasiadoALaIzquierda || estaDemasiadoALaDerecha || estaDemasiadoAbajo || estaDemasiadoArriba) {
            return false;
        }

        // Si sobrevivió a todas las pruebas de separación, obligatoriamente se están
        // tocando
        return true;
    }

    @Override
    public void actualizar(double deltaTime) {
        cambiarSprite(deltaTime);
    }

}
