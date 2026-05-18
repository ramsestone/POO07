package juego;

public abstract class ElementoGrafico {
    protected double posX;
    protected double posY;
    protected boolean esVisible;

    public ElementoGrafico() {
        this.esVisible = false;
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

    public abstract void aparecer();
}