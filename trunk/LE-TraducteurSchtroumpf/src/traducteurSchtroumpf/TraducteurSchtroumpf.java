package traducteurSchtroumpf;

import interfaces.IModificateur;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TraducteurSchtroumpf implements IModificateur{
	
	public String modifier(String texte){
		Pattern p = Pattern.compile("\\w+");
	    Matcher m = p.matcher(texte);
	    StringBuffer sb = new StringBuffer();
	    Random rand = new Random();
	    int r = rand.nextInt(5);
	    int i = 0;
	    while(m.find()){
	    	if(i==r){
	    		m.appendReplacement(sb,"schtroumpf");
	    		i = 0;
	    		r = rand.nextInt(5);
	    	}
	    	else{
	    		i++;
	    	}    
	    }
	    m.appendTail(sb);
	    return sb.toString();
	}
}
