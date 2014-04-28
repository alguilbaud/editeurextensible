package interfaces;

/**
 * L'interface IChargeur permet de représenter les plugins de type "Chargement", c'est à dire qui peuvent ajouter
 * du texte dans l’éditeur, sans modifier le texte déjà présent.
 * @author Benjamin Grouhan, Alexis Guilbaud, Kevin Mokili
 */
public interface IChargeur extends IPluginTerminal{
	/**
	 * Méthode qui devra retourner le texte récupéré.
	 * @return Le texte chargé.
	 */
	public String recupererDonnees();
	
}
