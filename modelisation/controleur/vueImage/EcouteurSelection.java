package modelisation.controleur.vueImage;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import modelisation.modele.Modele;

public class EcouteurSelection implements MouseListener, MouseMotionListener{
	
	static final int ButtonLeft=MouseEvent.BUTTON1;
	static final int ButtonRight=MouseEvent.BUTTON3;
	protected boolean isClick;
	protected int boutonChoisi;
	protected boolean surImage;
	protected Modele m;
	
	public EcouteurSelection(Modele m) {
		this.m = m;
		surImage = false;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {

	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if(isClick && evt.getButton()==boutonChoisi) {
			isClick = false;
			if(boutonChoisi == ButtonLeft) System.out.println("Relachement BoutonGauche");
			else System.out.println("Relachement BoutonDroit");
			m.recap();
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		surImage = true;
		System.out.println("sur image");
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		surImage = false;
		System.out.println("pas sur image");
		
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		if(evt.getButton()==ButtonLeft && !isClick) {
			isClick = true;
			boutonChoisi = ButtonLeft;
			System.out.println("Bouton gauche");
		} else if(evt.getButton()==ButtonRight && !isClick) {
			isClick = true;
			boutonChoisi = ButtonRight;
			System.out.println("Bouton Droit");
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		if(surImage) {
			if(boutonChoisi == ButtonLeft) m.zoneSelectionne(evt.getX(), evt.getY(), true);
			else if (boutonChoisi == ButtonRight) m.zoneSelectionne(evt.getX(), evt.getY(), false);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
