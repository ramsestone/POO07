package juego;

import edu.epromero.util.Lienzo;

public abstract class Proyectil extends ElementoGrafico {

    protected final double VELOCIDAD_PROYECTIL = 750;
    protected final double outboundsOffset = 50;

    public void actualizar(double deltaTime) {
        if (!esVisible)
            return;

        // Revisa si está dentro de la ventana cada frame
        this.isInBounds = (posX >= -outboundsOffset && posX < Juego.getAnchoPantalla() + outboundsOffset)
                && (posY >= -outboundsOffset && posY <= Juego.getAltoPantalla() + outboundsOffset);
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
