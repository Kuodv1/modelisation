package modelisation.controleur.barreLaterale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import modelisation.modele.Modele;

public class EcouteurChargerImage implements ActionListener {

	protected Modele m;
	
	public EcouteurChargerImage(Modele m) {
		this.m = m;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser dialogue = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.pgm,*.ppm","pgm","ppm");
		dialogue.setFileFilter(filter);
		filter = new FileNameExtensionFilter("*.ppm","ppm");

		int i = dialogue.showOpenDialog(null);
		
		if(i==0) {
			if (dialogue.getSelectedFile().exists()) {
			String link = dialogue.getSelectedFile().toString();
				if(link.endsWith(".pgm")) {
					m.readPgm(link);
				} else if (link.endsWith(".ppm")){
					m.readPpm(link);
				}else {
					JOptionPane.showMessageDialog(null, "Erreur : Choisissez un fichier de type PGM","Erreur",JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Erreur : Choisissez un fichier existant et de type PGM","Erreur",JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}

}
