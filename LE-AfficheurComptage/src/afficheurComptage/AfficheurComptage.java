package afficheurComptage;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import editeur.IAfficheur;

public class AfficheurComptage implements IAfficheur{

	public String informationsPlugin() {
		return "Je suis un afficheur qui indique la taille et compte les mots.";
	}

	public JComponent creerJComponent(JTextArea textArea) {
		final JTextArea infos = new JTextArea("Taille : 0 / Mots : 0");
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

	public String getPosition() {
		return "South";
	}

}
