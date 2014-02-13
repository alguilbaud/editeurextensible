package main;

import java.util.HashMap;

public interface IGestionnaire {
	
	
	public HashMap<String, String> listerPlugins();
	public void activerPlugin(String nomPlugin);
	public void desactiverPlugin(String nomPlugin);
}
