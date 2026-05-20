package juego;

import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;

@ComportamientoEnemigo
public class AveDePresa extends NaveEnemiga {

    public AveDePresa(Imagen sprite, String tipo, int resistencia, int puntos) {
        super(sprite);
        this.tipo = tipo;
        this.resistencia = resistencia;
        this.puntos = puntos;
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
