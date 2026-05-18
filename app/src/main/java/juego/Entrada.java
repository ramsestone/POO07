package juego;

import java.awt.event.KeyEvent;
import edu.epromero.util.LienzoStd;

public class Entrada {
    
    public boolean izquierdaPres () {
        return LienzoStd.fuePulsadaTecla(KeyEvent.VK_A) || LienzoStd.fuePulsadaTecla(KeyEvent.VK_LEFT);
    }

    public boolean derechaPres () {
        return LienzoStd.fuePulsadaTecla(KeyEvent.VK_D) || LienzoStd.fuePulsadaTecla(KeyEvent.VK_RIGHT);
    }

    public boolean disparoPres () {
        return LienzoStd.fuePulsadaTecla(KeyEvent.VK_SPACE) || LienzoStd.esRatonPresionado();
    }
}
