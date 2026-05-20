package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public abstract class Proyectil extends ElementoGrafico {

    protected Imagen sprite;
    protected final double VELOCIDAD_PROYECTIL = 200;
    protected boolean isInBounds;

    /**
     * Crea un nuevo sistema de armamento.
     * @param sprite objeto Imagen con el sprite cargado.
     * @param anchoPantalla el ancho de la pantalla donde se renderiza
     * @param altoPantalla el alto de la pantalla donde se renderiza
     */
    public Proyectil(Imagen sprite, double anchoPantalla, double altoPantalla) {
        super(sprite, anchoPantalla, altoPantalla);
        this.sprite = new Imagen(sprite);

        this.isInBounds = (posX >= 0 && posX < anchoPantalla) && (posY >= 0 && posY <= altoPantalla);
    }

    public abstract void actualizar(double deltaTime);

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
        if (!isInBounds) {
            this.esVisible = false;
        }
    }

}
