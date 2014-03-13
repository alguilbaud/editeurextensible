package editeur;

import chargement.IPluginTerminal;

public interface IModificateur extends IPluginTerminal{
	public String modifier(String texte);

}
