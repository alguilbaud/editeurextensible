package editeur;

import chargement.IPlugin;

public interface IModificateur extends IPlugin{
	public String modifier(String texte);

}
