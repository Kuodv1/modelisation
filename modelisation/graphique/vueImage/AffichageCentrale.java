package modelisation.graphique.vueImage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


import javax.swing.JPanel;

import modelisation.controleur.vueImage.EcouteurSelection;
import modelisation.modele.Modele;

public class AffichageCentrale extends JPanel{
	
	public AffichageCentrale(Modele m) {
		super(new BorderLayout());
		this.setPreferredSize(new Dimension(200,200));
		VueImage vm = new VueImage(m);
		this.add(vm,BorderLayout.CENTER);
	}
	

}
