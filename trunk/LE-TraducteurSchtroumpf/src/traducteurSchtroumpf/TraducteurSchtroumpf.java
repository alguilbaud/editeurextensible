package traducteurSchtroumpf;

import editeur.IModificateur;

public class TraducteurSchtroumpf implements IModificateur{
	
	public String informationsPlugin(){
		return "Je suis un traducteur en Schtroumpf.";
	}
	
	public String modifier(String texte){
		String[] mots = texte.split(" ");
		int i = (int) Math.round(Math.random()*5+1);
		while (i<mots.length){
			//à améliorer pour garder les \n et autres ponctuations
			mots[i] = "schtroumpf";
			i += (int) Math.round(Math.random()*5+1);
		}
		String res = "";
		for(int j=0;j<mots.length;j++ ){
			res = res.concat(mots[j]+" ");
		}
		
		return res;
	}

	public String nomBouton() {
		return "Traduire en Schtroumpf";
	}
	
	/*public static void main(String[] args){
		TraducteurSchtroumpf ts = new  TraducteurSchtroumpf();
		String texte = "Coucou, ceci est un test pour le plugin traducteur. \n" +
				"J'espère que tout va bien marcher, parce que quand même, traduire, c'est trop génial.\n" +
				"Ah oui, au fait, hier j'ai mangé une pomme et elle était bonne.";
		texte = ts.modifier(texte);
		System.out.println(texte);
	}*/
}
