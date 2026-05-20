package juego;

import edu.epromero.util.Imagen;

public class ProyectilAzul extends Proyectil {

    public ProyectilAzul(double anchoPantalla, double altoPantalla) {
        Imagen sprite = new Imagen("app\\src\\main\\resources\\proyectil_azul.png");
        super(sprite, anchoPantalla, altoPantalla);
    }

    @Override
    public void actualizar(double deltaTime) {
        if (!esVisible) return;
        this.posY += this.VELOCIDAD_PROYECTIL * deltaTime;
    }
}
