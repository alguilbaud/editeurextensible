package interfaces;

import javax.swing.JComponent;
import javax.swing.JTextArea;


public interface IAfficheur extends IPluginTerminal{
	public JComponent creerJComponent(JTextArea textArea);
	
	public String getPosition();
	
}
