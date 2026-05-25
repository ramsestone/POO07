package juego;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GestorPuntaje {

    private final String NOMBRE_ARCHIVO = "game_data.spc";
    private List<Puntuacion> topPuntajes;

    public GestorPuntaje() {
        this.topPuntajes = new ArrayList<>();
        cargarPuntajes();
    }

    /**
     * Lee el archivo y decodifica las líneas en formato "Nombre,Puntaje"
     */
    public void cargarPuntajes() {
        this.topPuntajes.clear();
        try {
            File archivo = new File(NOMBRE_ARCHIVO);
            if (archivo.exists()) {
                Scanner lector = new Scanner(archivo);
                while (lector.hasNextLine()) {
                    String linea = lector.nextLine();
                    String[] partes = linea.split(",");

                    // Validamos que la línea tenga exactamente nombre y puntaje
                    if (partes.length == 2) {
                        String nombre = partes[0];
                        int puntos = Integer.parseInt(partes[1]);
                        this.topPuntajes.add(new Puntuacion(nombre, puntos));
                    }
                }
                lector.close();
            }
        } catch (Exception e) {
            System.err.println("Error al leer el archivo de puntuaciones.");
        }
        // Ordenamos la lista de mayor a menor al terminar de leer
        Collections.sort(this.topPuntajes);
    }

    /**
     * Agrega un nuevo registro, reordena y elimina el excedente si hay más de
     * 10.
     */
    public void agregarPuntaje(String nombre, int puntos) {
        boolean jugadorExiste = false;

        // Buscamos si el piloto ya está registrado en la lista
        for (Puntuacion p : this.topPuntajes) {

            // Usamos equalsIgnoreCase para que "RAMSES" y "Ramses" cuenten como
            // el mismo
            if (p.nombre.equalsIgnoreCase(nombre)) {
                jugadorExiste = true;

                // Si el jugador existe, evaluamos si rompió su propio récord
                if (puntos > p.puntos) {
                    p.puntos = puntos; // Actualizamos el valor en memoria
                }

                break; // Rompemos el ciclo for porque ya encontramos al jugador
            }
        }
        

        // Si el jugador no puso nombre, le asignamos uno por defecto
        if (nombre == null || nombre.trim().isEmpty()) {
            nombre = "ANONIMO";
        }
        if (!jugadorExiste) {
            this.topPuntajes.add(new Puntuacion(nombre, puntos));
        }
        Collections.sort(this.topPuntajes);

        // Si la lista supera los 10 elementos, eliminamos el último (el de
        // menor puntaje)
        if (this.topPuntajes.size() > 10) {
            this.topPuntajes.remove(10);
        }

        guardarPuntajes();
    }

    private void guardarPuntajes() {
        try {
            FileWriter escritor = new FileWriter(NOMBRE_ARCHIVO, false);
            for (Puntuacion p : this.topPuntajes) {
                escritor.write(p.nombre + "," + p.puntos + "\n");
            }
            escritor.close();
        } catch (IOException e) {
            System.err.println(
                    "No se pudieron guardar las puntuaciones en el disco.");
        }
    }

    public List<Puntuacion> getTopPuntajes() {
        return this.topPuntajes;
    }

    // =========================================================================
    // Clase interna (Estructura de Datos) para agrupar Nombre y Puntaje
    // Implementa Comparable para permitir el ordenamiento automático
    // =========================================================================
    public static class Puntuacion implements Comparable<Puntuacion> {
        public String nombre;
        public int puntos;

        public Puntuacion(String nombre, int puntos) {
            this.nombre = nombre;
            this.puntos = puntos;
        }

        @Override
        public int compareTo(Puntuacion otra) {
            // Invertimos el orden (otra.puntos comparado con this.puntos)
            // para que el ordenamiento sea Descendente (el más alto primero)
            return Integer.compare(otra.puntos, this.puntos);
        }
    }
}
