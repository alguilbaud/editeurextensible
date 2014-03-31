package afficheurComptage;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import editeur.IAfficheur;

public class AfficheurComptage implements IAfficheur{

	public String informationsPlugin() {
		return "Je suis un afficheur qui indique la taille et compte les mots.";
	}

	public JComponent creerJComponent(JTextArea textArea) {
		JTextArea infos = new JTextArea("Il y a 0 mots pour une taille totale de 0 caractères.");
		
		return infos;
	}

	public String getPosition() {
		return "South";
	}

}
