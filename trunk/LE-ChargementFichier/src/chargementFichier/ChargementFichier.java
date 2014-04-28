package chargementFichier;

import interfaces.IChargeur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JPanel;


public class ChargementFichier implements IChargeur{
	
	public String informationsPlugin(){
		return "Je suis un chargeur de données à partir d'un fichier.";
	}

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
	
	
	/*public static void main(String[] args){
		ChargementFichier cf = new ChargementFichier();
		System.out.println(cf.recupererDonnees("/comptes/E096489E/Documents/Lorem.txt"));
	}*/
}
