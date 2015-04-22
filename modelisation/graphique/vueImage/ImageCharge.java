package modelisation.graphique.vueImage;

import java.awt.Image;
import java.awt.image.BufferedImage;

import modelisation.modele.Modele;

public abstract class ImageCharge extends BufferedImage {

	protected int width;
	protected int height;
	
	public ImageCharge(int width, int height, int imageType) {
		super(width, height, imageType);
		this.width = width;
		this.height = height;
		// TODO Auto-generated constructor stub
	}
	
	public int getHauteur() {
		return height;
	}
	
	public int getLargeur() {
		return width;
	}
	
	public abstract Image getImage();
	
	public abstract void dessiner(Modele m);

}
