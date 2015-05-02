package modelisation.graphique.vueImage;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import modelisation.modele.Modele;

public class ImageChargePPM extends ImageCharge{

	/**
	 * Constructeur ImageChargePPM
	 * @param width
	 * @param height
	 */
	public ImageChargePPM(int width, int height) {
		super(width,height,BufferedImage.TYPE_3BYTE_BGR);
	}
	
	/**
	 * Initialise l'image à l'aide du modèle m
	 * @param m
	 */
	public void dessiner(Modele m) {
		int[][] recup = new int[m.getImage().length][m.getImage()[0].length];
		for(int i = 0; i<m.getImage().length;i++) {
			recup[i] = (int[]) m.getImage()[i].clone();
		}
		int bleu = 0;
		int rouge = 0;
		int vert = 0;
		for(int y = 0; y<recup.length;y++) {
			for(int x = 0; x<recup[y].length;x++) {
				bleu = (recup[y][x]/(1000000));
        		recup[y][x] = recup[y][x]%1000000;
        		vert = (recup[y][x]/(1000));
        		recup[y][x] = recup[y][x]%1000;
        		rouge = recup[y][x];
        		this.setRGB(x, y, new Color(rouge,vert,bleu).getRGB());
			}
		}
	}


	@Override
	/**
	 * Retourne l'image 
	 *@return
	 */
	public Image getImage() {
		 return this;
	}
}
