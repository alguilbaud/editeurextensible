package editeur;

import chargement.Loader;
import editeur.Editeur;

public class Main {

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		Loader l = new Loader();
		l.loadAuto();
//		Editeur ed = new Editeur();
		
//		ed.demarrer();
		
		/*ed.ecrire("abcdefghi");
		
		ed.afficher();
		
		ed.selectionner(0,3);
		ed.copier();
		ed.positionnerCurseur(9);
		ed.coller();
		
		ed.afficher();
		
		ed.selectionner(3,6);
		ed.copier();
		ed.selectionner(9,12);
		ed.coller();
		
		ed.afficher();
		
		ed.effacer();
		
		ed.afficher();
		
		ed.selectionner(0,9);
		ed.couper();
		
		ed.afficher();
		
		ed.positionnerCurseur(2);
		ed.coller();
		
		ed.afficher();
		
		ed.selectionner(9,11);
		ed.effacer();
		
		ed.afficher();*/
	}

}
