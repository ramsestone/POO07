package juego;

public class ProyectilRojo extends Proyectil{
    
    public ProyectilRojo(double anchoPantalla, double altoPantalla) {
        super(Assets.PROYECTIL_AZUL, anchoPantalla, altoPantalla);
    }

    @Override
    public void actualizar(double deltaTime) {
        if (!esVisible)
            return;
        super.actualizar(deltaTime);
        this.posY -= this.VELOCIDAD_PROYECTIL * deltaTime;
    }
}
