package juego;

import edu.epromero.util.Imagen;

public class ProyectilRojo extends Proyectil {

    public ProyectilRojo() {
        setSprite(Assets.PROYECTIL_ROJO);
        initHitBox();
    }

    @Override
    protected void setSprite(Imagen sprite) {
        super.sprite = sprite;
    }

    @Override
    public void actualizar(double deltaTime) {
        if (!esVisible)
            return;
        super.actualizar(deltaTime);
        this.posY -= this.VELOCIDAD_PROYECTIL * deltaTime;
    }
}
