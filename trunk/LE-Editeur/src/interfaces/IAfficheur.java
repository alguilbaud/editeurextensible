package interfaces;

import javax.swing.JComponent;
import javax.swing.JTextArea;

/**
 * L'interface IAfficheur permet de représenter les plugins de type "Afficheur", c'est à dire ceux chargés d'ajouter de l'information 
 * sur l'application dans un composant graphique.
 * @author Benjamin Grouhan, Alexis Guilbaud, Kevin Mokili
 */
public interface IAfficheur extends IPluginTerminal{
	/**
	 * Méthode qui devra créer le JComponent contenant les informations à afficher.
	 * @param textArea La zone de texte sur laquelle il est possible de récupérer des informations
	 * @return Le JComponent créé
	 */
	public JComponent creerJComponent(JTextArea textArea);
	
	/**
	 * Méthode qui devra retourner, sous forme de String, la position sur laquelle le composant graphique doit être placé sur l'application.
	 * @return Une String avec pour valeurs possibles : 
	 * <ul>
	 * <li> North </li>
	 * <li> East </li>
	 * <li> South </li>
	 * <li> West </li>
	 * </ul>
	 * @return
	 */
	public String getPosition();
	
}
