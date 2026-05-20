package juego;

import edu.epromero.util.Destruible;
import edu.epromero.util.Imagen;

public abstract class NaveEnemiga extends ElementoGrafico implements Destruible, Dispara{
    public String tipo;
    public int resistencia;
    public int puntos;

    public NaveEnemiga(Imagen sprite, double anchoPantalla, double altoPantalla) {
        super(sprite, anchoPantalla, altoPantalla);
    }

    @Override
    public void aparecer(double posInicialX, double posInicialY) {
        this.posX = posInicialX;
        this.posY = posInicialY;
        this.esVisible = true;
    }

}
