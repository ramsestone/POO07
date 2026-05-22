package juego;

import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;

@ComportamientoEnemigo(tipo = "Ave de Presa", resistencia = 3, puntos = 10)
public class AveDePresa extends NaveEnemiga {

    public AveDePresa(Imagen sprite, double anchoPantalla, double altoPantalla) {
        super(sprite, anchoPantalla, altoPantalla);
        this.puntosDeVida = 3;
        this.valorEnPuntos = 10;
        this.tipoNave = "Ave de Presa";
        this.hitSprite = Assets.AVE_DE_PRESA_NEGATIVA;
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
    public Proyectil crearProyectil() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void aparecer(double posInicialX, double posInicialY) {
        super.aparecer(posInicialX, posInicialY);
    }

    @Override
    protected Imagen getDamageSprite() {
        return Assets.AVE_DE_PRESA_NEGATIVA;
    }
}
