package juego;

import edu.epromero.util.FabricaAudio;
import edu.epromero.util.Imagen;

public class ProyectilMorado extends Proyectil {

    public ProyectilMorado() {
        setSprite(new Imagen(Assets.PROYECTIL_MORADO));
        initHitBox();
    }

    @Override
    public void playSonido() {
        FabricaAudio sonido = new FabricaAudio();
        sonido.reproducir(Assets.PROY_MORADO_DISPARO);
    }

    @Override
    protected void setSprite(Imagen sprite) {
        super.sprite = sprite;
    }

    @Override
    public void actualizar(double deltaTime) {
        if (!esVisible) {
            return;
        }

        super.actualizar(deltaTime);
        this.posY -= this.velocidadProyectil * deltaTime;
    }

    public Imagen getSprite() {
        return this.sprite;
    }
}
