package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Pendu extends JPanel implements PanneauPrincipal{
	
	JLabel ecran = new JLabel();
	private Image image = new ImageIcon("./images/pendu0.jpg").getImage();
	private JPanel imagePan = new JPanel();
	private JLabel imageLabel;
	// rem : les panneaux pour boutons sont créés à l'instanciation et pas comme variable de classe, sinon on ne pourrait pas tous supprimer lors du removeAll. Corr : Le mieux aurait été de créer un JPanel général dans notre pendu pour y mettre les JComponent en variables de classe.
	
	
	private Observateur obs;
	private String touches = "qwertzuiopasdfghjklyxcvbnm";
	private String secret = "";
	private String secretCache = "";
	private int coups;
	private int score;
	private String issue;
	private String nomJoueur = "pas de nom";
	
	public Pendu() {
		
//		this.setSize(800,500);
		this.setLayout(new BorderLayout());
		
		nouveauJeu();
		
		afficher();
	}
	
	private void afficher() {
		removeAll();
		
		JPanel ecranPan = new JPanel();
		JPanel clavierPan = new JPanel();
		JPanel clavierPanP1 = new JPanel();
		JPanel clavierPanP2 = new JPanel();
		JPanel clavierPanP3 = new JPanel();
		
		afficherImagePan();
		
		
		ecranPan.setBackground(Color.WHITE); // pas pour JLabel
		ecranPan.setSize(300,200);
		ecran.setFont(new Font("Serif", Font.BOLD, 50));
		ecran.setForeground(Color.GREEN.darker());
		
		clavierPan.setLayout(new BorderLayout());
		
		for (int i=0;i<touches.length();i++) {
			JButton b = new JButton(Character.toString(touches.charAt(i)));
			b.setPreferredSize(new Dimension(65,65));
			b.addActionListener(new clavierListener(touches.charAt(i))); // charAt pour récupérer une lettre (pas c[i] ni c.get(i))
			if (i<10) {clavierPanP1.add(b);}
			else if (i<19) {clavierPanP2.add(b);}
			else {clavierPanP3.add(b);};
		}
		
//		imagePan.add(imageLabel,BorderLayout.CENTER);
		this.add(imagePan, BorderLayout.NORTH);
		ecranPan.add(ecran,BorderLayout.NORTH);
		this.add(ecranPan, BorderLayout.CENTER);
		clavierPan.add(clavierPanP1,BorderLayout.NORTH);
		clavierPan.add(clavierPanP2,BorderLayout.CENTER); 
		clavierPan.add(clavierPanP3,BorderLayout.SOUTH);
		this.add(clavierPan, BorderLayout.SOUTH);
		this.revalidate();
//		this.repaint();
	}
	
	private void afficherImagePan() { // REPERE panel image panel
		imagePan.removeAll();
		
		imagePan.setLayout(new GridLayout());
		
		JPanel scoreLabelPan = new JPanel();
		scoreLabelPan.setLayout(new BorderLayout());
//		scoreLabelPan.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		JLabel scoreLabel = new JLabel("Score : " + String.valueOf(score));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER); // centrer veticalement le composant
//		scoreLabel.setVerticalAlignment(SwingConstants.CENTER); pas horiz et vert en même temps! ==> setHoriz pour horizontalité du composant, new Borderlayout() et add(pan,BorderLayout.CENTER) pour verticalité dans panel
		image = image.getScaledInstance(image.getWidth(this)/3,image.getHeight(this)/3,Image.SCALE_DEFAULT); // redimensionnage
		imageLabel = new JLabel(new ImageIcon(image));
		JPanel panel3 = new JPanel();
		JLabel issueLabel = new JLabel(issue);
//		issueLabel.setFont(new Font("Sans-Serif",Font.CENTER_BASELINE, 12));
		
		scoreLabelPan.add(scoreLabel, BorderLayout.CENTER);
		imagePan.add(scoreLabelPan, BorderLayout.WEST);
		imagePan.add(imageLabel, BorderLayout.CENTER);
		panel3.add(issueLabel, BorderLayout.CENTER);
		imagePan.add(panel3, BorderLayout.EAST);
		
		imagePan.revalidate();
	}

	private void nouveauJeu() { // on met throw pour le try/catch/finally. Surtout pour finally?
		coups = 0;
		
		FileReader reader = null;
		LineNumberReader lNReader = null;
		String str;
		int nombre = (int)(Math.random()*336529);
		try {
			reader = new FileReader("./dictionnaire.txt");
			lNReader = new LineNumberReader(reader);
			secret = "";
			secretCache = "";
			while ((str=lNReader.readLine())!=null) {
				int i=lNReader.getLineNumber();
				if (i==nombre) {
					for (int j=0; j<str.length(); j++) {
						char c = str.charAt(j);
						if (str.charAt(j)=='é' | str.charAt(j)=='è' | str.charAt(j)=='ê' | str.charAt(j)=='ë') c = 'e';
						if (str.charAt(j)=='à' | str.charAt(j)=='â' | str.charAt(j)=='ä') c = 'a';
						if (str.charAt(j)=='ô' | str.charAt(j)=='ö') c = 'o';
						if (str.charAt(j)=='ù' | str.charAt(j)=='û' | str.charAt(j)=='ü') c = 'u';
						secret += Character.toString(c);
						secretCache += '_';
					}
					break;
				}
			}
			
//			for (int i=0; i<secret.length();i++) {
//				secretCache += "_";
//			}
			afficheMot();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally  {
			if (reader!=null)
				try { // on re-try/catch si un finally implique du contenu du 1er try
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	private void afficheMot() {
		String secretAffiche = "";
		for (int i=0; i<secretCache.length(); i++) {
			secretAffiche += secretCache.charAt(i) + " ";
		}
		ecran.setText(secretAffiche);
	}
	
	private class clavierListener implements ActionListener{
		private char c;
		private clavierListener(final char c) {
			super();
			this.c = c;
		}
		public void actionPerformed(ActionEvent e) {
			if (coups < 7 & ((JButton)e.getSource()).getBackground()!=Color.LIGHT_GRAY) {
				String secretCacheProvisoire = "";
				boolean bonCoup = false;
				for (int i=0; i<secret.length();i++) {
					char lettreSecret = secret.charAt(i);
					char lettreSecretCache = secretCache.charAt(i);
					if (lettreSecretCache == '_') {
						if (c == lettreSecret) {// méthode String.equals(String) pas nécessaires pour des chars. "==" suffit.
							secretCacheProvisoire += c;
							bonCoup = true;
						} else {
							secretCacheProvisoire += '_';
						}
					} else {
						secretCacheProvisoire += lettreSecretCache;
					}
	//				secretCacheProvisoire = secret.substring(0,i) + c + secret.substring(i+1,secret.length());
				}
				if (bonCoup == false) {
					coups += 1;
				}
				secretCache = secretCacheProvisoire;
				
				if (secretCache.equals(secret)) { // REPERE gagné
					if (coups == 0) score += 100;
					if (coups == 1) score += 50;
					if (coups == 2) score += 35;
					if (coups == 3) score += 25;
					if (coups == 4) score += 15;
					if (coups == 5) score += 10;
					if (coups == 6) score += 5;
					issue = "Mot trouvé !";
					
					nouveauJeu();
					afficher();
				}
				if (! secretCache.equals(secret) & coups == 7) { /// REPERE perdu
					secretCache = secret;
					issue = "Mot non trouvé. Votre partie s'achève ici.";

					int i=0;
					try { 
						
						ObjectInputStream ois = new ObjectInputStream(
								new BufferedInputStream(
										new FileInputStream(
												new File("scores.txt"))));
						
						Score[] listeScore = (Score[]) ois.readObject(); 
						
						ois.close();
						for (int j=0;j<listeScore.length;j++) {
							System.out.println(listeScore[j]);
						}
						int length = listeScore.length;
						
						Score[] listeScoreProvisoire = new Score[10];
						for (i=0; i<length; i++) {
							System.out.println("i = "+i+"    length"+length);
							if (listeScore[i].score<score) { //score tout court étant : int
								listeScoreProvisoire[i] = new Score(nomJoueur,score);
								break;
							} else {
								listeScoreProvisoire[i] = listeScore[i];
							}
						}
						for (i=1; i<length; i++) {
							System.out.println("i = "+i);
							listeScoreProvisoire[i] = listeScore[i-1];
						}
						listeScore = listeScoreProvisoire;
						
						ObjectOutputStream oos = new ObjectOutputStream(
								new BufferedOutputStream(
										new FileOutputStream(
												new File("scores.txt"))));
						
						oos.writeObject(listeScore);
						oos.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
				image = new ImageIcon("./images/pendu"+coups+".jpg").getImage();
				afficherImagePan();
				afficheMot();
				
				((JButton)e.getSource()).setBackground(Color.LIGHT_GRAY);
			}
		}
	}
	
	public void addObservateur(Observateur obs) {
		this.obs = obs;
	}
}
