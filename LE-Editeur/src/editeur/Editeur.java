package editeur;

import interfaces.IAfficheur;
import interfaces.IChargeur;
import interfaces.IModificateur;
import interfaces.IPluginApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import chargement.Loader;

/**
 * La classe Editeur contient toutes les méthodes de base pour un éditeur de texte (copier, couper, coller, sélectionner...) 
 * ainsi que tout ce qui concerne son interface graphique.
 * 
 * @author Benjamin Grouhan, Alexis Guilbaud, Kevin Mokili
 *
 */

public class Editeur implements IPluginApp{
	private JFrame fenetre;
	private String texte = "";
	private String pressePapier = ""; //stocke ce qui est copié
	private int debutCurseur = 0; //correspond à l'emplacement du curseur ou à l'emplacement du début de la sélection s'il y en a une
	private int longueurSelection = 0;
	private Loader loader;
	private JTextArea textArea;
	private JPanel textAreaPane;
	
	/**
	 * Constructeur d'Editeur.
	 * <p> 
	 * A la construction d'un objet Editeur, le texte contenu ainsi que le presse-papier sont vides, le cuseur est à la position 0 et 
	 * la longueur de la sélection vaut 0.
	 * </p>
	 */
	public Editeur(){
		texte = "";
		pressePapier = "";
		debutCurseur = 0;
		longueurSelection = 0;
	}
	
	/**
	 * Retourne le texte contenu dans l'éditeur.
	 * @return Le texte écrit dans l'éditeur à cet instant.
	 */
	public String getTexte() {
		return texte;
	}

	/**
	 * Met à jour le texte contenu dans l'éditeur.
	 * @param texte Le nouveau texte.
	 */
	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	/**
	 * Démarre l'application de l'éditeur et va appeler les méthodes pour créér son interface graphique.
	 * @param l Instance du Loader à utiliser
	 * @see Editeur#afficher()
	 */
	public void demarrer(Loader l){
		loader = l;
		afficher();
		
	}
	
	/**
	 * Crée une JFrame qui contiendra la fenêtre de l'Editeur, puis appelle la méthode pour créer les fenêtres.
	 * @see Editeur#creationFenetre()
	 */
	public void afficher(){
		fenetre = new JFrame("Editeur");
		creationFenetre();
		fenetre.setVisible(true);
	}
	
	/**
	 * Insère la chaîne en paramètre à l'emplacement du curseur, ou à la place de la sélection, 
	 * s'il y en a une (= efface la sélection puis insère)
	 * @param chaine Le texte à insérer
	 */
	public void ecrire(String chaine){ 
		String part1 = texte.substring(0,debutCurseur);
		String part2 = texte.substring(debutCurseur+longueurSelection);
		texte = part1 + chaine + part2;
		debutCurseur = debutCurseur + chaine.length();
		longueurSelection = 0;
		textArea.setText(texte);
		textArea.setCaretPosition(debutCurseur); //fait rien, pourquoi ?
	}
	
