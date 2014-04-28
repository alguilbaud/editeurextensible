package interfaces;

/**
 * L'interface IModificateur permet de représenter les plugins de type "Modificateur", c'est à dire permettant de modifier
 * le contenu du texte déjà écrit dans l’éditeur.
 * @author Benjamin Grouhan, Alexis Guilbaud, Kevin Mokili
 */
public interface IModificateur extends IPluginTerminal{
	/**
	 * Permet de modifier le texte entré en paramètres.
	 * @param texte Le texte à modifier
	 * @return Le texte une fois modifié
	 */
	public String modifier(String texte);

}
