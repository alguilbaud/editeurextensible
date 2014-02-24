package editeur;

import chargement.IPlugin;

public interface IChargeur extends IPlugin{
	public String recupererDonnees(String param);
	
}
