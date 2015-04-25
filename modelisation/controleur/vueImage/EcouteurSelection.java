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
	protected Point p2;
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
				if(surImage) m.select(p1, new Point(evt.getX(),evt.getY()));
				else m.select(p1, p2);
			}
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		surImage = true;
	}

	@Override
	public void mouseExited(MouseEvent evt) {
		int x = evt.getX();
		if(x<0) x=0;
		else if(x>m.getLargeur()) {x = m.getLargeur()-1;}
		
		int y = evt.getY();
		if(y<0) y =0;
		else if (y>m.getHauteur()) {y= m.getHauteur()-1;}
		p2 = new Point(x,y);
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
