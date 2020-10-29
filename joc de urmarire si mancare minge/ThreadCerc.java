package examen;

import java.awt.Color;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static examen.NewJFrame.arrayCercuri;

public class ThreadCerc extends Thread {

    String name;
    static NewJFrame obi;

    @Override
    public void run() {
        while (true) {

            if ("adaug".equals(name)) { // adaugare cercuri inamici / hrana

                Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}; // color range-ul
                Color c = colors[(int) (Math.random() * 4)];

                arrayCercuri.add(new Cerc(new Random().nextInt(500), new Random().nextInt(500), new Random().nextInt(100), c)); // adaug cercul cu proprietati random

                try {
                    sleep(1000);

                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadCerc.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if ("micsorez".equals(name)) { // micsorare pana la eliminare

                for (int i = 0; i < arrayCercuri.size(); i++) {
                    arrayCercuri.get(i).r--;
                    
                    if (arrayCercuri.get(i).r == 0) { // elimin daca nu mai e vizibil
                        arrayCercuri.remove(i);
                    }
                }

                try {
                    sleep(100);

                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadCerc.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
    }

    public ThreadCerc(String name) { // preiau identificator comanda
        this.name = name;
    }
}
