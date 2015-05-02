package modelisation.graphique.vueImage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

import modelisation.modele.Modele;

public class AffichageCentrale extends JPanel{
	
	protected VueImage vm;
	
	/**
	 * Constructeur AffichageCentrale
	 * @param m
	 */
	public AffichageCentrale(Modele m) {
		super(new BorderLayout());
		this.setPreferredSize(new Dimension(200,200));
		vm = new VueImage(m);
		this.add(vm,BorderLayout.CENTER);
	}
	
	/**
	 * Initialisation de vm
	 */
	public void changement() {
		vm.setLocation(((this.getWidth())-vm.getWidth())/2,(this.getHeight()-vm.getHeight())/2);
	}

}