	/**
	 * Efface le caractère avant le curseur, ou la sélection s'il y en a une.
	 */
	public void effacer(){
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
	
	/**
	 * Met dans le presse papier ce qui est sélectionné
	 */
	public void copier(){
		if(longueurSelection>0){
			pressePapier = texte.substring(debutCurseur,debutCurseur+longueurSelection);
			System.out.println("Copié");
		}
	}
	
	/**
	 * Met dans le presse papier le texte sélectionné puis efface cette sélection.
	 */
	public void couper(){
		copier();
		effacer();
	}
	
	/**
	 * Insère le contenu du presse papier à l'emplacement du curseur, 
	 * ou à la place de la sélection, s'il y en a une (= efface la sélection puis insère).
	 */
	public void coller(){ 
		if(pressePapier.length()>0){
			ecrire(pressePapier);
		}
	}
	
	/**
	 * Met à jour les attributs représantant la sélection courante (debutCurseur et longueurSelection).
	 * @param debut L'emplacement du premier caractère sélectionné dans le texte.
	 * @param fin L'emplacement du dernier caractère sélectionné dans le texte.
	 */
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
	
	/**
	 * Crée et place tous les pannels nécessaires pour l'application :
	 * <ul>
	 * <li> Un pour la zone de texte </li>
	 * <li> Un pour les boutons basiques </li>
	 * <li> Un pour les boutons des plugins </li>
	 * </ul>
	 */
	private void creationFenetre()
	{
		fenetre.setPreferredSize(new Dimension(1200,600)); //On donne une taille à notre fenêtre
		fenetre.setExtendedState(fenetre.getExtendedState() |JFrame.MAXIMIZED_BOTH);
		fenetre.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		fenetre.setResizable(true); 
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		fenetre.getContentPane().add(panel,"North");
		
		textAreaPane = new JPanel();
		textAreaPane.setLayout(new BorderLayout());
		textAreaPane.setPreferredSize(new Dimension(1200,600));
		creationTextArea(textAreaPane);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setPreferredSize(new Dimension(1200,40));
		creationBoutonsStandards(buttonPane);
		
		JPanel pluginsPane = new JPanel();
		pluginsPane.setPreferredSize(new Dimension(1200,100));
		creationBoutonsPlugins(pluginsPane);
		
		JPanel globalPane = new JPanel();
		globalPane.setLayout(new BorderLayout());
		globalPane.add(textAreaPane, BorderLayout.CENTER);
		globalPane.add(buttonPane, BorderLayout.NORTH);
		globalPane.add(pluginsPane, BorderLayout.SOUTH);
		
		fenetre.getContentPane().add(globalPane);
		fenetre.pack();
	}
	
	/**
	 * Crée la zone de texte de l'éditeur ainsi que les listeners correspondants.
	 * @param panel Le JPanel de l'application
	 */
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
	
	/**
	 * Crée les boutons d'actions standards (Copier, Couper, Coller, Effacer).
	 * @param panel Le JPanel de l'application
	 */
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
	
	/**
	 * Crée les boutons des plugins actuellement chargés, à l'aide des méthodes correspondant à chaque type de plugins.
	 * @param panel Le JPanel de l'application
	 * @see Editeur#creationBoutonsAfficheurs(JPanel)
	 * @see Editeur#creationBoutonsChargeurs(JPanel)
	 * @see Editeur#creationBoutonsModificateurs(JPanel)
	 */
	private void creationBoutonsPlugins(JPanel panel)
	{
		creationBoutonsModificateurs(panel);
		creationBoutonsChargeurs(panel);
		creationBoutonsAfficheurs(panel);
	}
	
	/**
	 * Crée les boutons des plugins de type "Afficheur" actuellement chargés obtenus à l'aide du Loader.
	 * <p>Ajoute également le composant graphique généré par ce plugin à la bonne position sur la fenêtre de l'application.</p>
	 * 
	 * @param panel Le JPanel de l'application
	 */
	private void creationBoutonsAfficheurs(JPanel panel){
		ArrayList<String> afficheurs = loader.getNomsPlugins("afficheur");
		for(String nomAff : afficheurs){
			final JButton jb = new JButton(nomAff);
			jb.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) { 	
					try {
						IAfficheur plug = (IAfficheur) loader.loadPlugin(jb.getText());
						JComponent comp = plug.creerJComponent(textArea);
						String position = plug.getPosition();
						if(position.equals("North") || position.equals("East") || position.equals("South") || position.equals("West")){
							textAreaPane.add(comp, position);
							textAreaPane.revalidate();
						}
						else{
							System.out.println("La position de l'afficheur n'est pas valide.");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} 
			});
			panel.add(jb);
		}
	}
	
	/**
	 * Crée les boutons des plugins de type "Modificateur" actuellement chargés, obtenus à l'aide du Loader.
	 * <p>Ajoute également le listener correspondant, exécutant l'action du plugin après appui sur le bouton</p>
	 * @param panel Le JPanel de l'application
	 */
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
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} 
			});
			panel.add(jb);
		}
	}
	
	/**
	 * Crée les boutons des plugins de type "Chargeurs" actuellement chargés, obtenus à l'aide du Loader.
	 * <p>Ajoute également le listener correspondant, exécutant l'action du plugin après appui sur le bouton</p>
	 * @param panel Le JPanel de l'application
	 */
	private void creationBoutonsChargeurs(JPanel panel){
		ArrayList<String> chargeurs = loader.getNomsPlugins("chargeur");
		for(String nomCharg : chargeurs){
			final JButton jb = new JButton(nomCharg);
			jb.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) { 	
					try {
						IChargeur plug = (IChargeur) loader.loadPlugin(jb.getText());
						String txt = plug.recupererDonnees();
						if(txt!=null && !txt.equals("")){
							ecrire(txt);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} 
			});
			panel.add(jb);
		}
	}
}