package gestionnairePlugins;

import chargement.IPluginTerminal;

public class GestionnairePlugins implements IPluginTerminal{
	
	public String informationsPlugin(){
		return "Je suis un gestionnaire de plugins.";
	}
	
	
	//pour l'instant osef du gestionnaire, normalement c'est juste un affichage graphique, toutes les actions se font avec le loader
	
	public String nomBouton() {
		return "Gérer Plugins";
	}
	
	/*
	@Override
	public HashMap<String, String> listerPlugins() {
		
		return null;
	}

	@Override
	public void activerPlugin(String nomPlugin) {
		
	}

	@Override
	public void desactiverPlugin(String nomPlugin) {
		
	}
	
	*/

}
