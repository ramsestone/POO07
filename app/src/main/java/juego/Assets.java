package juego;

import edu.epromero.util.Imagen;

/**
 * Clase centralizada para la gestión de rutas de recursos. Evita la dispersión
 * de strings en el código.
 */
public final class Assets {

    // Se declara final para que nadie pueda instanciar esta clase
    private Assets() {}

    // Rutas base (útil si decides cambiar la estructura de carpetas después)
    private static final String BASE_PATH = "../resources/";

    // Constantes públicas para acceso global
    // public static final String DESTRUCTOR = BASE_PATH + "destructor.png";
    public static final Imagen HEROE =
     new Imagen(BASE_PATH + "nave_rebelde.png");
    public static final Imagen FONDO =
     new Imagen(BASE_PATH + "background_blue.png");
    public static final Imagen AVE_DE_PRESA =
     new Imagen(BASE_PATH + "ave_de_presa.png");
    public static final Imagen AVE_DE_PRESA_NEGATIVA =
     new Imagen(BASE_PATH + "ave_de_presa_negative.png");
    public static final Imagen PROYECTIL_AZUL =
     new Imagen(BASE_PATH + "proyectil_azul.png");
    public static final Imagen PROYECTIL_ROJO =
     new Imagen(BASE_PATH + "proyectil_rojo.png");
    // Si decides cambiar la ruta, solo modificas la constante BASE_PATH
}
