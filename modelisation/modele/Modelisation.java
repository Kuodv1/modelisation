package modelisation.modele;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import modelisation.graphique.barreLaterale.VueBarreLaterale;
import modelisation.graphique.menubarre.VueMenuBarre;
import modelisation.graphique.vueImage.AffichageCentrale;
import modelisation.graphique.vueImage.VueImage;

public class Modelisation extends JFrame{

	/**
	 * Constructeur de Modelisation
	 */
	public Modelisation() {
		super("Projet Modelisation - Redimension d'une image");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Modele m = new Modele();
        VueMenuBarre vmb = new VueMenuBarre(m);
        VueBarreLaterale vbl = new VueBarreLaterale(m);
        AffichageCentrale ac = new AffichageCentrale(m);
        m.addObserver(vbl);
        add(vmb, BorderLayout.NORTH);
        add(vbl, BorderLayout.EAST);
        add(ac, BorderLayout.CENTER);
        pack() ;
        setVisible(true);
	}
	
	
    public static void main(String[] args) {
        new Modelisation() ;
    }
}
