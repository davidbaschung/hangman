package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Accueil extends JPanel implements PanneauPrincipal {
	
	private JLabel titre = new JLabel();
	private JTextArea explications = new JTextArea();
	private JEditorPane points = new JEditorPane();
	private Observateur obs;
	private Image fond;
	
	Accueil() {
		
		afficher();
		
	}
	
	private void afficher() {
		this.removeAll();
		
		titre.setText("Le Pendu");
		
		try {
			fond = new ImageIcon("./images/fondmenu.jpg").getImage();
//			this.add(fond);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		explications.setText("Vous avez 7 coups pour trouver le mot caché. Plus vous trouvez ce mot rapidement, plus vous gagnez de points. Si vous trouvez le mot, le jeu continue et vous cummulez vos points.");
		explications.setBackground(null);
		explications.setOpaque(false);
		explications.setLineWrap(true);
		
		
		points.setContentType("text/html");
		points.setOpaque(false);
		// setText doit être déclaré APRES setContentType
		String pointsTexte = "<div style='font-weight:bold'>POINTS lorsque le mot est trouvé:</div>"; //petit kiff HTML
		int[] pointsNombre = {100,50,35,25,15,10,5}; // R : int pointsnombre[] pourrait marcher?? 
		for (int i = 0; i<pointsNombre.length;i++) {
			pointsTexte =  pointsTexte +"<br/>"+i+" "+"erreurs : "+String.valueOf(pointsNombre[i]);
		}
		points.setText(pointsTexte);
		points.setBackground(null);
		
		JButton bJouer = new JButton("Jouer");
		bJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obs.setNouvellePage("pendu");
			}
		});
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // alignement vertical
		this.add(titre, BorderLayout.CENTER);
		this.add(explications, JPanel.CENTER_ALIGNMENT);
		this.add(points, BorderLayout.CENTER);
		this.add(bJouer);
		
		this.revalidate();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(255,215,170));//255,180,120));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(fond,130,30,this.getWidth()-260,this.getHeight()-60,this);
	}
	
	public void addObservateur(Observateur obs) {
		this.obs = obs;
	}
}
