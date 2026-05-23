package juego;

import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;

@ComportamientoEnemigo(tipo = "Ave de Presa", resistencia = 3, puntos = 10)
public class AveDePresa extends NaveEnemiga implements Dispara {

    public AveDePresa() {
        setSprite(new Imagen(Assets.AVE_DE_PRESA));
        initHitBox();
        this.setPosInicialX(- 75);
        this.setPosInicialY(this.altoPantalla - 75);

        this.tipoNave = "Ave de Presa";
        this.puntosDeVida = 3;
        this.valorEnPuntos = 10;
        
        this.balasPorSegundo = 1d / 3;
        this.hitSprite = new Imagen(Assets.AVE_DE_PRESA_NEGATIVA);
        this.setVelocidadNave(100);
        this.setSistArmamento(new SistemaDeArmamento(balasPorSegundo));
    }

    @Override
    public void Mueve(Entrada entrada) {
        // Redirige la llamada a la superclase para no duplicar código
        super.Mueve(entrada);
    }

    @Override
    public boolean recibirDanio() {
        return super.recibirDanio();
    }

    @Override
    public ProyectilRojo crearProyectil() {
        if (sistArmamento.puedeDisparar()) {
            sistArmamento.reiniciarEnfriamiento();
            ProyectilRojo proyectil = new ProyectilRojo();
            proyectil.setPosInicialX(posX);
            proyectil.setPosInicialY(posY);
            return proyectil;
        }
        return null;
    }

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

    @Override
    public void aparecer() {
        super.aparecer();
    }

    @Override
    public void actualizar(double deltaTime) {
        super.actualizar(deltaTime);
    }

    @Override
    protected Imagen getDamageSprite() {
        return new Imagen(Assets.AVE_DE_PRESA_NEGATIVA);
    }

    @Override
    protected void setSprite(Imagen sprite) {
        this.sprite = sprite;
    }
}
