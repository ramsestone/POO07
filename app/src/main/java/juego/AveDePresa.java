package juego;

import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;

@ComportamientoEnemigo(tipo = "Ave de Presa", resistencia = 3, puntos = 10)
public class AveDePresa extends NaveEnemiga {

    public AveDePresa(Imagen sprite, double anchoPantalla, double altoPantalla) {
        super(sprite, anchoPantalla, altoPantalla);
        this.puntosDeVida = 3;
        this.valorEnPuntos = 10;
        this.tipoNave = "Ave de Presa";
    }

    @Override
    public Proyectil crearProyectil() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void aparecer(double posInicialX, double posInicialY) {
        super.aparecer(posInicialX, posInicialY);
    }

    @Override
    public void mover(double deltaTime) {
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

    public int getPuntos() {
        return this.valorEnPuntos;
    }

}
