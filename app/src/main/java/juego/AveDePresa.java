package juego;

import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;

@ComportamientoEnemigo(tipo = "Ave de Presa", resistencia = 3, puntos = 10)
public class AveDePresa extends NaveEnemiga implements Dispara {

    public AveDePresa(Imagen sprite, double anchoPantalla, double altoPantalla) {
        super(sprite, anchoPantalla, altoPantalla);
        this.puntosDeVida = 3;
        this.valorEnPuntos = 10;
        this.balasPorSegundo = 1d / 3;
        this.tipoNave = "Ave de Presa";
        this.hitSprite = Assets.AVE_DE_PRESA_NEGATIVA;
        this.setVelocidadNave(100);
        this.setSistArmamento(new SistemaDeArmamento(balasPorSegundo));
    }

    /**
     * Se activa únicamente en el fotograma exacto en el que ocurre la colisión.
     * 
     * @param dmgSprite El sprite alternativo a mostrar.
     */
    @Override
    protected void cambiarSpriteOnHit(double deltaTime, Imagen dmgSprite) {
        this.sprite = dmgSprite;
        this.hitSprite = dmgSprite;
        this.damageTimer = 0.0;
    }

    @Override
    public boolean recibirDanio() {
        return super.recibirDanio();
    }
    
    @Override
    public void aparecer(double posInicialX, double posInicialY) {
        super.aparecer(posInicialX, posInicialY);
    }
    
    @Override
    public ProyectilRojo crearProyectil() {
        if (sistArmamento.puedeDisparar()) {
            sistArmamento.reiniciarEnfriamiento();
            ProyectilRojo proyectil = new ProyectilRojo(anchoPantalla, altoPantalla);
            proyectil.aparecer(posX, posY);
            return proyectil;
        }
        return null;
    }

    protected void iaDeMovimiento(double deltaTime) {
        if (!isInBounds && this.posX >= 0 && this.posX <= anchoPantalla) {
            isInBounds = true;
        }
        // Estado A: La nave ya está en la zona de juego (Patrullaje)
        if (this.isInBounds) {

            this.posX += (this.velocidadNave * deltaTime) * this.factorMovimiento;

            // Verificamos si chocó con un borde para invertir la dirección
            if (this.posX >= this.anchoPantalla || this.posX <= 0) {
                this.factorMovimiento *= -1;
            }

        }
        // Estado B: La nave está fuera de la pantalla (Acercamiento)
        else {
            if (this.posX < 0) {
                // Si spawneó a la izquierda, la empujamos hacia la derecha
                this.posX += (this.velocidadNave * deltaTime) * Math.abs(this.factorMovimiento);
            } else {
                // Si spawneó a la derecha, la empujamos hacia la izquierda
                this.posX -= (this.velocidadNave * deltaTime) * Math.abs(this.factorMovimiento);
            }
        }
    }

    @Override
    public void actualizar(double deltaTime) {
        iaDeMovimiento(deltaTime);
        super.actualizar(deltaTime);
    }

    @Override
    protected Imagen getDamageSprite() {
        return Assets.AVE_DE_PRESA_NEGATIVA;
    }
}
