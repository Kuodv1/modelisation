package modelisation.controleur.barreLaterale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelisation.modele.Modele;

public class AnnulerSelection implements ActionListener{

	protected Modele m;
	
	/**
	 * Construteur AnnulerSelection
	 * @param m
	 */
	public AnnulerSelection(Modele m){
		this.m = m;
	}
	
	@Override
	/**
	 * Fonction appelé quand une action se produit
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		m.annulerSelection();
		
	}

}
