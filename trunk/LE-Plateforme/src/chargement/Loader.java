package chargement;

import java.io.*;
import java.util.*;
import java.lang.Class;
import java.net.URL;
import java.net.URLClassLoader;


public class Loader {
	private HashMap<String,String> mapNomsTypes = new HashMap<String,String>();
	private HashMap<String, IPlugin> mapPlugins = new HashMap<String, IPlugin>(); // pour le singleton
	private static final String FILE_PATH = "/comptes/E096489E/workspace/LE-Plateforme/src/chargement/listePlugins.txt";
	private URLClassLoader cl;
	
	public void loadAuto() throws Throwable{
		BufferedReader br = null;
		String ligne = "";
		br = new BufferedReader(new FileReader(FILE_PATH));
		ArrayList<URL> urls = new ArrayList<URL>();
		//premier passage pour construire l'URLClassLoader
		while((ligne = br.readLine()) != null){
			if(ligne.charAt(0)!='#'){
				String[] args = ligne.split(",");
				
				URL url = new URL("file:///comptes/E096489E/workspace" + args[2]);
				urls.add(url);
			}
		}
		
		br.close();
		URL[] tableau_url = new URL[urls.size()];
		cl = new URLClassLoader(urls.toArray(tableau_url));
		//deuxième passage pour charger les plugins de démarrage
		br = new BufferedReader(new FileReader(FILE_PATH));
		while ((ligne = br.readLine()) != null){
			if(ligne.charAt(0)!='#'){
				String[] args = ligne.split(",");
				mapNomsTypes.put(args[0], args[3]);
				if (args[4].equals("1")){
					//String typePlugin = args[3];
					String nomPlugin = args[1];
					IPlugin plug = loadPlugin(nomPlugin);
					System.out.println(plug.informationsPlugin());
					
					/*else{
						System.out.println("Le fichier des plugins est mal paramétré");
						throw new Exception();
					}*/
									
				}
			}
		}
		br.close();
	}
	
	private IPlugin loadPlugin(String nom) throws Throwable{
		if(mapPlugins.containsKey(nom)){
			return mapPlugins.get(nom);
		}
		else{
			Class<?> classe = Class.forName(nom, true, cl);
			IPlugin instPlugin = (IPlugin) classe.newInstance();
			mapPlugins.put(nom,instPlugin);
			return instPlugin;	
		}
	}
	
	private ArrayList<String> getNomsPlugins(String type){
		ArrayList<String> noms = new ArrayList<String>();
		for (String n : mapNomsTypes.keySet()){
			if(mapNomsTypes.get(n).equals(type)){
				noms.add(n);
			}
		}
		return noms;
	}
	
}
