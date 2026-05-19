package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class Proyectil extends ElementoGrafico{

    private final double VELOCIDAD_PROYECTIL = 300;

    private Imagen sprite;
    final String SPRITE_PATH = "app/src/main/resources/proyectil_00.png";

    public Proyectil(double xInicial, double yInicial){
        super();
        this.posX = xInicial;
        this.posY = yInicial;
        this.sprite = new Imagen(SPRITE_PATH);
    }

    public void actualizarMovimiento(double deltaTime) {
        if (!esVisible) {
            return;
        }

        this.posY += VELOCIDAD_PROYECTIL * deltaTime;
    }

    public void aparecer(){
        this.esVisible = true;
    }

    // Si el elemento es visible 
    public void renderizar(Lienzo lienzo) {
        if (esVisible) {
            lienzo.dibujo(this.posX, this.posY, this.sprite);
        }
    }

}
