package juego;

public class SistemaDeArmamento {
    protected double tiempoEntreDisparos;
    protected double temporizador;

    /**
     * Crea un nuevo sistema de armamento.
     * @param balasPorSegundo La cadencia de tiro deseada.
     */
    public SistemaDeArmamento(double balasPorSegundo) {
        // Convertimos balas por segundo a tiempo de espera
        this.tiempoEntreDisparos = 1.0 / balasPorSegundo;
        // Inicia cargado y listo para disparar
        this.temporizador = this.tiempoEntreDisparos;
    }

    /**
     * @return true si el arma ya se enfrió y está lista para volver a disparar.
     */
    public boolean puedeDisparar() {
        return this.temporizador >= this.tiempoEntreDisparos;
        // TODO: Arreglar tiempo entre disparos. this.temporizador nunca se actualiza
    }

    /**
     * Reinicia el reloj de enfriamiento a cero.
     */
    public void reiniciarEnfriamiento() {
        this.temporizador = 0.0;
    }

}
