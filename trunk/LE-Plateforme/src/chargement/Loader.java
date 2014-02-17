package chargement;

import java.io.*;
import java.util.*;
import java.lang.Class;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import main.IAfficheur;
import main.IChargeur;
import main.IGestionnaire;
import main.IModificateur;


public class Loader {
	HashMap<String, IAfficheur> mapAfficheurs = new HashMap<String, IAfficheur>(); // pour le singleton
	
	/*
	 * 
	 */
	private Object chargeBean(String nomFichier) throws IOException,FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		// charge un bean de type personne, en l'instanciant pour un nom de fichier donné
		HashMap <String, String> hm = new HashMap<String, String>();
		BufferedReader br = null;
		String ligne = "";
		boolean trouve = false;
		String nomClasse = "";
		                
		br = new BufferedReader(new FileReader("/comptes/E096489E/workspace/LogicielsExtensibles/src/codeTiers/" + nomFichier));
		while ((ligne = br.readLine()) != null){
			String[] args = ligne.split("=");
			if (args[0].equals("class")){
				trouve = true;
				nomClasse = args[1];
			}
			else{
				hm.put(args[0],args[1]);
			}
		}
		br.close();
		
		if (trouve){
			Class<?> classe = Class.forName(nomClasse);
			Object inst = classe.newInstance();
			Method[] methodes = classe.getMethods();
			
			for(Map.Entry<String, String> ens : hm.entrySet()){
				Method m = recupererMethode(methodes, ens.getKey());
				Class<?>[] typesParametres = m.getParameterTypes();
				Class<?> type = typesParametres[0];
				if(type.toString().equals("int")){
					int i = Integer.parseInt(ens.getValue());
					m.invoke(inst, i);
				}
				else{
					m.invoke(inst, ens.getValue());
				}
			}
			return inst;
		}
		return null;
	}
	
	/*
	 * Retourne le setteur correspondant
	 */
	private Method recupererMethode(Method[] tabm, String nomVariable){
		String temp = nomVariable.substring(0,1);
		temp = temp.toUpperCase();
		String nomMethode = "set" + temp + nomVariable.substring(1); //nomVariable contient maintenant le nom du setteur associé à la variable
		for(Method m : tabm){
			if (m.getName().equals(nomMethode)){
				return m;
			}
		}
		return null;
	}
	
	/*
	 * Instancie tous les objets spécifiés dans le code tiers
	 */
	public List<Object> loadData() throws FileNotFoundException, IllegalArgumentException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException{
		List<Object> listeObjets = new ArrayList<Object>();
		File rep = new File("/comptes/E096489E/workspace/LogicielsExtensibles/src/codeTiers");
		File[] fichiersTxt = rep.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		for(File f : fichiersTxt){
			listeObjets.add(chargeBean(f.getName()));
		}
		return listeObjets;
	}
	
	/*
	 * Retourne un objet de type IAfficheur de manière paresseuse si spécifié
	 */
	public IAfficheur loadAfficheur(String nom) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		if(mapAfficheurs.containsKey(nom)){
			return mapAfficheurs.get(nom);
		}
		else{
			BufferedReader br = null;
			String ligne = "";
			br = new BufferedReader(new FileReader("/comptes/E096489E/workspace/LogicielsExtensibles/src/main/afficheurs.txt"));
			String nomClasse="";
			while ((ligne = br.readLine()) != null){
				String[] args = ligne.split("=");
				if (args[0].equals(nom)){
					args = args[1].split(" ");
					/*if(args.length!=1){//charger paresseusement avec un proxy
						nomClasse = args[0];
						ProxyAfficheur instPAffiche = new ProxyAfficheur(nomClasse);
						mapAfficheurs.put(nom,instPAffiche);
						return instPAffiche;
					}
					else{*/ // instancier directement l'object afficheur
					nomClasse = args[0];
					Class<?> classe = Class.forName(nomClasse);
					IAfficheur instAffiche = (IAfficheur) classe.newInstance();
					mapAfficheurs.put(nom,instAffiche);
					return instAffiche;
					//}
				}
			}
		}
		return null;
	}
	
	/*
	 * Retourne la liste des afficheurs contenus dans le fichier afficheurs.txt sous forme de String
	 */
	public List<String> listeDAfficheurs() throws IOException{
		List<String> listeNoms = new ArrayList<String>();
		BufferedReader br = null;
		String ligne = "";
		br = new BufferedReader(new FileReader("/comptes/E096489E/workspace/LogicielsExtensibles/src/main/afficheurs.txt"));
		while ((ligne = br.readLine()) != null){
			String[] args = ligne.split("=");
			listeNoms.add(args[0]);
		}
		return listeNoms;
	}
}
