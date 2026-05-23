package juego;

import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;

@ComportamientoEnemigo(tipo = "Destructor", resistencia = 3, puntos = 50)
public class Destructor extends NaveEnemiga {
    private double alturaDeseada;
    private int direccionDeDisparo;


    public Destructor() {
        setSprite(new Imagen(Assets.DESTRUCTOR));
        initHitBox();
        setAlturaDeseada(145);

        this.setVelocidadNave(100);
        this.setPosInicialX(anchoPantalla / 2);
        this.setPosInicialY(this.altoPantalla - 75);
        this.direccionDeDisparo = 1;

        this.tipoNave = "Destructor";
        this.puntosDeVida = 3;
        this.valorEnPuntos = 50;

        this.balasPorSegundo = 1d / 3;
        this.setSistArmamento(new SistemaDeArmamento(balasPorSegundo));
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
        return new Imagen(Assets.DESTRUCTOR_NEGATIVO);
    }

    @Override
    protected ProyectilNaranja crearProyectil() {
        if (sistArmamento.puedeDisparar()) {
            sistArmamento.reiniciarEnfriamiento();
            ProyectilNaranja proyectil = new ProyectilNaranja();
            proyectil.setPosInicialX(posX);
            proyectil.setPosInicialY(posY);
            proyectil.setDireccion(direccionDeDisparo);
            cambiarDireccionDeDisparo();
            return proyectil;
        }
        return null;
    }

    private void cambiarDireccionDeDisparo() {
        this.direccionDeDisparo *= -1;
    }

    @Override
    protected void iaDeMovimiento(double deltaTime) {
        if (!isInBounds && this.posY <= Juego.getAltoPantalla() && this.posY <= getAlturaDeseada()) {
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
            if (this.posY >= getAlturaDeseada()) {
                this.posY -= (this.velocidadNave * deltaTime) * this.factorMovimiento;
            }
            if (this.posX < 0) {
                // Si spawneó a la izquierda, la empujamos hacia la derecha
                this.posX += (this.velocidadNave * deltaTime) * this.factorMovimiento;
            } else {
                // Si spawneó a la derecha, la empujamos hacia la izquierda
                this.posX -= (this.velocidadNave * deltaTime) * this.factorMovimiento;
            }
        }

    }
    /**
     * Asigna la altura deseada usando AltoDeLaPantalla - 
     * 
     * @param deltaTime El tiempo transcurrido (para futuras implementaciones físicas si es
     *        necesario).
     */
    public double getAlturaDeseada() {
        return alturaDeseada;
    }

    public void setAlturaDeseada(double alturaDeseada) {
        this.alturaDeseada = Juego.getAltoPantalla() - alturaDeseada;
    }

}
