package modelisation.graphique.vueImage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;


import javax.swing.JPanel;

import modelisation.controleur.vueImage.EcouteurSelection;
import modelisation.modele.Modele;

public class VueImage extends JPanel implements Observer {

	protected Modele m;
	protected ImageCharge affi;
	protected ImageCharge selection;
	
	    public VueImage(Modele m) {
	    	super(new BorderLayout());
	    	setPreferredSize(new Dimension(200,200));
	    	this.m = m;
	    	m.addObserver(this);
	    	affi = null;
	    	selection = null;
	    	EcouteurSelection listener = new EcouteurSelection(m);
	    	this.addMouseListener(listener);
	    	this.addMouseMotionListener(listener);
	    }

	    public void paintComponent(Graphics g){
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;
			if(affi!=null)this.setSize(new Dimension(affi.getLargeur(),affi.getHauteur()));
	        int w = getWidth() ;
	        int h = getHeight() ;
	        GradientPaint gp = new GradientPaint(-w, -h, Color.LIGHT_GRAY, w, h, Color.WHITE, false);
	        g2.setPaint(gp);
	        g2.fillRect(0, 0, w, h);
	        if(affi!=null) g.drawImage(affi.getImage(), 0,0, this);
	        if(selection!=null) selection.dessiner(m);
	        g.drawImage(selection, 0, 0, this);
	        ((AffichageCentrale) this.getParent()).changement();
	      }            
	    
		@Override
		public void update(Observable o, Object arg) {
			if(m.getImage()!=null) {
				int[][] img = m.getImage();
				this.setSize(new Dimension(img[0].length,img.length));
				if(m.getLink().endsWith(".ppm")) {
					affi = new ImageChargePPM(img[0].length,img.length);
					selection = new SelectionConserve(img[0].length,img.length);
					affi.dessiner(m);
				} else if(m.getLink().endsWith(".pgm")) {
					affi = new ImageChargePGM(img[0].length,img.length);
					selection = new SelectionConserve(img[0].length,img.length);
					affi.dessiner(m);
				}
			}
			System.out.println("repaint");
			repaint();
			
		}
}