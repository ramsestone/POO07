package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.LienzoStd;

public class Proyectil extends ElementoGrafico{
    private ElementoGrafico elementoPadre;

    private final double VELOCIDAD_PROYECTIL = 5;
    private final int OFFSET_Y = 36;

    private Imagen sprite;
    final String SPRITE_PATH = "app/src/main/resources/proyectil_00.png";

    private boolean isAlive = false;

    public Proyectil(ElementoGrafico elementoPadre){
        super();
        this.elementoPadre = elementoPadre;
        this.sprite = new Imagen(SPRITE_PATH);
    }

    public void actualizarMovimiento() {
        if (!esVisible) {
            return;
        }

        this.posX += VELOCIDAD_PROYECTIL;
    }

    public void aparecer(){
        this.esVisible = true;
        this.isAlive = true;
        this.posX = this.elementoPadre.posX;
        this.posY = this.elementoPadre.posY + OFFSET_Y;
        renderizar();
    }

    // Si el elemento es visible 
    public void renderizar() {
        if (esVisible && !isAlive) {
            LienzoStd.dibujo(posX, posY, this.sprite);
        }
    }

}
