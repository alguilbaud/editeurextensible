package editeur;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

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
	private JTextArea textArea;
	
	
	public Editeur(){
		texte = "";
		pressePapier = "";
		debutCurseur = 0;
		longueurSelection = 0;
	}
	
	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
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
		textArea.setText(texte);
		textArea.setCaretPosition(debutCurseur); //fait rien, pourquoi ?
	}
	
	public void effacer(){ //efface le caractère avant le curseur, ou la sélection s'il y en a une
		if(longueurSelection>0){
			texte = texte.substring(0,debutCurseur) + texte.substring(debutCurseur + longueurSelection);
			longueurSelection = 0;
		}
		else if(debutCurseur>0){
			texte = texte.substring(0,debutCurseur-1) + texte.substring(debutCurseur);
		}
		System.out.println("Texte : "+texte);
		textArea.setText(texte);
		textArea.setCaretPosition(debutCurseur); //fait rien, pourquoi ?
	}
	
	public void copier(){ //met dans le presse papier ce qui est sélectionné
		if(longueurSelection>0){
			pressePapier = texte.substring(debutCurseur,debutCurseur+longueurSelection);
			System.out.println("Copié");
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
		if(fin < debut){
			int i = debut;
			debut = fin;
			fin = i;
		}
		if(debut<0){
			debutCurseur = 0;
		}
		else{
			debutCurseur = debut;
		}
		longueurSelection = fin - debutCurseur;
	}
	
	private void creationFenetre()
	{
		fenetre.setSize(1200,600); //On donne une taille à notre fenêtre
		fenetre.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		fenetre.setResizable(false); //On interdit la redimensionnement de la fenêtre
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menubar = new JMenuBar();
		
		creationMenu(menubar);
		
		fenetre.setJMenuBar(menubar);
		
		JPanel panel = new JPanel();
		fenetre.getContentPane().add(panel,"North");
		
		JPanel textAreaPane = new JPanel();
		textAreaPane.setLayout(new BorderLayout());
		creationTextArea(textAreaPane);
		
		JPanel buttonPane = new JPanel();
		creationBoutonsStandards(buttonPane);
		
		JPanel pluginsPane = new JPanel();
		creationBoutonsPlugins(pluginsPane);
		
		JPanel globalPane = new JPanel();
		globalPane.setLayout(new BorderLayout());
		globalPane.add(textAreaPane, BorderLayout.CENTER);
		globalPane.add(buttonPane, BorderLayout.NORTH);
		globalPane.add(pluginsPane, BorderLayout.SOUTH);
		
		fenetre.getContentPane().add(globalPane);
		
	}
	
	private void creationTextArea(JPanel panel){
		textArea = new JTextArea();
		
		textArea.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				selectionner(arg0.getDot(),arg0.getMark());
			}
		});
		textArea.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {
				String texte = textArea.getText();
				setTexte(texte);
			}
			public void keyTyped(KeyEvent arg0) {}
		});
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel.add(scrollPane, "Center");		
	}
	
	private void creationMenu(JMenuBar menubar){
		JMenuItem nouveau = new JMenuItem("Nouveau");
		JMenuItem ouvrir = new JMenuItem("Ouvrir");
		JMenuItem quitter = new JMenuItem("Quitter");
		JMenuItem copier = new JMenuItem("Copier");
		JMenuItem coller = new JMenuItem("Coller");
		
		JMenu menu1 = new JMenu("Fichier");
		JMenu menu2 = new JMenu("Edition");
		
		menu1.add(nouveau);
		menu1.add(ouvrir);
		menu1.add(quitter);
		menu2.add(copier);
		menu2.add(coller);
		
		menubar.add(menu1);
		menubar.add(menu2);
	}
	
	private void creationBoutonsStandards(JPanel panel){
		JButton boutonCopier = new JButton("Copier");
		boutonCopier.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				copier();
			} 
		});
		
		JButton boutonCouper = new JButton("Couper");
		boutonCouper.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				couper();
			} 
		});
		
		JButton boutonColler = new JButton("Coller");
		boutonColler.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				coller();
			} 
		});
		
		JButton boutonEffacer = new JButton("Effacer");
		boutonEffacer.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				effacer();
			} 
		});
		
		panel.add(boutonCopier);
		panel.add(boutonColler);
		panel.add(boutonCouper);
		panel.add(boutonEffacer);
		
		
	}
	
	private void creationBoutonsPlugins(JPanel panel)
	{
		creationBoutonsModificateurs(panel);
		creationBoutonsChargeurs(panel);
		creationBoutonsAfficheurs(panel);
	}
	
	private void creationBoutonsAfficheurs(JPanel panel){
		ArrayList<String> afficheurs = loader.getNomsPlugins("afficheur");
		for(String nomAff : afficheurs){
			final JButton jb = new JButton(nomAff);
			jb.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) { 	
					try {
						IAfficheur plug = (IAfficheur) loader.loadPlugin(jb.getText());
						// créer nouveau pannel contenant le nb de lignes
						plug.afficher(null);
					} catch (Throwable e1) {
						e1.printStackTrace();
					}
				} 
			});
			panel.add(jb);
		}
	}
	
	private void creationBoutonsModificateurs(JPanel panel){
		ArrayList<String> modificateurs = loader.getNomsPlugins("modificateur");
		for(String nomMod : modificateurs){
			final JButton jb = new JButton(nomMod);
			jb.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) { 	
					try {
						IModificateur plug = (IModificateur) loader.loadPlugin(jb.getText());
						String txt = plug.modifier(texte.substring(debutCurseur, debutCurseur+longueurSelection));
						if(txt!=null){
							ecrire(txt);
						}
					} catch (Throwable e1) {
						e1.printStackTrace();
					}
				} 
			});
			panel.add(jb);
		}
	}
	
	private void creationBoutonsChargeurs(JPanel panel){
		ArrayList<String> chargeurs = loader.getNomsPlugins("chargeur");
		for(String nomCharg : chargeurs){
			final JButton jb = new JButton(nomCharg);
			jb.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) { 	
					try {
						IChargeur plug = (IChargeur) loader.loadPlugin(jb.getText());
						JFileChooser jfc = new JFileChooser();
						JPanel panelParcourir = new JPanel();
						int retour = jfc.showOpenDialog(panelParcourir);
						String cheminFichier = "";
						if(retour==JFileChooser.APPROVE_OPTION){
							// un fichier a été choisi (sortie par OK)
							// nom du fichier  choisi 
							jfc.getSelectedFile().getName();
							// chemin absolu du fichier choisi
							cheminFichier = jfc.getSelectedFile().getAbsolutePath();
							System.out.println(cheminFichier);
						}
						ecrire(plug.recupererDonnees(cheminFichier));
					} catch (Throwable e1) {
						e1.printStackTrace();
					}
				} 
			});
			panel.add(jb);
		}
	}
	
	
	
	
	private void ajouterPlugin(IPluginTerminal plug){
		//méthode appelée par le gestionnaire pour rajouter un plugin
		//rajoute le plugin dans la Hashmap contenant les plugins
		//fait un appel à la méthode ajouterFonctionnalites(plug)
		//fermer la fenêtre et la relancer afficher
	}
	
}