package juego;

import java.util.ArrayList;
import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;

@ComportamientoEnemigo(tipo = "Pulverizador", resistencia = 5, puntos = 150)
public class Pulverizador extends NaveEnemiga implements Dispara {

    public Pulverizador() {
        setSprite(new Imagen(Assets.PULVERIZADOR));
        initHitBox();
        this.setPosInicialX(-75);
        this.setPosInicialY(this.altoPantalla - 75);

        this.tipoNave = "Pulverizador";
        this.puntosDeVida = 5;
        this.valorEnPuntos = 150;

        this.balasPorSegundo = 1d / 3;
        this.hitSprite = new Imagen(Assets.PULVERIZADOR_NEGATIVO);
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
    public ArrayList<Proyectil> crearProyectiles() {
        if (sistArmamento.puedeDisparar()) {
            sistArmamento.reiniciarEnfriamiento();
            ArrayList<Proyectil> proyectiles = new ArrayList<>();
            Proyectil proyectil = new ProyectilMorado();
            proyectiles.add(proyectil);
            proyectil.setPosInicialX(posX);
            proyectil.setPosInicialY(posY);
            return proyectiles;
        }
        return null;
    }

    protected void iaDeMovimiento(double deltaTime) {
        if (!isInBounds && this.posX >= 0
                && this.posX <= Juego.getAnchoPantalla()) {
            isInBounds = true;
        }
        // Estado A: La nave ya está en la zona de juego (Patrullaje)
        if (this.isInBounds) {

            this.posX +=
                    (this.velocidadNave * deltaTime) * this.factorMovimiento;

            // Verificamos si chocó con un borde para invertir la dirección
            if (this.posX >= Juego.getAnchoPantalla() || this.posX <= 0) {
                this.factorMovimiento *= -1;
            }

        } else {
            if (this.posX < 0) {
                // Si spawneó a la izquierda, la empujamos hacia la derecha
                this.posX += (this.velocidadNave * deltaTime)
                        * this.factorMovimiento;
            } else {
                // Si spawneó a la derecha, la empujamos hacia la izquierda
                this.posX -= (this.velocidadNave * deltaTime)
                        * this.factorMovimiento;
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
        return new Imagen(Assets.PULVERIZADOR_NEGATIVO);
    }

    @Override
    protected void setSprite(Imagen sprite) {
        this.sprite = sprite;
    }
}
