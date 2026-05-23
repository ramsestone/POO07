package juego;

import edu.epromero.util.Imagen;

public class ProyectilVerde extends Proyectil{
    public ProyectilVerde() {
        setSprite(new Imagen(Assets.PROYECTIL_VERDE));
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
