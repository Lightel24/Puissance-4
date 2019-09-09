import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;

public class Jeton implements Serializable{
	 Color Couleur;
	 Image Jeton;
	 int Ligne;
	 int Colonne;

	 public Jeton() {

	 }
		public Jeton(Color SetCouleur, int colonne,int ligne) {
		 Couleur = SetCouleur;
		 Ligne = ligne;
		 Colonne = colonne;


	 
	 }
	 
	public Color getCouleur() {
		
		return Couleur;
	}

	public int getColonne() {
		return Colonne;
	}
	public int getLigne() {
		return Ligne;
}
}
