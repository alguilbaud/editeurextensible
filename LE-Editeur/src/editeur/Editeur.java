package editeur;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import chargement.IPlugin;

public class Editeur implements IPlugin{
	IAfficheur aff = null;
	private JFrame fenetre;
	
	public Editeur(){
		texte = "";
		pressePapier = "";
		debutCurseur = 0;
		longueurSelection = 0;
		
		
	}
	
	private String texte = "";
	private String pressePapier = ""; //stocke ce qui est copié
	private int debutCurseur = 0; //correspond à l'emplacement du curseur ou à l'emplacement du début de la sélection s'il y en a une
	private int longueurSelection = 0;
	
	
	public String informationsPlugin(){
		return "Je suis un éditeur de texte.";
	}
	
	public void demarrer(){
		//TODO : à faire
		afficher();
	}
	
	public void afficher(){
		if (aff!=null){
			aff.afficher(texte);
		}
		else{
			//TODO : affichage graphique standard à faire
			fenetre = new JFrame("Sans titre");
			init();
			fenetre.setVisible(true);
		}
	}
	
	public void positionnerCurseur(int pos){
		if(pos<0){
			debutCurseur = 0;
		}
		else if(pos>texte.length()){
			debutCurseur = texte.length();
		}
		else{
			debutCurseur = pos;
		}
		longueurSelection = 0;
	}
	
	public void ecrire(String chaine){ //insère la chaîne à l'emplacement du curseur, ou à la place de la sélection, s'il y en a une (= efface la sélection puis insère)
		String part1 = texte.substring(0,debutCurseur);
		String part2 = texte.substring(debutCurseur+longueurSelection);
		texte = part1 + chaine + part2;
		debutCurseur = debutCurseur + chaine.length();
		longueurSelection = 0;
	}
	
	public void effacer(){ //efface le caractère avant le curseur, ou la sélection s'il y en a une
		if(longueurSelection>0){
			texte = texte.substring(0,debutCurseur) + texte.substring(debutCurseur + longueurSelection);
			longueurSelection = 0;
		}
		else{
			texte = texte.substring(0,debutCurseur-1) + texte.substring(debutCurseur);
		}
		
	}
	
	public void copier(){ //met dans le presse papier ce qui est sélectionné
		if(longueurSelection>0){
			pressePapier = texte.substring(debutCurseur,debutCurseur+longueurSelection);
		}
	}
	
	public void couper(){ //met dans le presse papier ce qui est sélectionné puis efface la sélection
		copier();
		effacer();
	}
	
	public void coller(){ //insère le contenu du presse papier à l'emplacement du curseur, ou à la place de la sélection, s'il y en a une (= efface la sélection puis insère)
		if(pressePapier.length()>0){
			ecrire(pressePapier);
		}
	}
	
	public void selectionner(int debut, int fin){
		if(debut<0){
			debutCurseur = 0;
		}
		else{
			debutCurseur = debut;
		}
		if(fin>texte.length()){
			longueurSelection = texte.length() - debutCurseur;
		}
		else{
			longueurSelection = fin - debutCurseur;
		}
	}
	
	
	private void init()
	{
		fenetre.setSize(400,200); //On donne une taille à notre fenêtre
		fenetre.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		fenetre.setResizable(false); //On interdit la redimensionnement de la fenêtre
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBackground(Color.white);
		
		JTextArea textArea = new JTextArea("abcdefghi");
		panel.add(textArea);
		
		JPanel b1 = new JPanel();
		JButton bouton = new JButton("Mon bouton");
		b1.add(bouton);
		
		JPanel b2 = new JPanel();
		b2.setLayout(new BoxLayout(b2, BoxLayout.PAGE_AXIS));
		b2.add(b1);
		b2.add(panel);
		
		fenetre.setContentPane(b2);
	}
}
