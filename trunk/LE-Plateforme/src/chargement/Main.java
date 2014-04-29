package chargement;


/**
 * Classe principale permettant de créer une instance du Loader et de lancer les plugins de type "Application"
 * ainsi que ceux paramétrés pour être lancés automatiquement.
 * @author Benjamin Grouhan, Alexis Guilbaud, Kevin Mokili
 */
public class Main {
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
