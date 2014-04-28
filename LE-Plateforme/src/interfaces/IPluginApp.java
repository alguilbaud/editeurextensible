package interfaces;

import chargement.Loader;

/**
 * L'interface IPluginApp qui étend IPlugin, doit être implémentée par les plugins de type "Application".
 * @author Benjamin Grouhan, Alexis Guilbaud, Kevin Mokili
 */
public interface IPluginApp extends IPlugin {
	
	/**
	 * Méthode permettant de démarrer l'application.
	 * @param l Instance du Loader utiliséé pour le lancement.
	 */
	public void demarrer(Loader l);
}
