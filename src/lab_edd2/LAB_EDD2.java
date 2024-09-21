/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab_edd2;

/**
 *
 * @author maxtr
 */
public class LAB_EDD2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Arbolinho e = new Arbolinho();
        Nodo verde = new Nodo(0, "a", "e", "o", null, null);
        e.crearArbol();
        e.imprimirArbol();
        e.guardarAventura("izquierda", verde);
        System.out.println(e.recorrido.get(0).get(1));
    }
    
}
