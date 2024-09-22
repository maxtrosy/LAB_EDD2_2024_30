/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PANTALLAS;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import lab_edd2.Arbolinho;
import static lab_edd2.Arbolinho.guardarAventura;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import lab_edd2.Nodo;

/**
 *
 * @author maxtr
 */
public class Juego extends javax.swing.JFrame {

    private boolean botonPresionado = false;
    private List<Nodo> nodosRecorridos = new ArrayList<>();

    private String respuestaCorrecta;

    static Nodo nodoActual;

    static Nodo raiz;

    static int Altura = 13;

    private Map<Integer, String[]> datasetImagenes;

    Arbolinho arbol = new Arbolinho();

    static Arbolinho raizArbolihno;

    /**
     * Creates new form Juego
     */
    public Juego() {
        initComponents();

        jLabel1.revalidate();
        jLabel1.repaint();

        Color customColor = new Color(242, 242, 242);

        BtnOpcion1.setForeground(customColor);
        BtnOpcion2.setForeground(customColor);
        BtnOpcion3.setForeground(customColor);
        setLocationRelativeTo(null);
        transparenciaButton();

        try {
            File fontStyle = new File("src\\Fuente\\league-spartan\\LeagueSpartan-Bold.otf");

            Font fontAcertijo = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(40f);
            Font fontOpciones = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(30f);
            TxtAcertijo.setFont((fontAcertijo));
            BtnOpcion1.setFont((fontOpciones));
            BtnOpcion2.setFont((fontOpciones));
            BtnOpcion3.setFont((fontOpciones));
        } catch (Exception e) {
            e.printStackTrace();
        }

        arbol.crearArbol();
        nodoActual = arbol.raiz;
        raiz = arbol.raiz;

        cargarDatasetImagenes();
        actualizarImagenFondo();

        plantilla.setText(Integer.toString(nodoActual.nombre));
        jLabel2.setText(Integer.toString(1) + ", Raíz");

        arbol.guardarAventura("Raiz", nodoActual);

        TxtAcertijo.setVisible(false);
        BtnOpcion1.setVisible(false);
        BtnOpcion2.setVisible(false);
        BtnOpcion3.setVisible(false);
        BtnIzquierda.setVisible(false);
        BtnDerecha.setVisible(false);
    }

