package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public abstract class Proyectil extends ElementoGrafico {

    protected Imagen sprite;
    protected final double VELOCIDAD_PROYECTIL = 750;
    protected final double outboundsOffset = 50;

    /**
     * Crea un nuevo sistema de armamento.
     * 
     * @param sprite        objeto Imagen con el sprite cargado.
     * @param anchoPantalla el ancho de la pantalla donde se renderiza
     * @param altoPantalla  el alto de la pantalla donde se renderiza
     */
    public Proyectil(Imagen sprite, double anchoPantalla, double altoPantalla) {
        super(sprite, anchoPantalla, altoPantalla);
        this.sprite = new Imagen(sprite);
    }

    public void actualizar(double deltaTime) {
        if (!esVisible)
            return;

        // Revisa si está dentro de la ventana cada frame
        this.isInBounds = (posX >= -outboundsOffset && posX < anchoPantalla + outboundsOffset)
                && (posY >= -outboundsOffset && posY <= altoPantalla + outboundsOffset);
        if (!isInBounds) {
            esVisible = false;
        }
    }

    @Override
    public void aparecer(double posInicialX, double posInicialY) {
        super.aparecer(posInicialX, posInicialY);
    }

    // Si el elemento es visible
    @Override
    public void renderizar(Lienzo lienzo) {
        super.renderizar(lienzo);
        if (!this.isVisible()) {
            return;
        }
    }

}
