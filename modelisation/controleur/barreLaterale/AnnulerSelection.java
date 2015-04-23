package modelisation.controleur.barreLaterale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelisation.modele.Modele;

public class AnnulerSelection implements ActionListener{

	protected Modele m;
	
	public AnnulerSelection(Modele m){
		this.m = m;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		m.annulerSelection();
		
	}

}
