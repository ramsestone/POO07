package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class FondoDinamico extends ElementoGrafico {

    private double desplazamientoY;
    private double yOffset;
    private final double velocidadScroll = 50.0; // Píxeles por segundo
    private int altoFondo;

    public FondoDinamico() {
        setSprite(new Imagen(Assets.FONDO));
        this.altoFondo = this.sprite.alto();
        this.yOffset = this.altoFondo * 0.1;
        this.desplazamientoY = 0.0;
        this.esVisible = true;
    }

    @Override
    protected void setSprite(Imagen sprite) {
        this.sprite = new Imagen(Assets.FONDO);
    }

    @Override
    public void actualizar(double deltaTime) {
        // 1. Restamos la velocidad para que el fondo se mueva hacia ABAJO
        this.desplazamientoY -= velocidadScroll * deltaTime;

        // 2. Evaluamos el límite negativo. Si bajó más allá de su propia
        // altura,
        // le sumamos la altura para "teletransportarlo" suavemente y mantener
        // el bucle.
        if (this.desplazamientoY <= -this.altoFondo - yOffset) {
            this.desplazamientoY += this.altoFondo + yOffset;
        }
    }

    @Override
    public void renderizar(Lienzo lienzo) {
        int centroX = Juego.getAnchoPantalla() / 2;
        int centroYBase = Juego.getAltoPantalla() / 2;

        // DIBUJO 1: La imagen principal que va cayendo
        int posicionYImagenPrincipal =
                (int) (centroYBase + this.desplazamientoY);
        lienzo.dibujo(centroX, posicionYImagenPrincipal, this.sprite);

        // DIBUJO 2: La imagen secundaria. Al SUMARLE el alto en un plano
        // cartesiano,
        // la estamos colocando físicamente "arriba" de la imagen principal,
        // lista para entrar a la pantalla conforme la principal cae.
        double posicionYImagenSecundaria =
                posicionYImagenPrincipal + this.altoFondo + yOffset;
        lienzo.dibujo(centroX, posicionYImagenSecundaria, this.sprite);
    }
}
