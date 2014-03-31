package editeur;

import chargement.Loader;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Loader l = new Loader();
			l.loadAuto();
		}
		catch (Exception e){
			System.out.println("Une erreur est survenue.");
			e.printStackTrace();
		}
	}

}
