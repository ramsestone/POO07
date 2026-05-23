package juego;

import edu.epromero.util.Imagen;

public class Destructor extends NaveEnemiga {

    public Destructor() {
        // TODO setSprite();
        initHitBox();
    }

    @Override
    protected void setSprite(Imagen sprite) {
        this.sprite = sprite;
    }

    @Override
    protected Imagen getDamageSprite() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected ProyectilRojo crearProyectil() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void cambiarSpriteOnHit(double deltaTime, Imagen dmgSprite) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void iaDeMovimiento(double deltaTime) {
        // TODO Auto-generated method stub

    }

}
