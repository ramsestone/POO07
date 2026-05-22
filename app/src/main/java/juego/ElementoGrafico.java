package juego;

import java.awt.Color;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public abstract class ElementoGrafico {
    protected double posX;
    protected double posY;
    protected boolean isInBounds;

    // Offset es el valor que hay desde el centro hasta uno de sus bordes.
    protected double X_OFFSET;
    protected double Y_OFFSET;
    protected boolean esVisible;

    public void setEsVisible(boolean esVisible) {
        this.esVisible = esVisible;
    }

    protected Imagen sprite;
    // Dimensiones de la pantalla donde vive el ElemntoGrafico
    protected double anchoPantalla;
    protected double altoPantalla;
    // Dimensiones del sprite
    protected double anchoSprite;
    protected double altoSprite;
    // Lienzo
    protected Lienzo lienzo;

    public ElementoGrafico(Imagen sprite, double anchoPantalla, double altoPantalla) {
        this.esVisible = false;
        this.sprite = sprite;
        this.anchoSprite = this.sprite.ancho();
        this.altoSprite = this.sprite.alto();
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.X_OFFSET = this.getAnchoSprite() / 2;
        this.Y_OFFSET = this.getAltoSprite() / 2;
    }

    public abstract void actualizar(double deltaTime);

    public double getX() {
        return this.posX;
    }

    public double getY() {
        return this.posY;
    }

    public boolean isVisible() {
        return this.esVisible;
    }

    public void aparecer(double posInicialX, double posInicialY) {
        this.posX = posInicialX;
        this.posY = posInicialY;
        this.esVisible = true;
    }

    public void renderizar(Lienzo lienzo) {
        if (!esVisible) {
            return;
        }
        lienzo.dibujo(this.posX, this.posY, this.sprite);
        // DEBUG: Muestra los delimitadores de todos los elementos gráficos
        // lienzo.ponColorLapiz(Color.RED);
        // lienzo.rectangulo(posX, posY, anchoSprite / 2.0, altoSprite / 2.0);
    }

    public double getAnchoSprite() {
        return anchoSprite;
    }

    public double getAltoSprite() {
        return altoSprite;
    }
}