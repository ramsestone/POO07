package juego;

import edu.epromero.util.Destruible;
import edu.epromero.util.Imagen;

public abstract class NaveEnemiga extends ElementoGrafico implements Destruible, Dispara{
    protected int puntosDeVida;
    protected int valorEnPuntos;
    protected String tipoNave;

    public NaveEnemiga(Imagen sprite, double anchoPantalla, double altoPantalla) {
        super(sprite, anchoPantalla, altoPantalla);
    }

}
