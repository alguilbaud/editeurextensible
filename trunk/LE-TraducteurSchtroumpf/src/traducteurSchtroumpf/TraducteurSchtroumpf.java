package traducteurSchtroumpf;

import interfaces.IModificateur;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * La classe TraducteurSchtroumpf est un exemple de plugin de type "Modificateur", permettant de traduire un texte 
 * en "Schtroumpf", c'est à dire de remplacer certains mots par "schtroumpf" de manière aléatoire.
 * @author Benjamin Grouhan, Alexis Guilbaud, Kevin Mokili
 * @see IModificateur
 */
public class TraducteurSchtroumpf implements IModificateur{
	
	/**
	 * Implémentation de la méthode modifier(String) de l'interface IModificateur, remplaçant certains mots du
	 * texte passé en paramètres par le mot "schtroumpf" de manière aléatoire.
	 * @see IModificateur#modifier(String)
	 */
	public String modifier(String texte){
		Pattern p = Pattern.compile("\\w+");
	    Matcher m = p.matcher(texte);
	    StringBuffer sb = new StringBuffer();
	    Random rand = new Random();
	    int r = rand.nextInt(10);
	    int i = 0;
	    while(m.find()){
	    	if(i==r){
	    		m.appendReplacement(sb,"schtroumpf");
	    		i = 0;
	    		r = rand.nextInt(10);
	    	}
	    	else{
	    		i++;
	    	}    
	    }
	    m.appendTail(sb);
	    return sb.toString();
	}
}
