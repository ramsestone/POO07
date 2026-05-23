package juego;

import java.util.ArrayList;
import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;

@ComportamientoEnemigo(tipo = "Lanza Minas", resistencia = 4, puntos = 100)
public class LanzaMinas extends NaveEnemiga {

    public LanzaMinas() {
        setSprite(new Imagen(Assets.LANZA_MINAS));
        initHitBox();

        this.setVelocidadNave(100);
        this.setPosInicialX(anchoPantalla / 2);
        this.setPosInicialY(this.altoPantalla - 75);

        this.balasPorSegundo = 1.0 / 3.0;
        this.sistArmamento = new SistemaDeArmamento(balasPorSegundo);

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
        return new Imagen(Assets.LANZA_MINAS_NEGATIVO);
    }

    @Override
    protected ArrayList<Proyectil> crearProyectiles() {
        ArrayList<Proyectil> rafaga = new ArrayList<>();

        if (this.sistArmamento.puedeDisparar()) {
            this.sistArmamento.reiniciarEnfriamiento();

            // Ciclo para crear las 8 balas
            for (int i = 0; i < 8; i++) {
                ProyectilVerde mina = new ProyectilVerde(); // O el color que le corresponda
                mina.setPosInicialX(this.posX);
                mina.setPosInicialY(this.posY);

                // 1. Calculamos el ángulo en grados (0, 45, 90, 135...) y lo pasamos a radianes
                double angulo = Math.toRadians(i * 45);

                // 2. Extraemos los componentes vectoriales
                double direccionX = Math.cos(angulo);
                double direccionY = Math.sin(angulo);

                // 3. Inyectamos la dirección geométrica en la bala
                mina.setDireccion(direccionX, direccionY);

                rafaga.add(mina);
            }
        }

        // Retornamos la lista completa al motor (puede estar vacía si está en
        // enfriamiento)
        return rafaga;
    }

    @Override
    protected void iaDeMovimiento(double deltaTime) {
        if (!isInBounds && this.posY <= Juego.getAltoPantalla()
                && this.posY <= getAlturaDeseada()) {
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
}
