package modelisation.graphique.menubarre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import modelisation.modele.Modele;
import modelisation.graphique.menubarre.fichier.MenuFichier;

/**
 * VueMenuBarre
 * Classe permettant l affichage d une barre de menu situe en haut de la fenetre
 * @author Louis Mougin
 * @version Octobre 2014
 */
public class VueMenuBarre extends JPanel implements Observer {
	
	protected JMenu menuFichier;
	protected JMenu menuAlgo;
	protected JMenu menuChoixCouleur;
	protected JMenu menuParametre;
	protected Modele m;
	
	/**
	 * Instanciation de la vue
	 * @param m Le modele, necessaire pour l instanciation des JMenu
	 */
	public VueMenuBarre(Modele m) {
		super(new BorderLayout());	
		setPreferredSize(new Dimension(900,25));
		this.m = m;
		m.addObserver(this);
		JMenuBar menu_bar1 = new JMenuBar();
		menuFichier = new MenuFichier("Fichier",m);
	/*	menuAlgo = new MenuChoixAlgo("Algorithme de tri",m);
		menuChoixCouleur = new MenuChoixCouleurCase("Couleur",m);
		menuParametre = new MenuParametres("Paramètres",m);*/
		menu_bar1.add(menuFichier);
	/*	menu_bar1.add(menuAlgo);
		menu_bar1.add(menuChoixCouleur);
		menu_bar1.add(menuParametre);*/
		menu_bar1.setBackground(new Color(238,238,238));
		
		this.add(menu_bar1);
	}

	/**
	 * Fonction pour prevenir les JMenu afin qu ils se mettent a jour
	 * @param m Le modele, pour que les menus se mettent a jour dans de bonne condition
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
	/*	((MenuFichier) menuFichier).maj(m);
		((MenuChoixAlgo) menuAlgo).maj(m);
		((MenuParametres) menuParametre).maj(m);
		((MenuChoixCouleurCase) menuChoixCouleur).maj(m);*/
	}
}