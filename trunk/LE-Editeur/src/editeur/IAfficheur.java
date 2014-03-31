package editeur;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import chargement.IPluginTerminal;

public interface IAfficheur extends IPluginTerminal{
	public JComponent creerJComponent(JTextArea textArea);
	
	public String getPosition();
	
}
