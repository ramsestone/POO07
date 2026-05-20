package juego;

import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;

@ComportamientoEnemigo(tipo = "AveDePresa", resistencia = 3, puntos = 10)
public class AveDePresa extends NaveEnemiga {

    public AveDePresa(Imagen sprite, double anchoPantalla, double altoPantalla) {
        super(sprite, anchoPantalla, altoPantalla);
    }

    @Override
    public Proyectil disparar() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void actualizar(double deltaTime) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean recibirDanio() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getVidasActuales() {
        // TODO Auto-generated method stub
        return 0;
    }

}
