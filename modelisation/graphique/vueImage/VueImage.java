package modelisation.graphique.vueImage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import java.awt.image.*;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modelisation.controleur.vueImage.EcouteurSelection;
import modelisation.modele.Modele;

public class VueImage extends JPanel implements Observer {

	protected Modele m;
	protected ImageCharge affi;
	
	    public VueImage(Modele m) {
	    	super(new BorderLayout());
	    	setPreferredSize(new Dimension(200,200));
	    	this.m = m;
	    	m.addObserver(this);
	    	affi = null;
	    	EcouteurSelection listener = new EcouteurSelection(m);
	    	this.addMouseListener(listener);
	    	this.addMouseMotionListener(listener);
	    }

	    public void paintComponent(Graphics g){
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;
	        int w = getWidth() ;
	        int h = getHeight() ;
	        GradientPaint gp = new GradientPaint(-w, -h, Color.LIGHT_GRAY, w, h, Color.WHITE, false);
	        g2.setPaint(gp);
	        g2.fillRect(0, 0, w, h);
	        if(affi!=null) g.drawImage(affi.getImage(), (this.getWidth()-affi.getLargeur())/2, (this.getHeight()-affi.getHauteur())/2, this);             
	      }            
	    
		@Override
		public void update(Observable o, Object arg) {
			if(m.getImage()!=null) {
				int[][] img = m.getImage();
				this.setSize(new Dimension(img[0].length,img.length));
				if(m.getLink().endsWith(".ppm")) {
					affi = new ImageChargePPM(img[0].length,img.length);
					affi.dessiner(m);
				} else if(m.getLink().endsWith(".pgm")) {
					affi = new ImageChargePGM(img[0].length,img.length);
					affi.dessiner(m);
				}
			}
			System.out.println("repaint");
			repaint();
			
		}
}