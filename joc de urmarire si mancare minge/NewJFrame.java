package examen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewJFrame extends javax.swing.JFrame implements KeyListener {

    // Coordonare + raza initiala cerc
    int x = 400;
    int y = 400;
    int r = new Random().nextInt(100); // dimensiune oarecare

    int k = 0; // contor scor
    String val; // string in care convertesc contorul
    static ArrayList<Cerc> arrayCercuri = new ArrayList<Cerc>(); // array in care vom memora cercurile inamici / hrana
    static Graphics grf;

    ThreadCerc threadAdaugare = new ThreadCerc("adaug");
    ThreadCerc threadMicsorare = new ThreadCerc("micsorez");
    Thread redeseneaza;
    public static Object o = new Object();

    static int ljPanel, hjPanel;

    public NewJFrame() {
        initComponents();
        ljPanel = jPanel1.getWidth(); // latimea jPanel-ului
        hjPanel = jPanel1.getHeight(); // inaltimea jPanel-ului

        grf = this.jPanel1.getGraphics();
        jLabel3.setText("");
        jLabel4.setText(Integer.toString(r));

        // lansam firele
        threadAdaugare.start();
        threadMicsorare.start();

        redeseneaza = new Thread() { // thread folosit pentru a redesena componenta
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    repaint();
                }
            }
        };
        redeseneaza.start();

        //setFocusable(true);  // schimb statutul de focus al panelului ca sa deseneze
        addKeyListener(this); // listener pentru apasarea tastelor
    }

    // [1] Formula calculare distanta centru cercuri C1C2 = sqrt((x1 - x2)^2 + (y1 - y2)^2).
    static int atingere(int x1, int y1, int r1, int x2, int y2, int r2) {
        int distanta = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
        int sumaRaza = (r1 + r2) * (r1 + r2);

        if (distanta == sumaRaza) { // atingere
            return 1;

        } else if (distanta > sumaRaza) { // nu se ating
            return 0;

        } else { // intersectare
            return 1;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        grf.setColor(Color.red); // culoare cerc jucator
        grf.fillOval(x, y, r, r); // desenare cerc jucator

        for (int i = 0; i < arrayCercuri.size(); i++) { // parcurgere array cercuri inamici / hrana 
            grf.setColor(arrayCercuri.get(i).c); // setez culoarea inamic / hrana 
            grf.fillOval(arrayCercuri.get(i).x, arrayCercuri.get(i).y, arrayCercuri.get(i).r, arrayCercuri.get(i).r); // desenez cercul din array

            // conditie depistare hrana
            if (atingere(x, y, r, arrayCercuri.get(i).x, arrayCercuri.get(i).y, arrayCercuri.get(i).r) == 1 && arrayCercuri.get(i).c == Color.red) {
                /*(x > arrayCercuri.get(i).x && x < arrayCercuri.get(i).x + arrayCercuri.get(i).r && arrayCercuri.get(i).c == Color.red
            || y > arrayCercuri.get(i).y && y < arrayCercuri.get(i).y + arrayCercuri.get(i).r && arrayCercuri.get(i).c == Color.red
            || x < arrayCercuri.get(i).x && x > arrayCercuri.get(i).x - arrayCercuri.get(i).r && arrayCercuri.get(i).c == Color.red
            || y < arrayCercuri.get(i).y && y > arrayCercuri.get(i).y - arrayCercuri.get(i).r && arrayCercuri.get(i).c == Color.red)*/

                r += 2; // cresc raza cercului jucator
                k++; // cresc scorul
                arrayCercuri.remove(i); // elimin cercul mancat

                val = Integer.toString(k); // convertesc scorul la formatul string pentru afisare in label
                jLabel2.setText(val); // setez scorul
                jLabel3.setText("Ai mancat");
                jLabel4.setText(Integer.toString(r));

            } else {
                if (atingere(x, y, r, arrayCercuri.get(i).x, arrayCercuri.get(i).y, arrayCercuri.get(i).r) == 1 && arrayCercuri.get(i).c != Color.red) {

                    r -= 2; // micsorez raza cercului jucator
                    k--; // scad din scor
                    arrayCercuri.remove(i); // elimin cercul inamic

                    val = Integer.toString(k); // convertesc scorul la formatul string pentru afisare in label
                    jLabel2.setText(val); // setez scorul
                    jLabel3.setText("Te-ai ranit");
                    jLabel4.setText(Integer.toString(r));
                }
            }
        }
    }

    void deseneaza(Cerc cerc) {
        grf.setColor(cerc.c);
        grf.drawOval(cerc.x, cerc.y, cerc.r, cerc.r);
    }

    void sterge(Cerc cerc) {
        grf.setColor(Color.WHITE);
        grf.drawOval(cerc.x, cerc.y, cerc.r, cerc.r);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (y - 10 > 0) {
                y -= 10;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (y + 10 < hjPanel - r) {
                y += 10;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (x - 10 > 0) {
                x -= 10;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (x + 10 < ljPanel - r) {
                x += 10;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Scor");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), null));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), null));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), null));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Status");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Dimensiune");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

// [1] Metoda calculare distanta luata si modificata de pe https://www.geeksforgeeks.org/check-two-given-circles-touch-intersect/
}
