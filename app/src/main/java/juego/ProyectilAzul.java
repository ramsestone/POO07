package juego;

import edu.epromero.util.Lienzo;

public class ProyectilAzul extends Proyectil {

    public ProyectilAzul(double anchoPantalla, double altoPantalla) {
        super(Assets.PROYECTIL_AZUL, anchoPantalla, altoPantalla);
    }

    @Override
    public void actualizar(double deltaTime) {
        if (!esVisible)
            return;
        super.actualizar(deltaTime);
        this.posY += this.VELOCIDAD_PROYECTIL * deltaTime;
    }

    @Override
    public void renderizar(Lienzo lienzo) {
        super.renderizar(lienzo);
        // DEBUG
        // String debug_info = String.format("[PRY_AZ] Type: {%s} | X: {%f} | Y: {%f} |
        // Sprite Ancho: {%f} | Sprite Alto: {%f} | isInBounds: {%s}",
        // this.toString(), this.posX, this.posY, this.getAnchoSprite(),
        // this.getAltoSprite(), this.isInBounds);
        // lienzo.texto(500, 0, debug_info);
    }
}
