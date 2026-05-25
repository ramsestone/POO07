package juego;

import java.awt.event.KeyEvent;
import edu.epromero.util.Lienzo;

public class Entrada {
    private Lienzo lienzo;

    public Entrada() {

    }

    public Entrada(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    public boolean izquierdaPres() {
        return lienzo.fuePulsadaTecla(KeyEvent.VK_A)
                || lienzo.fuePulsadaTecla(KeyEvent.VK_LEFT);
    }

    public boolean derechaPres() {
        return lienzo.fuePulsadaTecla(KeyEvent.VK_D)
                || lienzo.fuePulsadaTecla(KeyEvent.VK_RIGHT);
    }

    public boolean disparoPres() {
        return lienzo.fuePulsadaTecla(KeyEvent.VK_SPACE);
    }
    public boolean upPress() {
        return lienzo.fuePulsadaTecla(KeyEvent.VK_UP);
    }

    public boolean downPress() {
        return lienzo.fuePulsadaTecla(KeyEvent.VK_DOWN);
    }
}
