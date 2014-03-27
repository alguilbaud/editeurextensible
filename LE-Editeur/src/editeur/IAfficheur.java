package editeur;

import javax.swing.JComponent;

import chargement.IPluginTerminal;

public interface IAfficheur extends IPluginTerminal{
	public void afficher(JComponent c);
	
}