    private void cargarDatasetImagenes() {
        datasetImagenes = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\Resources\\Nodos_DS.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                int nodoNombre = Integer.parseInt(partes[0]);
                datasetImagenes.put(nodoNombre, partes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void actualizarImagenFondo() {
        String[] infoNodo = datasetImagenes.get(nodoActual.nombre);
        if (infoNodo != null) {
            String imagen1 = infoNodo[1];
            String imagen2 = infoNodo[2];
            String imagen3 = infoNodo[3];

            if (BtnAcertijo.isVisible()) {
                jLabel1.setIcon(new ImageIcon("src\\Resources\\ImagenesJuego\\" + imagen1));
            } else if (TxtAcertijo.isVisible()) {
                jLabel1.setIcon(new ImageIcon("src\\Resources\\ImagenesJuego\\" + imagen2));
            } else if (BtnIzquierda.isVisible() && BtnDerecha.isVisible()) {
                jLabel1.setIcon(new ImageIcon("src\\Resources\\ImagenesJuego\\" + imagen3));
            }

            jLabel1.revalidate();
            jLabel1.repaint();
        }
    }

    public void transparenciaButton() {
        BtnAcertijo.setOpaque(false);
        BtnAcertijo.setContentAreaFilled(false);
        BtnAcertijo.setBorderPainted(false);

        BtnFin.setOpaque(false);
        BtnFin.setContentAreaFilled(false);
        BtnFin.setBorderPainted(false);

        BtnIzquierda.setOpaque(false);
        BtnIzquierda.setContentAreaFilled(false);
        BtnIzquierda.setBorderPainted(false);
        BtnIzquierda.setFont(BtnIzquierda.getFont().deriveFont(0f));

        BtnDerecha.setOpaque(false);
        BtnDerecha.setContentAreaFilled(false);
        BtnDerecha.setBorderPainted(false);
        BtnDerecha.setFont(BtnDerecha.getFont().deriveFont(0f));

        BtnOpcion1.setOpaque(false);
        BtnOpcion1.setContentAreaFilled(false);
        BtnOpcion1.setBorderPainted(false);

        BtnOpcion2.setOpaque(false);
        BtnOpcion2.setContentAreaFilled(false);
        BtnOpcion2.setBorderPainted(false);

        BtnOpcion3.setOpaque(false);
        BtnOpcion3.setContentAreaFilled(false);
        BtnOpcion3.setBorderPainted(false);

    }

    private void verificarRespuesta(String respuestaSeleccionada) {
        if (respuestaSeleccionada.equals(respuestaCorrecta)) {
            new Pop_up("src\\Resources\\PopUps\\2.png");

            BtnIzquierda.setVisible(true);
            BtnDerecha.setVisible(true);

            TxtAcertijo.setVisible(false);
            BtnOpcion1.setVisible(false);
            BtnOpcion2.setVisible(false);
            BtnOpcion3.setVisible(false);
            BtnAcertijo.setVisible(false);

            actualizarImagenFondo();
        } else {
            new Pop_up("src\\Resources\\PopUps\\1.png");;

            BtnIzquierda.setVisible(false);
            BtnDerecha.setVisible(false);

            BtnAcertijoActionPerformed(null);
        }
    }

    public void avanceHistoria(Nodo nodo, String direccion) {
        if (direccion.toLowerCase().equals("izquierda")) {
            if (nodoActual.izq == null && nodoActual.der == null) {

                verificarNodoLlegada(nodoActual, BtnIzquierda.getText());
            } else {
                nodoActual = nodoActual.izq;
                guardarAventura(direccion, nodoActual);
                arbol.guardarAventura(BtnIzquierda.getText(), nodoActual);
                plantilla.setText(Integer.toString(nodoActual.nombre));
                BtnIzquierda.setVisible(false);
                BtnDerecha.setVisible(false);
                BtnAcertijo.setVisible(true);
                actualizarImagenFondo();
                if (esHoja(nodoActual)) {
                    verificarNodoLlegada(nodoActual, BtnIzquierda.getText());
                }
            }
        } else if (direccion.toLowerCase().equals("derecha")) {
            if (nodoActual.izq == null && nodoActual.der == null) {

                verificarNodoLlegada(nodoActual, BtnDerecha.getText());
            } else {
                nodoActual = nodoActual.der;
                arbol.guardarAventura(BtnDerecha.getText(), nodoActual);
                plantilla.setText(Integer.toString(nodoActual.nombre));
                BtnIzquierda.setVisible(false);
                BtnDerecha.setVisible(false);
                BtnAcertijo.setVisible(true);
                actualizarImagenFondo();
                if (esHoja(nodoActual)) {
                    verificarNodoLlegada(nodoActual, BtnDerecha.getText());
                }
            }
        }

    }

    private void mostrarSecuenciaImagenes() {
        if (nodosRecorridos.isEmpty()) {
            return;
        }

        // Obtener el índice del nodo actual (último nodo de la lista)
        int currentIndex = nodosRecorridos.size() - 1;

        // Obtener el nodo anterior (penúltimo nodo)
        final Nodo nodoAnterior; // Declarar como final
        if (currentIndex > 0) {
            nodoAnterior = nodosRecorridos.get(currentIndex - 1); // Penúltimo nodo
        } else {
            nodoAnterior = null; // Asignar null si no hay nodo anterior
        }

        Timer timer = new Timer(500, new ActionListener() {
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < nodosRecorridos.size()) {
                    Nodo nodo = nodosRecorridos.get(index);
                    String[] infoNodo = datasetImagenes.get(nodo.nombre);

                    if (infoNodo != null) {
                        String imagen = infoNodo[1];
                        if (imagen != null && !imagen.isEmpty()) {
                            jLabel1.setIcon(new ImageIcon("src\\Resources\\ImagenesJuego\\" + imagen));
                            jLabel1.revalidate();
                            jLabel1.repaint();
                        } else {
                            System.out.println("Imagen no encontrada para el nodo: " + nodo.nombre);
                        }
                    } else {
                        System.out.println("Información del nodo no encontrada para el nodo: " + nodo.nombre);
                    }

                    index++;
                } else {
                    // Cuando termine la secuencia, mostrar la imagen del nodo anterior
                    if (nodoAnterior != null) {
                        String[] infoNodoAnterior = datasetImagenes.get(nodoAnterior.nombre);
                        if (infoNodoAnterior != null) {
                            String imagenAnterior = infoNodoAnterior[1];
                            if (imagenAnterior != null && !imagenAnterior.isEmpty()) {
                                jLabel1.setIcon(new ImageIcon("src\\Resources\\ImagenesJuego\\" + imagenAnterior));
                                jLabel1.revalidate();
                                jLabel1.repaint();
                            }
                        }
                    }

                    ((Timer) e.getSource()).stop(); // Detener el temporizador
                }
            }
        });

        timer.start();
    }

