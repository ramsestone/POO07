package juego;

import edu.epromero.util.Imagen;

public class ProyectilVerde extends Proyectil{
    private double direccionX;
    private double direccionY;
    private int offset;

    public ProyectilVerde() {
        setSprite(new Imagen(Assets.PROYECTIL_VERDE));
        this.offset = 45;
        initHitBox();
    }

    @Override
    protected void setSprite(Imagen sprite) {
        super.sprite = sprite;
    }

    @Override
    public void actualizar(double deltaTime) {
        super.actualizar(deltaTime);

        this.posX += (this.VELOCIDAD_PROYECTIL * deltaTime) * this.getDireccionX();
        this.posY += (this.VELOCIDAD_PROYECTIL * deltaTime) * this.getDireccionY();
    }

    public void setDireccion(double xDireccion, double yDireccion) {
        this.direccionX = xDireccion;
        this.direccionY = yDireccion;
    }

    public double getDireccionX() {
        return direccionX;
    }

    public void setDireccionX(int direccionX) {
        this.direccionX = direccionX;
    }

    public double getDireccionY() {
        return direccionY;
    }

    public void setDireccionY(int direccionY) {
        this.direccionY = direccionY;
    }
}
