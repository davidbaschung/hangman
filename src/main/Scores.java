package main;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Scores extends JPanel implements PanneauPrincipal {
	Observateur obs;
	Score[] listeScore;
	int length = 10;
	
	public Scores() {
		try {
			JPanel panScores = new JPanel();
			
			ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(
									new File("scores.txt"))));
			
			listeScore = (Score[]) ois.readObject(); 
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		afficher();
	}
	
	public void afficher() {
		JLabel titre = new JLabel("Meilleurs scores :");
		JLabel videTitre = new JLabel(" ");
		titre.setFont(new Font("Sans-Serif",Font.CENTER_BASELINE,15));
		
		JPanel genPan = new JPanel();
		genPan.setLayout(new BoxLayout(genPan,BoxLayout.Y_AXIS));
		
		JPanel panScores = new JPanel();
		panScores.setLayout(new BoxLayout(panScores,BoxLayout.Y_AXIS));
		
		for (int i=0; i<this.length; i++) {
			Score score = listeScore[i];
			panScores.add(new JLabel(score.nom+":   "+i+" points"));
		}

		genPan.add(titre);
		genPan.add(videTitre);
		genPan.add(panScores);
		this.add(genPan);
	}
	
	public void addObservateur(Observateur obs) {
		this.obs = obs;
	}
}
