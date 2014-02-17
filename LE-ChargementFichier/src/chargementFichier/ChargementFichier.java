package chargementFichier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import main.IChargeur;

public class ChargementFichier implements IChargeur{

	public String recupererDonnees(String param) {
		String res = "";
		String ligne = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(param));
			while ((ligne = br.readLine()) != null){
				 res = res.concat("\n"+ligne);
			}
			br.close();
			res = res.substring(1);
		} catch (IOException e) {
			System.out.println("Fichier non trouvé.");
		}
		return res;
	}
	
	public static void main(String[] args){
		ChargementFichier cf = new ChargementFichier();
		System.out.println(cf.recupererDonnees("/comptes/E096489E/Documents/Lorem.txt"));
	}
}
