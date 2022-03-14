package main;

public class Main {
	
	public static void main(String[] args) {
		Fenetre fen = new Fenetre();
	}
}

// notes

// il n'est pas possible de stocker une classe héritant d'une autre classe comme étant réellement cette autre classe. Donc : (class... extends JPanel) stocké à l'instanciation dans JPanel, c'est faux.' En revanche, peut passer (comme étant des paramètres) différentes extensions de panneaux en les stockant comme Container

// pattern observator :
// lorsque qu'on crée un panneau :
// 		directement créer une méthode d'affichage
//		fournir la Fenetre en paramètre, plutôt que d'appeler une méthode.