package modelisation.graphique.vueImage;

import java.awt.Color;
import java.awt.Image;

import modelisation.modele.Modele;

public class SelectionConserve extends ImageCharge{

	/**
	 * Constructeur SelectionConserve
	 * @param width
	 * @param height
	 */
	public SelectionConserve(int width, int height) {
		super(width, height, TYPE_INT_ARGB);
	}
	
	/**
	 * Initialise la zone de l'image selectionné à l'aide du modèle m
	 * @param m
	 */
	public void dessiner(Modele m) {
		int[][] recup = m.getConserverSupprimer();
		
		Color conser = new Color(55,0,55,150); 
		Color suppr = new Color(0,55,55,150);
		
		for(int y = 0; y<recup.length;y++) {
			for(int x = 0; x<recup[y].length;x++) {
        		if(recup[y][x] == 1) this.setRGB(x, y, conser.getRGB());
        		else if (recup[y][x] == -1) this.setRGB(x, y, suppr.getRGB());
			}
		}		
	}


	@Override
	/**
	 * Retourne l'image
	 * @return 
	 */
	public Image getImage() {
		return this;
	}
}
