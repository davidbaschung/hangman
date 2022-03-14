package main;

import java.io.Serializable;

public class Score implements Serializable {
	String nom;
	int score;
	public Score(String nom, int score) {
		this.nom = nom;
		this.score = score;
	}
}
