import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphicPanel extends JPanel{
	
	Image Panneau;
	Image JetonRouge;
	Image JetonJaune;
	static ArrayList<Jeton> JetonListe = new ArrayList<Jeton>();

	public GraphicPanel() {
		Panneau = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Plateau.png"));	
		JetonJaune = Toolkit.getDefaultToolkit().getImage(getClass().getResource("JetonJ.png"));
		JetonRouge = Toolkit.getDefaultToolkit().getImage(getClass().getResource("JetonR.png"));

		 
	}
		
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.drawImage(Panneau, 0, 0, this.getWidth(), this.getHeight(), this);
		int i =1;
	      for(Jeton p : GraphicPanel.JetonListe)
	      {  
	    	 int Colonne = p.getColonne();
	    	 int Ligne = p.getLigne();
	    	 int x = this.getWidth()/7*Colonne;
	    	 int y = this.getHeight()/6*Ligne;
	 		 if(p.getCouleur().equals(Color.YELLOW)) {
		    	 System.out.println("Graphics drawImage : Jeton "+p.getCouleur() + " en comparaison à " + new Color(255,255,0));
		 			g.drawImage(JetonJaune, x, y, this);
	 		 }
	 		 if(p.getCouleur().equals(Color.RED)) {
		    	 System.out.println("Graphics drawImage : Jeton "+p.getCouleur() + " en comparaison à " + new Color(255,0,0));
		 			g.drawImage(JetonRouge, x,y, this);

	 		 }
	 		 i++;
	      }
}
	private void destroyJetonList() {
		JetonListe = new ArrayList<Jeton>();
	}
}
