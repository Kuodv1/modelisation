package modelisation.graphique.vueImage;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import modelisation.modele.Modele;

public class ImageChargePGM extends ImageCharge {
	
	public ImageChargePGM(int width, int height) {
		super(width,height,BufferedImage.TYPE_BYTE_GRAY);
	}
	
	
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
	
	public Image getImage() {
		return this;
	}
}
