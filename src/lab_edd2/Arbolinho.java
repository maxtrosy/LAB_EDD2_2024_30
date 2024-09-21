/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab_edd2;

import PANTALLAS.Juego;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejo
 */
public class Arbolinho {

    public Nodo raiz;

    public static ArrayList<List> recorrido = new ArrayList<>();

    public Arbolinho() {
        this.raiz = null;
    }

    public void crearArbol() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\Resources\\Nodos_DS.txt"));
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] dataset = line.split(";");
                Nodo plantilla = new Nodo(Integer.parseInt(dataset[0]), "'" + dataset[1] + "'", "'" + dataset[2] + "'", "'" + dataset[3] + "'", null, null);
                if (raiz == null) {
                    raiz = plantilla;
                } else {
                    agregarRecursivo(raiz, plantilla);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }
    }

    private void crearArbolDesdeDataset() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\Resources\\Nodos_DS.txt"));
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split(";");
            int nombre = Integer.parseInt(partes[0]);
            String imagen1 = partes[1];
            String imagen2 = partes[2];
            String imagen3 = partes[3];
            boolean llegada = partes.length > 4 && Boolean.parseBoolean(partes[4]);

            Nodo nuevoNodo = new Nodo(nombre, imagen1, imagen2, imagen3, llegada, null, null);

        }
        reader.close();
    }

    public void agregarRecursivo(Nodo nodo, Nodo nuevoNodo) {
        if (nodo.nombre > nuevoNodo.nombre) {
            if (nodo.izq == null) {
                nodo.izq = nuevoNodo;
            } else {
                agregarRecursivo(nodo.izq, nuevoNodo);
            }

        } else {
            if (nodo.der == null) {
                nodo.der = nuevoNodo;
            } else {
                agregarRecursivo(nodo.der, nuevoNodo);
            }
        }
    }

    public static int alturaArbol(Nodo n1) {
        if (n1 == null) {
            return 0;
        }
        return Math.max(alturaArbol(n1.izq), alturaArbol(n1.der)) + 1;
    }

    public void imprimirArbol() {  // Método para iniciar la impresión del árbol
        int maxLevel = alturaArbol(raiz);  // Calcula la altura del árbol
        imprimirNodos(Collections.singletonList(raiz), 1, maxLevel);  // Llama al método de impresión empezando por la raíz
    }

    private void imprimirNodos(List<Nodo> nodos, int nivel, int maxLevel) {  // Método recursivo para imprimir los nodos en cada nivel
        if (nodos.isEmpty() || sonTodosNulos(nodos)) {
            return;  // Si no hay nodos o todos son null, termina la impresión
        }
        int floor = maxLevel - nivel;  // Calcula el nivel actual del piso (cuántos niveles faltan para llegar al final)
        int edgeLines = (int) Math.pow(2, Math.max(floor - 1, 0));  // Calcula cuántas líneas de conexión hay entre los niveles
        int firstSpaces = (int) Math.pow(2, floor) - 1;  // Calcula el espacio inicial antes del primer nodo
        int betweenSpaces = (int) Math.pow(2, floor + 1) - 1;  // Calcula el espacio entre los nodos en el mismo nivel

        imprimirEspacios(firstSpaces);  // Imprime los espacios iniciales

        List<Nodo> nuevosNodos = new ArrayList<>();  // Crea una nueva lista para los nodos de la siguiente línea
        for (Nodo nodo : nodos) {  // Itera sobre los nodos actuales
            if (nodo != null) {  // Si el nodo no es null
                System.out.print(nodo.nombre);  // Imprime el dato del nodo
                nuevosNodos.add(nodo.izq);  // Agrega el hijo izquierda a la lista de nuevos nodos
                nuevosNodos.add(nodo.der);  // Agrega el hijo derecho a la lista de nuevos nodos
            } else {  // Si el nodo es null
                nuevosNodos.add(null);  // Agrega null para mantener la estructura
                nuevosNodos.add(null);  // Agrega null para mantener la estructura
                System.out.print(" ");  // Imprime un espacio vacío en lugar del nodo
            }

            imprimirEspacios(betweenSpaces);  // Imprime los espacios entre nodos en el mismo nivel
        }
        System.out.println("");  // Salto de línea después de imprimir todos los nodos de este nivel

        for (int i = 1; i <= edgeLines; i++) {  // Imprime las líneas de conexión (barras diagonales) entre los nodos
            for (int j = 0; j < nodos.size(); j++) {  // Itera sobre los nodos de nuevo
                imprimirEspacios(firstSpaces - i);  // Imprime los espacios antes de la conexión

                if (nodos.get(j) == null) {  // Si el nodo es null, solo imprime los espacios necesarios
                    imprimirEspacios(edgeLines + edgeLines + i + 1);
                    continue;
                }

                if (nodos.get(j).izq != null) {
                    System.out.print("/");  // Imprime "/" para la conexión izquierda
                } else {
                    imprimirEspacios(1);  // Si no hay conexión izquierda, imprime un espacio
                }
                imprimirEspacios(i + i - 1);  // Espacio entre las conexiones izquierda y derecha

                if (nodos.get(j).der != null) {
                    System.out.print("\\");  // Imprime "\\" para la conexión derecha
                } else {
                    imprimirEspacios(1);  // Si no hay conexión derecha, imprime un espacio
                }
                imprimirEspacios(edgeLines + edgeLines - i);  // Imprime los espacios después de la conexión
            }

            System.out.println("");  // Salto de línea después de imprimir las conexiones de este nivel
        }

        imprimirNodos(nuevosNodos, nivel + 1, maxLevel);  // Llama recursivamente para imprimir el siguiente nivel de nodos
    }

    private boolean sonTodosNulos(List<Nodo> nodos) {  // Verifica si todos los nodos en una lista son null
        for (Nodo nodo : nodos) {
            if (nodo != null) {
                return false;  // Si encuentra un nodo no nulo, devuelve false
            }
        }
        return true;  // Si todos son null, devuelve true
    }

    private void imprimirEspacios(int count) {  // Método auxiliar para imprimir un número específico de espacios
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    public static void guardarAventura(String entrada, Nodo nodo) {

        List<String> aventura = new ArrayList<String>();
        aventura.add(Integer.toString(nodo.nombre));
        aventura.add(entrada);
        recorrido.add(aventura);

    }

    public void avanceHistoria(Nodo nodo, String direccion) {

        Nodo actual = nodo;

        if (direccion.equals("izquierda")) {
            if (nodo.izq == null & nodo.der == null) {
                if (nodo.llegada == true) {
                    System.out.println("Tremendo");
                }
            }

        } else {
            if (direccion.equals("derecha")) {

            }
        }
    }

    public static void recorridoPostMortem(ArrayList<List> recorrido) {

        for (List list : recorrido) {
            
        }

    }

}
