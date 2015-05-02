package modelisation.controleur.barreLaterale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelisation.modele.Modele;

public class EcouteurPixel implements ActionListener{

	protected Modele m;
	protected int choix;
	//choix = 0 <=> clique sur Stop
	//choix = 1 <=> clique sur conserver
	//choix = 2 <=> clique sur supprimers
	
	/**
	 * Constructeur EcouterPixel
	 * @param m
	 * @param choix
	 */
	public EcouteurPixel(Modele m, int choix) {
		this.m = m;
		this.choix = choix; 
		
	}

	@Override
	/**
	 * Fonction est appelé quand une action se produit 
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		m.setAction(choix);
	}
}
