package afficheurComptage;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import editeur.IAfficheur;

public class AfficheurComptage implements IAfficheur{

	public String informationsPlugin() {
		return "Je suis un afficheur qui indique la taille et compte les mots.";
	}

	public JComponent creerJComponent(JTextArea textArea) {
		return null;
	}

	public String getPosition() {
		return "South";
	}

}
