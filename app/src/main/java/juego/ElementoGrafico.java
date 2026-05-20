package juego;

import java.awt.Color;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public abstract class ElementoGrafico {
    protected double posX;
    protected double posY;
    protected boolean esVisible;
    protected Imagen sprite;
    // Dimensiones de la pantalla donde vive el ElemntoGrafico
    protected double anchoPantalla;
    protected double altoPantalla;
    // Dimensiones del sprite
    protected double anchoSprite;
    protected double altoSprite;

    public ElementoGrafico(Imagen sprite, double anchoPantalla, double altoPantalla) {
        this.esVisible = false;
        this.sprite = sprite;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.anchoSprite = this.sprite.ancho();
        this.altoSprite = this.sprite.alto();

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
        if (esVisible) {
            // DEBUG: Muestra los delimitadores de todos los elementos gráficos
            lienzo.ponColorLapiz(Color.RED);
            lienzo.cuadro(posX, posY, this.getAnchoSprite() / 2);
            lienzo.dibujo(posX, posY, this.sprite);
        }
    }

    public double getAnchoSprite() {
        return anchoSprite;
    }

    public double getAltoSprite() {
        return altoSprite;
    }
}