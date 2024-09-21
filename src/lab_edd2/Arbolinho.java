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

    public void imprimir(Nodo n1, int nivel) {
        if (n1 != null) {
            // Primero imprime el hijo derecho
            imprimir(n1.der, nivel + 1);

            // Imprime espacios para la indentación
            for (int i = 0; i < nivel; i++) {
                System.out.print("      ");  // Ajusta la cantidad de espacios según desees
            }

            // Imprime el dato del nodo
            System.out.println(n1.nombre);

            // Luego imprime el hijo izquierdo
            imprimir(n1.izq, nivel + 1);
        }
    }

    public void imprimirArbolVertical(Nodo nodo, int nivel) {
        if (nodo == null) return;

        // Imprimir el subárbol derecho primero
        imprimirArbolVertical(nodo.der, nivel + 1);

        // Imprimir el nodo actual con espacios
        if (nivel > 0) {
            for (int i = 0; i < nivel - 1; i++) {
                System.out.print("|\t");
            }
            System.out.print("|-------" + nodo.nombre + "\n");
        } else {
            System.out.print(nodo.nombre + "\n");
        }

        // Imprimir el subárbol izquierdo
        imprimirArbolVertical(nodo.izq, nivel + 1);
    }

    private void imprimirNodos(List<Nodo> nodos, int nivel, int maxLevel) {
        if (nodos.isEmpty() || sonTodosNulos(nodos)) {
            return;
        }
        int floor = maxLevel - nivel;
        int edgeLines = (int) Math.pow(2, Math.max(floor - 1, 0)) / 2;  // Reduce el número de líneas
        int firstSpaces = (int) Math.pow(2, floor) - 1;  // Espacio inicial
        int betweenSpaces = (int) Math.pow(2, floor) - 1;  // Espacio entre nodos

        imprimirEspacios(firstSpaces);

        List<Nodo> nuevosNodos = new ArrayList<>();
        for (Nodo nodo : nodos) {
            if (nodo != null) {
                System.out.print(nodo.nombre);
                nuevosNodos.add(nodo.izq);
                nuevosNodos.add(nodo.der);
            } else {
                nuevosNodos.add(null);
                nuevosNodos.add(null);
                System.out.print(" ");
            }

            imprimirEspacios(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= edgeLines; i++) {
            for (int j = 0; j < nodos.size(); j++) {
                imprimirEspacios(firstSpaces - i);

                if (nodos.get(j) == null) {
                    imprimirEspacios(edgeLines + edgeLines + i + 1);
                    continue;
                }

                if (nodos.get(j).izq != null) {
                    System.out.print("/");
                } else {
                    imprimirEspacios(1);
                }
                imprimirEspacios(i + i - 1);

                if (nodos.get(j).der != null) {
                    System.out.print("\\");
                } else {
                    imprimirEspacios(1);
                }
                imprimirEspacios(edgeLines + edgeLines - i);
            }

            System.out.println("");
        }

        imprimirNodos(nuevosNodos, nivel + 1, maxLevel);
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

        if (Juego.esHoja(nodo)) {

        } else {
            List<String> aventura = new ArrayList<String>();
            aventura.add(Integer.toString(nodo.nombre));
            aventura.add(entrada);
            recorrido.add(aventura);
        }

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

}
