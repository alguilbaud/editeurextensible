package afficheurNbLignes;

import java.util.HashMap;

import editeur.IAfficheur;

public class AfficheurNbLignes implements IAfficheur{
	
	public String informationsPlugin(){
		return "Je suis un afficheur avec numéros de lignes.";
	}

	public void afficher(String texte) {
		/*String[] lignes = texte.split("\n");
		for(int i=0; i<lignes.length;i++){
			System.out.println((i+1) + " " + lignes[i]);
		}*/
	}

	public String nomBouton() {
		return "Afficher numéros lignes";
	}

	
	/*public static void main(String[] args){
		AfficheurNbLignes aff = new AfficheurNbLignes();
		aff.afficher("bla\nblabla\nblablabla\nc fini");
	}*/
}
