package main;

import java.awt.Container;

public interface Observateur {
	public void setNouvellePage(String str);//Container classePanneau); // on fournit un String qui r�f�rencera les var de panneaux accueil et pendu cr��s dans notre Fenetre. (on ne peut pas acc�der � ces var depuis un panneau)
	// OLD AVEC (Container classePanneau) :la classe fournie doit �tre la m�me dans la d�finition de la m�thode et � l'appel de celle-ci. Ne fonctionne pas avec JPanel
}
