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
        return lienzo.fuePulsadaTecla(KeyEvent.VK_A) || lienzo.fuePulsadaTecla(KeyEvent.VK_LEFT);
    }

    public boolean derechaPres() {
        return lienzo.fuePulsadaTecla(KeyEvent.VK_D) || lienzo.fuePulsadaTecla(KeyEvent.VK_RIGHT);
    }

    public boolean arribaPres() {
        return lienzo.fuePulsadaTecla(KeyEvent.VK_D) || lienzo.fuePulsadaTecla(KeyEvent.VK_RIGHT);
    }

    public boolean disparoPres() {
        return lienzo.fuePulsadaTecla(KeyEvent.VK_SPACE);
    }
}
