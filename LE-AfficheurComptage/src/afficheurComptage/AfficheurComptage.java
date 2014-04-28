package afficheurComptage;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import interfaces.IAfficheur;

/**
 * La classe AfficheurComptage est un exemple de plugin de type "Afficheur",
 * qui permet d'ajouter un composant graphique contenant des informations
 * sur la taille du texte et le nombre de mots écrits dans l'éditeur de texte.
 * @author Benjamin Grouhan, Alexis Guilbaud, Kevin Mokili
 * @see IAfficheur
 */
public class AfficheurComptage implements IAfficheur{

	/**
	 * Implémentation de la méthode creerJComponent(JTextArea) de l'interface IAfficheur.
	 * <p> Permet de créer une zone de texte affichant la taille du texte et le nombre de mots du texte. </p>
	 * <p> Va également créer les listeners correspondant pour mettre à jour l'affichage 
	 * à chaque changement dans le texte. </p>
	 * @param textArea La zone de texte de l'éditeur dans laquelle il est possible d'obtenir des informations.
	 * @return Le composant graphique (JTextArea) affichant les informations voulues.
	 * @see IAfficheur#creerJComponent(JTextArea)
	 */
	public JComponent creerJComponent(JTextArea textArea) {
		final JTextArea infos = new JTextArea("Taille : 0 / Mots : 0");
		infos.setEditable(false);
		final Document doc = textArea.getDocument();
		doc.addDocumentListener(new DocumentListener(){
			public String creerString(){
				int taille = doc.getLength();
				int mots = 0;
				boolean motEnCours = false;
				for(int i=0; i<taille; i++){
					String car=null;
					try {
						car = doc.getText(i, 1);
					} catch (BadLocationException e) {
						System.out.println("BadLocationException.");
					}
					if (motEnCours){
						if (car.equals(" ") || car.equals("\n") || car.equals("\t")){
							motEnCours = false;
						}
					}
					else{
						if (!car.equals(" ") && !car.equals("\n") && !car.equals("\t")){
							mots++;
							motEnCours = true;
						}
					}
				}
				return "Taille : "+taille+" / Mots : "+mots;
			}
			
			public void changedUpdate(DocumentEvent arg0) {
				infos.setText(creerString());
			}

			public void insertUpdate(DocumentEvent arg0) {
				infos.setText(creerString());
			}

			public void removeUpdate(DocumentEvent arg0) {
				infos.setText(creerString());
			}
			
		});
		return infos;
	}

	/**
	 * Implémentation de la méthode getPosition() de l'interface IAfficheur.
	 * @see IAfficheur#getPosition()
	 */
	public String getPosition() {
		return "South";
	}

}
