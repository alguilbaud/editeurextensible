package chargement;

import interfaces.IPlugin;
import interfaces.IPluginApp;

import java.io.*;
import java.util.*;
import java.lang.Class;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * La classe Loader 
 * @author Benjamin Grouhan, Alexis Guilbaud, Kevin Mokili
 *
 */
public class Loader {
	/**
	 * Relie le nom de plugin avecson type.
	 */
	private HashMap<String,String> mapNomsTypes = new HashMap<String,String>();
	/**
	 * Relie le nom du plugin avec sa location (pour l’instancier).
	 */
	private HashMap<String,String> mapNomsLocations = new HashMap<String,String>();
	/**
	 * Relie le nom du plugin avec l’instance du plugin (singleton).
	 */
	private HashMap<String, IPlugin> mapPlugins = new HashMap<String, IPlugin>();
	/**
	 * Pour instancier les plugins à partir du ClassPath
	 */
	private URLClassLoader cl;
	
	/**
	 * Méthode qui va parser le fichier "listePlugins.txt" (voir la documentation de la plateforme pour plus d'informations sur ce fichier) :
	 * <ul>
	 * <li> Une fois pour créer l’URLClassLoader avec les ClassPath ainsi que les 2 premières HashMap.</li>
	 * <li> Une seconde fois pour instancier les plugins dont ChargementAuto est
	 * à 1 (en les stockant dans mapPlugins) et également appeler la méthode
	 * démarrer sur ceux qui sont de type application.</li>
	 * </ul>
	 * 	 * @throws Exception
	 */
	public void loadAuto() throws Exception{
		BufferedReader br = null;
		String ligne = "";
		String filePath = new File(".").getCanonicalFile().getParent() + "/LE-Plateforme/src/chargement/listePlugins.txt" ;
		br = new BufferedReader(new FileReader(filePath));
		ArrayList<URL> urls = new ArrayList<URL>();	
		String canonicalFilePath = "file://" + new File(".").getCanonicalFile().getParent();
		//premier passage pour construire l'URLClassLoader, ainsi que les HashMap mapNomsTypes et mapNomsLocations
		while((ligne = br.readLine()) != null){
			if(!ligne.equals("") && ligne.charAt(0)!='#'){
				String[] args = ligne.split(",");
				mapNomsTypes.put(args[0], args[3]);
				mapNomsLocations.put(args[0], args[1]);
				URL url = new URL(canonicalFilePath + args[2]);
				urls.add(url);
			}
		}
		
		br.close();
		URL[] tableau_url = new URL[urls.size()];
		cl = new URLClassLoader(urls.toArray(tableau_url));
		//deuxième passage pour charger les plugins de démarrage
		br = new BufferedReader(new FileReader(filePath));
		while ((ligne = br.readLine()) != null){
			if(!ligne.equals("") && ligne.charAt(0)!='#'){
				String[] args = ligne.split(",");
				if (args[4].equals("1")){
					String nomPlugin = args[0];
					IPlugin plug = loadPlugin(nomPlugin);
					if(args[3].equals("application")){
						IPluginApp plugApp = (IPluginApp) plug;
						plugApp.demarrer(this);
					}					
				}
			}
		}
		br.close();
	}
	
	/**
	 * Méthode qui permet de retourner l'instance d'un plugin à partir de son nom.
	 * <p> Si le plugin est déja instancié, il sera simplement obtenu à partir de la HashMap mapPlugins.
	 * Sinon, il sera instancié grâce au ClassLoader créé précédement.</p>
	 * @param nom Le nom du plugin
	 * @return L'instance du plugin correspondant
	 * @see Loader#mapPlugins
	 * @see Loader#cl
	 * @throws Exception
	 */
	public IPlugin loadPlugin(String nom) throws Exception{
		IPlugin plug;
		if(mapPlugins.containsKey(nom)){
			plug = mapPlugins.get(nom);
		}
		else{
			String location = mapNomsLocations.get(nom);
			Class<?> classe = Class.forName(location, true, cl);
			plug = (IPlugin) classe.newInstance();
			mapPlugins.put(nom,plug);
			System.out.println("Chargement du plugin "+nom);
		}
		return plug;
	}
	
	/**
	 * Retourne l'ensemble des noms des plugins d'un certain type (application, modificateur, etc.).
	 * @param type Le type souhaité sous forme de String. Valeurs acceptées : 
	 * <ul>
	 * <li> application </li>
	 * <li> afficheur </li>
	 * <li> chargeur </li>
	 * <li> modificateur</li>
	 * </ul>
	 * @return Un ArrayList comprenant l'ensemble des noms des plugins du type entré en paramètre.
	 */
	public ArrayList<String> getNomsPlugins(String type){
		ArrayList<String> noms = new ArrayList<String>();
		for (String n : mapNomsTypes.keySet()){
			if(mapNomsTypes.get(n).equals(type)){
				noms.add(n);
			}
		}
		return noms;
	}
}
