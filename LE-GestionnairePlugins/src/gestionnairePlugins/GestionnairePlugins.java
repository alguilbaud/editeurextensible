package gestionnairePlugins;

import java.util.HashMap;

import editeur.IGestionnaire;

public class GestionnairePlugins implements IGestionnaire{
	
	public String informationsPlugin(){
		return "Je suis un gestionnaire de plugins.";
	}

	@Override
	public HashMap<String, String> listerPlugins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void activerPlugin(String nomPlugin) {
		
	}

	@Override
	public void desactiverPlugin(String nomPlugin) {
		// TODO Auto-generated method stub
		
	}

}
