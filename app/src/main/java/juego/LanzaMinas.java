package juego;

import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;

@ComportamientoEnemigo(tipo = "Lanza Minas", resistencia = 4, puntos = 100)
public class LanzaMinas extends NaveEnemiga {

    public LanzaMinas() {
        setSprite(new Imagen(Assets.DESTRUCTOR));
        initHitBox();

        this.setVelocidadNave(100);
        this.setPosInicialX(anchoPantalla / 2);
        this.setPosInicialY(this.altoPantalla - 75);

        this.tipoNave = "Lanza Minas";
        this.puntosDeVida = 4;
        this.valorEnPuntos = 10;
    }

    @Override
    public void Mueve(Entrada entrada) {
        // Redirige la llamada a la superclase para no duplicar código
        super.Mueve(entrada);
    }

    @Override
    protected void setSprite(Imagen sprite) {
        this.sprite = sprite;
    }

    @Override
    protected Imagen getDamageSprite() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected ProyectilRojo crearProyectil() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void cambiarSpriteOnHit(double deltaTime, Imagen dmgSprite) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void iaDeMovimiento(double deltaTime) {
        // TODO Auto-generated method stub

    }

}
