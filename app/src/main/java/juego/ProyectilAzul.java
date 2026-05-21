package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class ProyectilAzul extends Proyectil {

    public ProyectilAzul(double anchoPantalla, double altoPantalla) {
        Imagen PROYECTIL_AZUL = new Imagen("app/src/main/resources/proyectil_azul.png");
        super(PROYECTIL_AZUL, anchoPantalla, altoPantalla);
    }

    @Override
    public void mover(double deltaTime) {
        if (!esVisible)
            return;
        super.mover(deltaTime);
        System.out.println(
                String.format("[PRY_AZ] X: {%f} | Y: {%f} | Sprite Ancho: {%f} | Sprite Alto: {%f} | isInBounds: {%s}",
                        this.posX, this.posY, this.getAnchoSprite(), this.getAltoSprite(), this.isInBounds));
        this.posY += this.VELOCIDAD_PROYECTIL * deltaTime;
    }

    @Override
    public void renderizar(Lienzo lienzo) {
        super.renderizar(lienzo);
    }
}
