package gestionnairePlugins;

import java.util.HashMap;

import editeur.IGestionnaire;

public class GestionnairePlugins implements IGestionnaire{
	
	public String informationsPlugin(){
		return "Je suis un gestionnaire de plugins.";
	}

	@Override
	public HashMap<String, String> listerPlugins() {
		//TODO : aller chercher la mapNomsTypes du Loader
		return null;
	}

	@Override
	public void activerPlugin(String nomPlugin) {
		//TODO : activer un Plugin
	}

	@Override
	public void desactiverPlugin(String nomPlugin) {
		//TODO : désactiver un Plugin (vraiment nécessaire de pouvoir désactiver ?)
	}

}
