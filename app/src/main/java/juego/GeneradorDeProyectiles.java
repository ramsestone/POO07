package juego;

public interface GeneradorDeProyectiles {
    /**
     * Cada nave decide qué tipo de proyectil instancia.
     */
    Proyectil crearProyectil();
    
}
