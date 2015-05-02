package modelisation.graphique.vueImage;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import modelisation.modele.Modele;

public class ImageChargePGM extends ImageCharge {
	
	/**
	 * Constructeur ImageChargePGM
	 * @param width
	 * @param height
	 */
	public ImageChargePGM(int width, int height) {
		super(width,height,BufferedImage.TYPE_BYTE_GRAY);
	}
	
	/**
	 * Initialise l'image à l'aide du modèle m
	 * @param m
	 */
	public void dessiner(Modele m) {
		int[][] recup = m.getImage();
		WritableRaster wr = this.getRaster();
		for(int y = 0; y<recup.length;y++) {
			for(int x =  0; x < recup[y].length;x++) {
				wr.setSample(x, y, 0, recup[y][x]);
			}
		}
		this.setData(wr);
	}
	
	/**
	 * Retourne une Image
	 *@return
	 */
	public Image getImage() {
		return this;
	}
}
