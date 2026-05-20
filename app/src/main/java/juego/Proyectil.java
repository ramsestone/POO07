package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public abstract class Proyectil extends ElementoGrafico {

    protected Imagen sprite;
    protected final double VELOCIDAD_PROYECTIL = 200;

    public Proyectil(Imagen sprite) {
        super(sprite);
        this.sprite = new Imagen(sprite);
    }

    public abstract void actualizar(double deltaTime);
    // {
    // if (!esVisible) {
    // return;
    // }

    // this.posY += VELOCIDAD_PROYECTIL * deltaTime;
    // }

    @Override
    public void aparecer(double posInicialX, double posInicialY) {
        super.aparecer(posInicialX, posInicialY);
    }

    // Si el elemento es visible
    @Override
    public void renderizar(Lienzo lienzo) {
        if (esVisible) {
            super.renderizar(lienzo);
            lienzo.dibujo(this.posX, this.posY, this.sprite);
        }
    }

}
