package main;

import java.awt.Container;

public interface Observateur {
	public void setNouvellePage(String str);//Container classePanneau); // on fournit un String qui référencera les var de panneaux accueil et pendu créés dans notre Fenetre. (on ne peut pas accéder à ces var depuis un panneau)
	// OLD AVEC (Container classePanneau) :la classe fournie doit être la même dans la définition de la méthode et à l'appel de celle-ci. Ne fonctionne pas avec JPanel
}
