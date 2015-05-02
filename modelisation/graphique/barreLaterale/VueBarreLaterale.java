package modelisation.graphique.barreLaterale;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import modelisation.controleur.barreLaterale.AnnulerSelection;
import modelisation.controleur.barreLaterale.EcouteurChargerImage;
import modelisation.controleur.barreLaterale.EcouteurPixel;
import modelisation.controleur.barreLaterale.EcouteurReduire;
import modelisation.modele.Modele;

public class VueBarreLaterale extends JPanel implements Observer{

	protected Modele m;
	protected JButton reduce;
	protected JButton stop;
	protected JButton conserv;
	protected JButton suppr;
	protected JButton annuler;
	protected GridLayout grid;
	
	/**
	 * Constructeur VueBarreLaterale
	 * @param m
	 */
	public VueBarreLaterale(Modele m) {
		super(new BorderLayout());
		this.m = m;
		grid = new GridLayout(6,1);
		this.setLayout(grid);
		JButton loadImage = new JButton("Charger une image");
		loadImage.addActionListener(new EcouteurChargerImage(m));
		
		this.add(loadImage);
		
		reduce = new JButton("Réduire");
		reduce.addActionListener(new EcouteurReduire(m));
		reduce.setEnabled(false);
		this.add(reduce);
		
		conserv = new JButton("Conserver");
		conserv.addActionListener(new EcouteurPixel(m,1));
		conserv.setEnabled(false);
		this.add(conserv);
		
		suppr = new JButton("Supprimer");
		suppr.addActionListener(new EcouteurPixel(m,2));
		suppr.setEnabled(false);
		this.add(suppr);
		
		stop = new JButton("Stop sélection");
		stop.addActionListener(new EcouteurPixel(m,0));
		stop.setEnabled(false);
		this.add(stop);
		
		annuler = new JButton("Annuler sélection");
		annuler.addActionListener(new AnnulerSelection(m));
		annuler.setEnabled(false);
		this.add(annuler);
	}

	@Override
	/**
	 * Fonction qui sera exécutée quand le modèle indique qu'il a changé et notifie ses observateurs
	 * @param argv0
	 * @param arg1
	 */
	public void update(Observable arg0, Object arg1) {
		if(!m.getLink().isEmpty()) {
			reduce.setEnabled(true);
			conserv.setEnabled(true);
			suppr.setEnabled(true);
			stop.setEnabled(true);
			annuler.setEnabled(m.annulerSelectPossible());
		}
		
	}
	
	
}