    public static boolean esHoja(Nodo nodo) {
        return nodo.izq == null && nodo.der == null;

    }

    private void verificarNodoLlegada(Nodo nodo, String direccion) {
        if (nodo.llegada) {
            if (nodo.nombre == 207) {
                jLabel1.setIcon(new ImageIcon("src\\Resources\\ImagenesJuego\\53.png"));
                BtnFin.setVisible(true);
            }

        } else {
            new Pop_up("src\\Resources\\PopUps\\3.png");
            nodoActual = encontrarPadre(raiz, nodo);

            jLabel2.setText(Integer.toString((Altura - nodoActual.alturaArbol(nodoActual)) + 1) + ", " + direccion);
            actualizarImagenFondo();
            plantilla.setText(Integer.toString(nodoActual.nombre));
            BtnIzquierda.setVisible(false);
            BtnDerecha.setVisible(false);
            BtnAcertijo.setVisible(true);
            //arbol.imprimirArbol();
            ImageIcon icono = new ImageIcon("src\\Resources\\PopUps\\3.png");
            JOptionPane.showMessageDialog(null, "", "¡Te moriste!", JOptionPane.PLAIN_MESSAGE, icono);
            arbol.recorridoMuerte(arbol.recorrido);

        }

    }

    private Nodo encontrarPadre(Nodo raiz, Nodo nodo) {
        if (raiz == null || nodo == null) {
            return null;
        }

        if (raiz.izq == nodo || raiz.der == nodo) {
            return raiz;
        }

        Nodo left = encontrarPadre(raiz.izq, nodo);
        if (left != null) {
            return left;
        }

        return encontrarPadre(raiz.der, nodo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        BtnVerRecorrido = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        BtnFin = new javax.swing.JButton();
        BtnAcertijo = new javax.swing.JButton();
        TxtAcertijo = new javax.swing.JLabel();
        BtnOpcion1 = new javax.swing.JButton();
        BtnOpcion2 = new javax.swing.JButton();
        BtnOpcion3 = new javax.swing.JButton();
        BtnIzquierda = new javax.swing.JButton();
        BtnDerecha = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        plantilla = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 800));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1280, 800));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, -1, -1));

        BtnVerRecorrido.setText("jButton2");
        BtnVerRecorrido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVerRecorridoActionPerformed(evt);
            }
        });
        getContentPane().add(BtnVerRecorrido, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 690, 340, 60));

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\maxtr\\OneDrive\\Documentos\\GitHub\\LAB_EDD2_2024_30\\src\\Resources\\cerrar.png")); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 10, -1, -1));

        BtnFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFinActionPerformed(evt);
            }
        });
        getContentPane().add(BtnFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 710, 160, 60));

        BtnAcertijo.setBackground(new java.awt.Color(242, 242, 242));
        BtnAcertijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAcertijoActionPerformed(evt);
            }
        });
        getContentPane().add(BtnAcertijo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, 340, 100));

        TxtAcertijo.setText("jLabel2");
        TxtAcertijo.setToolTipText("");
        TxtAcertijo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(TxtAcertijo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 1070, 230));

        BtnOpcion1.setBackground(new java.awt.Color(242, 242, 242));
        BtnOpcion1.setText("jButton1");
        BtnOpcion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOpcion1ActionPerformed(evt);
            }
        });
        getContentPane().add(BtnOpcion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 460, 340, 100));

        BtnOpcion2.setBackground(new java.awt.Color(242, 242, 242));
        BtnOpcion2.setText("jButton1");
        BtnOpcion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOpcion2ActionPerformed(evt);
            }
        });
        getContentPane().add(BtnOpcion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 460, 340, 100));

        BtnOpcion3.setBackground(new java.awt.Color(242, 242, 242));
        BtnOpcion3.setText("jButton1");
        BtnOpcion3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOpcion3ActionPerformed(evt);
            }
        });
        getContentPane().add(BtnOpcion3, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 460, 340, 100));

        BtnIzquierda.setText("izquierda");
        BtnIzquierda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIzquierdaActionPerformed(evt);
            }
        });
        getContentPane().add(BtnIzquierda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 630, 160, 40));

        BtnDerecha.setText("derecha");
        BtnDerecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDerechaActionPerformed(evt);
            }
        });
        getContentPane().add(BtnDerecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 630, 150, 50));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 730, 320, -1));

        plantilla.setText("jLabel2");
        getContentPane().add(plantilla, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\maxtr\\OneDrive\\Documentos\\GitHub\\LAB_EDD2_2024_30\\src\\Resources\\ImagenesJuego\\10.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnAcertijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAcertijoActionPerformed

        TxtAcertijo.setVisible(true);
        BtnOpcion1.setVisible(true);
        BtnOpcion2.setVisible(true);
        BtnOpcion3.setVisible(true);
        BtnAcertijo.setVisible(false);

        try {

            BufferedReader reader = new BufferedReader(new FileReader("src/Resources/Acertijos_DS.txt"));
            List<String[]> listaAcertijos = new ArrayList<>();
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                listaAcertijos.add(partes);
            }

            Random random = new Random();
            int indice = random.nextInt(listaAcertijos.size());
            String[] acertijoSeleccionado = listaAcertijos.get(indice);

            try {
                File fontStyle = new File("src\\Fuente\\league-spartan\\LeagueSpartan-Bold.otf");
                Font fontAcertijo = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(45f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(fontAcertijo);
                TxtAcertijo.setFont(fontAcertijo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String texto = acertijoSeleccionado[1];
            String textoHtml = "<html><div style='text-align: center;'>" + texto.replace("\n", "<br>") + "</div></html>";
            TxtAcertijo.setText(textoHtml);

            BtnOpcion1.setText(acertijoSeleccionado[2]);
            BtnOpcion2.setText(acertijoSeleccionado[3]);
            BtnOpcion3.setText(acertijoSeleccionado[4]);

            respuestaCorrecta = acertijoSeleccionado[5];
            String[] infoNodo = datasetImagenes.get(nodoActual.nombre);
            if (infoNodo != null) {
                String imagen2 = infoNodo[2];
                jLabel1.setIcon(new ImageIcon("src\\Resources\\ImagenesJuego\\" + imagen2));
                jLabel1.revalidate();
                jLabel1.repaint();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_BtnAcertijoActionPerformed


    private void BtnIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIzquierdaActionPerformed
        avanceHistoria(nodoActual, BtnIzquierda.getText());
        jLabel2.setText(Integer.toString(Altura - nodoActual.alturaArbol(nodoActual)) + ", " + BtnIzquierda.getText());

        arbol.guardarAventura(BtnIzquierda.getText(), nodoActual);
        nodosRecorridos.add(nodoActual);
        System.out.println(arbol.recorrido);

        if (esHoja(nodoActual) && nodoActual.nombre != 207) {
            arbol.imprimirArbol();
            new Pop_up("src\\Resources\\PopUps\\3.png");

        }
    }//GEN-LAST:event_BtnIzquierdaActionPerformed

    private void BtnDerechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDerechaActionPerformed
        avanceHistoria(nodoActual, BtnDerecha.getText());

        jLabel2.setText(Integer.toString(Altura - nodoActual.alturaArbol(nodoActual)) + ", " + BtnDerecha.getText());
        arbol.guardarAventura(BtnDerecha.getText(), nodoActual);
        nodosRecorridos.add(nodoActual);

        System.out.println(arbol.recorrido);

        if (esHoja(nodoActual) && nodoActual.nombre != 207) {
            arbol.imprimirArbol();
            new Pop_up("src\\Resources\\PopUps\\3.png");

        }
    }//GEN-LAST:event_BtnDerechaActionPerformed

    private void BtnOpcion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOpcion1ActionPerformed
        verificarRespuesta(BtnOpcion1.getText());
    }//GEN-LAST:event_BtnOpcion1ActionPerformed

    private void BtnOpcion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOpcion2ActionPerformed
        verificarRespuesta(BtnOpcion2.getText());
    }//GEN-LAST:event_BtnOpcion2ActionPerformed

    private void BtnOpcion3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOpcion3ActionPerformed
        verificarRespuesta(BtnOpcion3.getText());
    }//GEN-LAST:event_BtnOpcion3ActionPerformed

    private void BtnFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFinActionPerformed
        this.setVisible(false);
        Pantalla_Final frameinstruc = new Pantalla_Final();
        frameinstruc.setVisible(true);
    }//GEN-LAST:event_BtnFinActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BtnVerRecorridoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVerRecorridoActionPerformed
        actualizarImagenFondo();
    }//GEN-LAST:event_BtnVerRecorridoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Juego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAcertijo;
    private javax.swing.JButton BtnDerecha;
    private javax.swing.JButton BtnFin;
    private javax.swing.JButton BtnIzquierda;
    private javax.swing.JButton BtnOpcion1;
    private javax.swing.JButton BtnOpcion2;
    private javax.swing.JButton BtnOpcion3;
    private javax.swing.JButton BtnVerRecorrido;
    private javax.swing.JLabel TxtAcertijo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel plantilla;
    // End of variables declaration//GEN-END:variables
}
