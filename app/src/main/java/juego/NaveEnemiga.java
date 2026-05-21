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

}
