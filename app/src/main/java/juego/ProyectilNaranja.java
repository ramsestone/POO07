package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class ProyectilNaranja extends Proyectil {
    private int direccion;
    private int offset;

    public ProyectilNaranja() {
        setSprite(new Imagen(Assets.PROYECTIL_ROJO));
        this.offset = 45;
        initHitBox();
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
        this.posX += (this.velocidadProyectil * deltaTime) * direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public int getDireccion() {
        return direccion;
    }

    @Override
    public void renderizar(Lienzo lienzo) {
        if (!esVisible) {
            return;
        }

        if (direccion == 1) {
            lienzo.dibujo(posX, posY, sprite, offset);
        } else {
            lienzo.dibujo(posX, posY, sprite, -offset);
        }
    }
}
