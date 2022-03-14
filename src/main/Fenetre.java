package main;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Fenetre extends JFrame {
	
	Accueil accueil = new Accueil(); // � mettre avant le constructeur!!! car dans la d�finition de setNouvellePage(ci-dessous), on ajoute une r�f�rence depuis une autre classe (Accueil)
	Pendu pendu = new Pendu();
	Scores scores = new Scores();
	
	public Fenetre() {
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Le Pendu");
		this.setLocation(300,300);
		this.setSize(800, 500);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fichier = new JMenu("jeu");
		JMenuItem nouveau = new JMenuItem("nouveau"),
			regles = new JMenuItem("regles"),
			scoresItem = new JMenuItem("scores"),
			aPropos = new JMenuItem("� propos");

		nouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNouvellePageFenetre("pendu");
			}
		});
		regles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNouvellePageFenetre("accueil");
			}
		});
		scoresItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNouvellePageFenetre("scores");
			}
		});
		
		
		fichier.add(nouveau);
		fichier.add(regles);
		fichier.add(scoresItem);
		fichier.add(aPropos);
		menuBar.add(fichier);
		
		accueil.addObservateur(new FenetreObservateur(this));
		
		this.setContentPane(accueil);
		
		this.setJMenuBar(menuBar);
		this.setVisible(true);
	}
	
	public void setNouvellePageFenetre(String str) {
		Container nouveauPanneau = null;
		if (str.equals("accueil")) {
			nouveauPanneau = accueil = new Accueil(); // m�me si le principe �tait de fournir la va accueil sans la red�finir...
			accueil.addObservateur(new FenetreObservateur(this)); // on va quand m�me red�finir nos panneaux et leur ajouter un observateur, ainsi un nouveau jeu de pendu se recr�e � chaque fois lorsqu'on clique sur l'item "nouveau"...
			// de toute mani�re, il faut stocker nos panneaux dans une variable pour leur assigner un observateur.
		}
		if (str.equals("pendu")) {
			nouveauPanneau = pendu = new Pendu();
			pendu.addObservateur(new FenetreObservateur(this));
		}
		if (str.equals("scores")) {
			nouveauPanneau = scores = new Scores();
			scores.addObservateur(new FenetreObservateur(this));
		}
		getContentPane().removeAll(); // pas de this.var=... this �tant ici Observateur
		setContentPane(nouveauPanneau);
		getContentPane().revalidate();
	}
	
	// gr�ce � cet observateur, les getContentPane concerneront notre Fenetre et seront accessibles dans les PanneauPrincipal
	private static class FenetreObservateur implements Observateur {
		Fenetre f;
		public FenetreObservateur(Fenetre f) {
			this.f = f;
		}
		public void setNouvellePage(String str) {
			f.setNouvellePageFenetre(str);
		}
	}
	
	
}