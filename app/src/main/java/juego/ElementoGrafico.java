package juego;

import java.awt.Color;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public abstract class ElementoGrafico {
    protected double posX;
    protected double posY;
    protected boolean esVisible;
    protected Imagen sprite;

    // Dimensiones del sprite
    protected double ancho;
    protected double alto;

    public ElementoGrafico(Imagen sprite) {
        this.esVisible = false;
        this.sprite = sprite;
        this.ancho = this.sprite.ancho();
        this.alto = this.sprite.alto();

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
            lienzo.cuadro(posX, posY, this.getAncho() / 2);
            lienzo.dibujo(posX, posY, this.sprite);
        }
    }

    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }
}