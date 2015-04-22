package modelisation.graphique;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import modelisation.modele.Modele;

public class BarreChargement extends JPanel{
	
	JProgressBar chargement;
	Modele m;
	
	public BarreChargement(Modele m) {
		this.m = m;
		chargement = new JProgressBar();
		this.add(chargement);
	}

}
