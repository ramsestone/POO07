package juego;

import edu.epromero.util.FabricaAudio;
import edu.epromero.util.Imagen;

public class ProyectilVerde extends Proyectil {
    private double direccionX;
    private double direccionY;

    public ProyectilVerde() {
        setSprite(new Imagen(Assets.PROYECTIL_VERDE));
        initHitBox();
    }

    @Override
    protected void setSprite(Imagen sprite) {
        super.sprite = sprite;
    }

    @Override
    public void actualizar(double deltaTime) {
        super.actualizar(deltaTime);

        this.posX +=
                (this.velocidadProyectil * deltaTime) * this.getDireccionX();
        this.posY +=
                (this.velocidadProyectil * deltaTime) * this.getDireccionY();
    }

    @Override
    public void playSonido() {
        FabricaAudio sonido = new FabricaAudio();
        sonido.reproducir(Assets.PROY_VERDE_DISPARO);
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
