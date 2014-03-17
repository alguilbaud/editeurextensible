package editeur;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import chargement.IPluginApp;
import chargement.IPluginTerminal;
import chargement.Loader;

public class Editeur implements IPluginApp{
	IAfficheur aff = null;
	private JFrame fenetre;
	private String texte = "";
	private String pressePapier = ""; //stocke ce qui est copié
	private int debutCurseur = 0; //correspond à l'emplacement du curseur ou à l'emplacement du début de la sélection s'il y en a une
	private int longueurSelection = 0;
	private Loader loader;
	
	
	public Editeur(){
		texte = "";
		pressePapier = "";
		debutCurseur = 0;
		longueurSelection = 0;
	}
	
	public String informationsPlugin(){
		return "Je suis un éditeur de texte.";
	}
	
	public void demarrer(Loader l){
		loader = l;
		afficher();
		
	}
	
	public void afficher(){
		if (aff!=null){
			aff.afficher(texte);
		}
		else{
			//TODO : affichage graphique standard à faire
			fenetre = new JFrame("Editeur");
			creationFenetre();
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
	
	/**
	 * TODO Si possible, casser la méthodes en plusieurs méthodes plus courtes !
	 */
	private void creationFenetre()
	{
		fenetre.setSize(400,200); //On donne une taille à notre fenêtre
		fenetre.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		fenetre.setResizable(false); //On interdit la redimensionnement de la fenêtre
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JMenuBar menubar = new JMenuBar();
		JMenu menu1 = new JMenu("Fichier");
		JMenu menu2 = new JMenu("Edition");
		
		JMenuItem nouveau = new JMenuItem("Nouveau");
		JMenuItem ouvrir = new JMenuItem("Ouvrir");
		JMenuItem quitter = new JMenuItem("Quitter");
		JMenuItem copier = new JMenuItem("Copier");
		JMenuItem coller = new JMenuItem("Coller");
		
		JTextArea textArea = new JTextArea();
		
		JPanel panel = new JPanel();
		fenetre.getContentPane().add(panel,"North");
				/* Ajouter les choix au menu  */
		menu1.add(nouveau);
		menu1.add(ouvrir);
		menu1.add(quitter);
		menu2.add(copier);
		menu2.add(coller);
				/* Ajouter les menu sur la bar de menu */
		menubar.add(menu1);
		menubar.add(menu2);
				/* Ajouter la bar du menu à la frame */
		fenetre.setJMenuBar(menubar);
		
		JPanel textAreaPane = new JPanel();
		textAreaPane.setLayout(new BorderLayout());
		textAreaPane.add(textArea, "Center");
		
		JPanel buttonPane = new JPanel();
		JButton boutonCopier = new JButton("Copier");
		JButton boutonCouper = new JButton("Couper");
		JButton boutonColler = new JButton("Coller");
		JButton boutonEffacer = new JButton("Effacer");
		
		buttonPane.add(boutonCopier);
		buttonPane.add(boutonColler);
		buttonPane.add(boutonCouper);
		buttonPane.add(boutonEffacer);
		
		JPanel globalPane = new JPanel();
		globalPane.setLayout(new BorderLayout());
		globalPane.add(textAreaPane, BorderLayout.CENTER);
		globalPane.add(buttonPane, BorderLayout.NORTH);
		
		creationBoutonsPlugins(globalPane);
		
		fenetre.getContentPane().add(globalPane);
		
	}
	
	private void creationBoutonsPlugins(JPanel panel)
	{
		//crée tous les boutons en se basant sur la hashMap des fonctionnalités des plugins
		JPanel pluginsPane = new JPanel();
		creationBoutonsModificateurs(pluginsPane);
		creationBoutonsChargeurs(pluginsPane);
		creationBoutonsAfficheurs(pluginsPane);
		panel.add(pluginsPane, BorderLayout.SOUTH);
	}
	
	private void creationBoutonsAfficheurs(JPanel panel){
		ArrayList<String> afficheurs = loader.getNomsPlugins("afficheur");
		for(String nomAff : afficheurs){
			panel.add(new JButton(nomAff));
		}
	}
	
	private void creationBoutonsModificateurs(JPanel panel){
		ArrayList<String> modificateurs = loader.getNomsPlugins("modificateur");
		for(String nomMod : modificateurs){
			panel.add(new JButton(nomMod));
		}
	}
	
	private void creationBoutonsChargeurs(JPanel panel){
		ArrayList<String> chargeurs = loader.getNomsPlugins("chargeur");
		for(String nomCharg : chargeurs){
			panel.add(new JButton(nomCharg));
		}
	}
	
	
	
	
	private void ajouterPlugin(IPluginTerminal plug){
		//méthode appelée par le gestionnaire pour rajouter un plugin
		//rajoute le plugin dans la Hashmap contenant les plugins
		//fait un appel à la méthode ajouterFonctionnalites(plug)
		//fermer la fenêtre et la relancer afficher
	}
	
}
