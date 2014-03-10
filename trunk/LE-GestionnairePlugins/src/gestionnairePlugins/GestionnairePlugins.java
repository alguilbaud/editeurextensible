package gestionnairePlugins;

import chargement.IPlugin;

public class GestionnairePlugins implements IPlugin{
	
	public String informationsPlugin(){
		return "Je suis un gestionnaire de plugins.";
	}
	
	public void demarrer(){
		//ne fait rien
	}
	
	//pour l'instant osef du gestionnaire, normalement c'est juste un affichage graphique, toutes les actions se font avec le loader
	
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
