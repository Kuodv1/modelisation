package modelisation.graphique.vueImage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;

import javax.swing.JPanel;

import modelisation.modele.Modele;

public class ImageChargePGM extends ImageCharge {

	protected Image img;
	
	public ImageChargePGM(int width, int height) {
		super(width,height,BufferedImage.TYPE_BYTE_GRAY);
		img = null;
	}
	
	
	public void dessiner(Modele m) {
		int[][] recup = m.getImage();
		ColorModel cm = this.getColorModel();
		int index = 0;
		byte[]pix = new byte[recup.length * recup[0].length];
		for(int y = 0; y<recup.length;y++) {
			for(int x =  0; x < recup[y].length;x++) {
				pix[index++] = new Byte((byte) recup[y][x]);
			}
		}
		JPanel test = new JPanel(); 
		img = test.createImage(new MemoryImageSource(recup[0].length, recup.length,cm,pix,0,recup[0].length));
		
	}
	
	public Image getImage() {
		return img;
	}
}
