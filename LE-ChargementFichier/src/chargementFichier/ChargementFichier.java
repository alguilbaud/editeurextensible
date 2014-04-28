package chargementFichier;

import interfaces.IChargeur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * La classe ChargementFichier est un exemple de plugin de type "Chargement", 
 * qui permet d'ajouter du texte dans l'éditeur à partir d'un fichier. 
 * @author Benjamin Grouhan, Alexis Guilbaud, Kevin Mokili
 * @see IChargeur
 */
public class ChargementFichier implements IChargeur{

	/**
	 * Implémentation de la méthode recupererDonnees() de l'interface IChargeur.
	 * <p> Va tout d'abord proposer d'ouvrir un fichier à l'aide d'un JFileChooser 
	 * puis va charger le texte contenu dans le fichier pour le retourner sous forme de String.</p>
	 * @see IChargeur#recupererDonnees()
	 */
	public String recupererDonnees() {
		
		JFileChooser jfc = new JFileChooser();
		JPanel panelParcourir = new JPanel();
		int retour = jfc.showOpenDialog(panelParcourir);
		String cheminFichier = "";
		if(retour==JFileChooser.APPROVE_OPTION){
			// un fichier a été choisi (sortie par OK)
			// nom du fichier  choisi 
			jfc.getSelectedFile().getName();
			// chemin absolu du fichier choisi
			cheminFichier = jfc.getSelectedFile().getAbsolutePath();
		}
		String res = "";
		String ligne = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(cheminFichier));
			while ((ligne = br.readLine()) != null){
				 res = res.concat("\n"+ligne);
			}
			br.close();
			if(!res.equals("")){
				res = res.substring(1);
			}
		} catch (IOException e) {
			System.out.println("Fichier non trouvé.");
		}
		return res;
	}
}
