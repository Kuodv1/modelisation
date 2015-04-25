package modelisation.controleur.vueImage;

import java.awt.Point;
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
	protected Point p1;
	protected Modele m;
	
	public EcouteurSelection(Modele m) {
		this.m = m;
		surImage = false;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		if(!isClick && evt.getButton()==ButtonRight) {
			m.supprimerSelection(evt.getX(), evt.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if(isClick && evt.getButton()==boutonChoisi) {
			isClick = false;
			if(boutonChoisi == ButtonLeft) {//System.out.println("Relachement BoutonGauche");
			m.select(p1, new Point(evt.getX(),evt.getY()));
			}
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		surImage = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		surImage = false;		
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		if(evt.getButton()==ButtonLeft && !isClick) {
			isClick = true;
			boutonChoisi = ButtonLeft;
			//System.out.println("Bouton gauche");
			p1 = new Point(evt.getX(),evt.getY());
		} else if(evt.getButton()==ButtonRight && !isClick) {
			isClick = true;
			boutonChoisi = ButtonRight;
			//System.out.println("Bouton Droit");
			p1 = new Point(evt.getX(),evt.getY());
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
