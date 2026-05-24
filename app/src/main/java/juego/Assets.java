package juego;

/**
 * Clase centralizada para la gestión de rutas de recursos. Evita la dispersión
 * de strings en el código.
 */
public final class Assets {

  // Se declara final para que nadie pueda instanciar esta clase
  private Assets() {

  }

  // Rutas base (útil si decides cambiar la estructura de carpetas después)
  private static final String BASE_PATH = "/resources/";

  // Musica
  public static final String MUSICA = BASE_PATH + "music.wav";

  // Constantes públicas para acceso global
  public static final String FONDO = BASE_PATH + "fondo.png";

  public static final String HEROE = BASE_PATH + "nave_rebelde.png";
  public static final String HEROE_NEGATIVO =
      BASE_PATH + "nave_rebelde_negativo.png";

  public static final String DESTRUCTOR = BASE_PATH + "destructor.png";
  public static final String DESTRUCTOR_NEGATIVO =
      BASE_PATH + "destructor_negativo.png";

  public static final String AVE_DE_PRESA = BASE_PATH + "ave_de_presa.png";
  public static final String AVE_DE_PRESA_NEGATIVA =
      BASE_PATH + "ave_de_presa_negative.png";

  public static final String LANZA_MINAS = BASE_PATH + "lanza_minas.png";
  public static final String LANZA_MINAS_NEGATIVO =
      BASE_PATH + "lanza_minas_negativo.png";

  public static final String PROYECTIL_AZUL = BASE_PATH + "proyectil_azul.png";

  public static final String PROYECTIL_ROJO = BASE_PATH + "proyectil_rojo.png";

  public static final String PROYECTIL_NARANJA =
      BASE_PATH + "proyectil_naranja.png";

  public static final String PROYECTIL_VERDE =
      BASE_PATH + "proyectil_verde.png";
}
