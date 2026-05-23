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
        if (sistArmamento.puedeDisparar()) {
            sistArmamento.reiniciarEnfriamiento();
            ProyectilRojo proyectil = new ProyectilRojo();
            proyectil.setPosInicialX(posX);
            proyectil.setPosInicialY(posY);
            return proyectil;
        }
        return null;
    }

    @Override
    protected void cambiarSpriteOnHit(double deltaTime, Imagen dmgSprite) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void iaDeMovimiento(double deltaTime) {
        if (!isInBounds && this.posX >= 0 && this.posX <= Juego.getAnchoPantalla()) {
            isInBounds = true;
        }
        // Estado A: La nave ya está en la zona de juego (Patrullaje)
        if (this.isInBounds) {

            this.posX += (this.velocidadNave * deltaTime) * this.factorMovimiento;

            // Verificamos si chocó con un borde para invertir la dirección
            if (this.posX >= Juego.getAnchoPantalla() || this.posX <= 0) {
                this.factorMovimiento *= -1;
            }

        }
        // Estado B: La nave está fuera de la pantalla (Acercamiento)
        else {
            if (this.posX < 0) {
                // Si spawneó a la izquierda, la empujamos hacia la derecha
                this.posX += (this.velocidadNave * deltaTime) * this.factorMovimiento;
            } else {
                // Si spawneó a la derecha, la empujamos hacia la izquierda
                this.posX -= (this.velocidadNave * deltaTime) * this.factorMovimiento;
            }
        }
    }

}
