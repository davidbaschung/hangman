package main;

public class Main {
	
	public static void main(String[] args) {
		Fenetre fen = new Fenetre();
	}
}

// notes

// il n'est pas possible de stocker une classe h�ritant d'une autre classe comme �tant r�ellement cette autre classe. Donc : (class... extends JPanel) stock� � l'instanciation dans JPanel, c'est faux.' En revanche, peut passer (comme �tant des param�tres) diff�rentes extensions de panneaux en les stockant comme Container

// pattern observator :
// lorsque qu'on cr�e un panneau :
// 		directement cr�er une m�thode d'affichage
//		fournir la Fenetre en param�tre, plut�t que d'appeler une m�thode.