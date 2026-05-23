package juego;

import edu.epromero.util.Lienzo;
import edu.epromero.util.Imagen;

public abstract class ElementoGrafico {
    protected double posX;
    protected double posY;
    protected double velocidadElemento;

    protected boolean isInBounds;

    protected Imagen sprite;
    protected double anchoSprite;
    protected double altoSprite;

    protected double anchoPantalla;
    protected double altoPantalla;

    // Offset es el valor que hay desde el centro hasta uno de sus bordes.
    protected double xOffset;
    protected double yOffset;
    protected boolean esVisible;


    // Lienzo
    protected Lienzo lienzo;

    public ElementoGrafico() {
        this.esVisible = false;
        this.anchoPantalla = Juego.getAnchoPantalla();
        this.altoPantalla = Juego.getAltoPantalla();
    }

    public abstract void actualizar(double deltaTime);

    protected void initHitBox() {
        this.anchoSprite = this.sprite.ancho();
        this.altoSprite = this.sprite.alto();

        this.xOffset = this.anchoSprite / 2.0;
        this.yOffset = this.altoSprite / 2.0;
    }

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
        lienzo.dibujo(posX, posY, sprite);
        // DEBUG: Muestra los delimitadores de todos los elementos gráficos
        // lienzo.ponColorLapiz(Color.RED);
        // lienzo.rectangulo(posX, posY, anchoSprite / 2.0, altoSprite / 2.0);
    }

    protected abstract void setSprite(Imagen sprite);

    public void setEsVisible(boolean esVisible) {
        this.esVisible = esVisible;
    }

    public void setVelocidadElemento(double velocidadElemento) {
        this.velocidadElemento = velocidadElemento;
    }

    public double getVelocidadElemento() {
        return this.velocidadElemento;
    }

}
