package modelisation.graphique.menubarre.fichier;


import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import modelisation.controleur.menubarre.fichier.EcouteurQuitter;
import modelisation.modele.Modele;

/**
 * MenuFichier
 * @author Louis Mougin
 * @version Octobre 2014
 */
public class MenuFichier extends JMenu {
	
	/**
	 * JMenu Fichier, correspond au possibilite de Lancer/Pause/Reset/Quitter
	 * @param s Le nom du JMenu
	 * @param m Le modele, necessaire pour l instanciation des ecouteurs
	 */
	public MenuFichier(String s,Modele m) {
		super(s);
		
		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new EcouteurQuitter());
		add(quitter);
		
		this.setBackground(new Color(238,238,238));
	}

	/**
	 * Fonction pour mettre a jour les posibilites de cliquer ou non sur les JMenuItem du JMenu, 
	 * essentiellement Lecture et Pause
	 * @param m Le modele, necessaire pour activer ou desactiver les JMenuItem Lecture/Pause
	 */
	public void maj(Modele m) {
		
	}
}