package modelisation.controleur.vueImage;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import modelisation.graphique.vueImage.AffichageCentrale;

public class EcouteurResizeAffichageCentrale implements ComponentListener{

	protected AffichageCentrale pere;

	
	public EcouteurResizeAffichageCentrale(AffichageCentrale pane) {
		pere = pane;
	}
	
	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		pere.changement();
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
