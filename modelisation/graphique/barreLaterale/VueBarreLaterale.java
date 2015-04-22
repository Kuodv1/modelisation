package modelisation.graphique.barreLaterale;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelisation.controleur.barreLaterale.EcouteurChargerImage;
import modelisation.controleur.barreLaterale.EcouteurPixel;
import modelisation.controleur.barreLaterale.EcouteurReduire;
import modelisation.modele.Modele;

public class VueBarreLaterale extends JPanel implements Observer{

	protected Modele m;
	protected JButton reduce;
	protected GridLayout grid;
	
	public VueBarreLaterale(Modele m) {
		super(new BorderLayout());
		this.m = m;
		grid = new GridLayout(5,1);
		this.setLayout(grid);
		JButton loadImage = new JButton("Charger une image");
		loadImage.addActionListener(new EcouteurChargerImage(m));
		
		this.add(loadImage);
		
		reduce = new JButton("Réduire");
		reduce.addActionListener(new EcouteurReduire(m));
		reduce.setEnabled(false);
		
		this.add(reduce);
		
		JButton conserv = new JButton("Conserver");
		conserv.addActionListener(new EcouteurPixel(m,1));
		this.add(conserv);
		
		JButton suppr = new JButton("Supprimer");
		suppr.addActionListener(new EcouteurPixel(m,2));
		this.add(suppr);
		
		JButton stop = new JButton("Stop sélection");
		stop.addActionListener(new EcouteurPixel(m,0));
		this.add(stop);
		

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(!m.getLink().isEmpty()) {
			reduce.setEnabled(true);
		}
		
	}
	
	
}
