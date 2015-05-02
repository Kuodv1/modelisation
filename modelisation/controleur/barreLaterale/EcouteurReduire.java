package modelisation.controleur.barreLaterale;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelisation.modele.Modele;

public class EcouteurReduire implements ActionListener {

	protected Modele m;
	protected JTextField reducLargeur;
	protected JTextField reducHauteur;
	protected JComboBox choixMethode;
	
	/**
	 * Construteur EcouterReduire
	 * @param m
	 */
	public EcouteurReduire(Modele m) {
		this.m = m;
		this.reducLargeur = new JTextField(5);
		this.reducHauteur = new JTextField(5);
		String[] methodeString = { "Classique","Energie avant" };
		choixMethode = new JComboBox(methodeString);
		
	}

	@Override
	/**
	 * Fonction est appelé quand une action se produit
	 * @param arg0
	 */
	public void actionPerformed(ActionEvent arg0) {
		JPanel redim = new JPanel(new GridLayout(3,2));
		redim.add(new JLabel("Réduire la largeur de combien de pixels ? "));
		redim.add(reducLargeur);
		redim.add(new JLabel("Réduire la hauteur de combien de pixels ? "));
		redim.add(reducHauteur);
		redim.add(new JLabel("Utilisation de la méthode : "));
		redim.add(choixMethode);
		
		int result = JOptionPane.showConfirmDialog(null, redim, 
		"Réduction", JOptionPane.OK_CANCEL_OPTION);//Fenetre 1 : Recupere taille souhaite.
		if (result == JOptionPane.OK_OPTION) {
			int reductionLargeur = 0;
			int reductionHauteur = 0;
			try {
				reductionLargeur = Integer.parseInt(reducLargeur.getText());
				reductionHauteur = Integer.parseInt(reducHauteur.getText());
				m.setMethode((String)choixMethode.getSelectedItem());
				m.reduction(reductionLargeur,reductionHauteur);
			} catch(NumberFormatException nfe) {
				//Les informations rentrees dans la zone de texte ne sont pas des nombres.
				JOptionPane.showMessageDialog(null, "Taille mal renseignée ou trop haute. Entrer des chiffres.","Erreur",JOptionPane.WARNING_MESSAGE);

			}//end catch		
		}//end if result
	}//end actionPerformed
}
