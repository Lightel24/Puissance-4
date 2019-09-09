import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame extends JFrame{
	
	JPanel DiagPanel = new JPanel();
	JDialog diag;
	JLabel YourTurnLabel = new JLabel();
	GraphicPanel Panel;
	static boolean Gaming = true;
	static Color SelfEquipe;
	static NetworkComponent NetworkInterface ;
	static boolean Yourturn;
	Thread t;

	public Frame() {
		SelfEquipe = SetupFrame.getEquipeColor();
		Panel = new GraphicPanel();
		
		this.setSize(678,585);
		this.setResizable(false);
		this.setTitle("Puissance 4");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(Panel);
		System.out.println("Création d'un MouseListener");
		this.addMouseListener(new MouseAdapter(){
		      public void mousePressed(MouseEvent e){
		    	  if(Yourturn) {
		       	  int colonne=0;
		       	  if(e.getX()<678/7) {
		       		 colonne=0;	 
		       	 }else{
		       		 if(e.getX()<678/7*2) {
		       			 colonne=1;
		       		 }else{
		       			 if(e.getX()<678/7*3) {
		       				 colonne=2;
		       			 }else {
		       				 if(e.getX()<678/7*4) {
		       					 colonne=3;
		       				 }else {
		       					 if(e.getX()<678/7*5) {
		       						 colonne=4;
		       					 }else {
		       						 if(e.getX()<678/7*6) {
		       							 colonne=5;
		       						 }else{
		       							 if(e.getX()<=678) {
		       								 colonne=6;
		       						 }
		       							 
		       						 }
		       							 
		       						 }
		       					
		       				 }
		       			 }
		       		 }
		       	 }
		       	  System.out.println("MousePressed: "+e.getX()+"\nColonne: "+colonne);
		    	 AjouterUnJeton(colonne,SelfEquipe);
		    	  repaint();
		      }
		      }
		});
				
      }

	
	public void createDiag() {
		  System.out.println("Création d'une JDialog");
     	  diag = new JDialog(this);
     	  diag.setContentPane(DiagPanel);
     	 if(Yourturn) {
     		YourTurnLabel.setText("C'est votre tour.");
     		DiagPanel.repaint();
 		}else{ 			
     		YourTurnLabel.setText("Ce n'est pas votre tour");
     		DiagPanel.repaint();
 		}
     	DiagPanel.add(YourTurnLabel);
    	  diag.setSize(200, 100);
     	  diag.setResizable(false);
     	  diag.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
     	  diag.setLocation(diag.getParent().getLocationOnScreen().x+diag.getParent().getWidth(),diag.getParent().getLocationOnScreen().y);
     	  diag.setVisible(true);
	}
	
	
	private void switchYourTurn() {
		if(Yourturn) {
			Yourturn=false;
     		YourTurnLabel.setText("Ce n'est pas votre tour");
			DiagPanel.repaint();
		}else{
			Yourturn = true;
     		YourTurnLabel.setText("C'est votre tour.");
			DiagPanel.repaint();
		}
	}
	
	
	public static void setStarter(boolean starter) {
		Yourturn = starter;
	}
	
	
	protected void AjouterUnJeton(int colonne, Color Equipe) {
	  	  int PlusHauteLigne = 5;
	   			 
	   		 
	   	 
	   	  for(Jeton p : GraphicPanel.JetonListe)
		      {
	   		  if(p.getColonne()==colonne) {
	   			  if(p.getLigne()<=PlusHauteLigne) {
	   				PlusHauteLigne=p.getLigne()-1;
	   			  
	   		  }
		      }
		      }
		    	if(PlusHauteLigne>=0) {  
		    		GraphicPanel.JetonListe.add(new Jeton(Equipe,colonne,PlusHauteLigne));
		    		NetworkInterface.sendJeton(new Jeton(Equipe,colonne,PlusHauteLigne));
			    	switchYourTurn();
	   		  
		    	}
			  		
		}


	public void startNetworkComponent(boolean server, String iPAdress) {
		
		NetworkInterface = new NetworkComponent(server, iPAdress);
		NetworkInterface.start();
		
	}
	
	public void startGameEngine() {
		t = new Thread(new GamingEngine());
		t.start();
	}
	
	public class GamingEngine implements Runnable{
		public void run() {
		System.out.println("Lancement: GameEngine ");
		while(Gaming) {
			
			if(diag.getLocationOnScreen().x!=diag.getParent().getLocationOnScreen().x+diag.getParent().getWidth()) {
				diag.setLocation(diag.getParent().getLocationOnScreen().x+diag.getParent().getWidth(),diag.getLocationOnScreen().y);
				diag.repaint();
				
			}
			
			
				Jeton p = NetworkInterface.getUpdate();
				if(p != null) {
	    		GraphicPanel.JetonListe.add(p);
	    		switchYourTurn();
	    		Panel.repaint();
	    		NetworkInterface.clearJetonTampon();
				}
				
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
			
		}
		
		
		
	}
	}
	


