package afficheurNbLignes;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import editeur.IAfficheur;

public class AfficheurNbLignes implements IAfficheur{
	
	public String informationsPlugin(){
		return "Je suis un afficheur avec numéros de lignes.";
	}

	public JComponent creerJComponent(JTextArea textArea) {
		
		return null;
	}

	public String getPosition(){
		return "West";
	}
}
