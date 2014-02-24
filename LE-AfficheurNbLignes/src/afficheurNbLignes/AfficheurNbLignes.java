package afficheurNbLignes;

import main.IAfficheur;

public class AfficheurNbLignes implements IAfficheur{

	public void afficher(String texte) {
		String[] lignes = texte.split("\n");
		for(int i=0; i<lignes.length;i++){
			System.out.println((i+1) + " " + lignes[i]);
		}
	}

	
	/*public static void main(String[] args){
		AfficheurNbLignes aff = new AfficheurNbLignes();
		aff.afficher("bla\nblabla\nblablabla\nc fini");
	}*/
}
