package modelisation.controleur.menubarre.fichier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * EcouteurQuitter
 * @author Louis Mougin
 * @version Octobre 2014
 */
public class EcouteurQuitter implements ActionListener {

	/**
	* Instancie l ecouteur.
	*/
	public EcouteurQuitter() {

	}

	/**
	* @param e Un evenement sur le bouton.
	* Quitte l application
	*/
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}