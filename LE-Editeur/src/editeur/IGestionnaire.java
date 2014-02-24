package editeur;

import java.util.HashMap;
import chargement.IPlugin;

public interface IGestionnaire extends IPlugin{
	
	
	public HashMap<String, String> listerPlugins();
	public void activerPlugin(String nomPlugin);
	public void desactiverPlugin(String nomPlugin);
}
