import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class NetworkComponent extends Thread{
	
	boolean server;
	boolean connected= false;
	boolean starter;
	static Jeton JetonTampon;
	Jeton Outpacket;
	Jeton Inpacket;
	String iPAdrress;
	ServerSocket ServeurObj;
	Socket ClientObj = new Socket();
	
	public NetworkComponent(boolean server,String iPAdrress) {
		this.server=server;
		this.iPAdrress=iPAdrress;
	}
	
	@Override
	public void run() {
		System.out.println("Lancement de: NetworComponent :"+this.getName());
		try {
			 ObjectOutputStream out;
			 ObjectInputStream in;
			if(server) {
				System.out.println("Lancement de: Serveur");
				ServeurObj = new ServerSocket(3009);
				ClientObj = ServeurObj.accept();
				connected = true;
				
				out = new ObjectOutputStream(ClientObj.getOutputStream());
				in = new ObjectInputStream(ClientObj.getInputStream());
				
				Random rand = new Random(); 
				int nombreAleatoire = rand.nextInt(2);	
				
				if(nombreAleatoire==1) {
					starter = true;
				}else {
					starter = false;
				}
				
				System.out.println("Starter: "+starter);
				out.writeObject(starter);
		        out.flush();
				
			}else {

				System.out.println("Lancement de: Client");
				ClientObj = new Socket(iPAdrress,3009);
				connected = true;
				out = new ObjectOutputStream(ClientObj.getOutputStream());
				in = new ObjectInputStream(ClientObj.getInputStream());
				starter = !(boolean) in.readObject();
				System.out.println("Starter: "+starter);
			}
			
			Frame.setStarter(starter);

			
			
			
		if(starter) {

				 while(true) {
						System.out.println("Attente de paquet à envoyer");
						while(Outpacket == null) {
							Thread.sleep(100);
						}
					//Envoi paquet
						System.out.println("Envoie de paquet : "+Outpacket.getCouleur());
					out.writeObject(Outpacket);
			        out.flush();
			        Outpacket = null;
				
				System.out.println("Attente de réponse ...");
		        Inpacket = (Jeton) in.readObject();
				if(Inpacket != null) {
					System.out.println("Réception de paquet : "+Inpacket.getCouleur());
					 //reception paquet
		        	Jeton Jeton = (Jeton) Inpacket;
		        	JetonTampon = Jeton;
		        	Inpacket = null;
				}
				 }
				 
		}else {
			
			
			 while(true) {
				 

					System.out.println("Attente de réponse ...");
			        Inpacket = (Jeton) in.readObject();			
					if(Inpacket != null) {
						System.out.println("Réception de paquet : "+Inpacket.getCouleur());
						 //reception paquet
			        	Jeton Jeton = (Jeton) Inpacket;
			        	JetonTampon = Jeton;
			        	Inpacket = null;
			        	
							System.out.println("Attente de paquet à envoyer");
							while(Outpacket == null) {
								Thread.sleep(100);
							}
						//Envoi paquet
							System.out.println("Envoie de paquet : "+Outpacket.getCouleur());
						out.writeObject(Outpacket);
				        out.flush();
				        Outpacket = null;
						
			}
		 }
		}
		} catch (IOException e) {
			JOptionPane.showMessageDialog( null, "Une erreur réseau est survenue. La communication a été intérompue.\n"+e.getLocalizedMessage(),"Erreur" , JOptionPane.ERROR_MESSAGE);
			System.exit(1);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog( null, "Une erreur réseau est survenue. La communication a été intérompue.\n"+e.getLocalizedMessage(),"Erreur" , JOptionPane.ERROR_MESSAGE);
				System.exit(1);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog( null, "Une erreur réseau est survenue. La communication a été intérompue.\n"+e.getLocalizedMessage(),"Erreur" , JOptionPane.ERROR_MESSAGE);
			System.exit(1);
			}
	}
	
	public Jeton getUpdate(){
		
		if(JetonTampon!= null) {
		return JetonTampon;
		}else {
			JetonTampon = null;
		return JetonTampon;
		}
	}
	
	public void clearJetonTampon() {
		JetonTampon = null;
	}

	public boolean getNetworkState() {
		
		return connected;
	}

	public void sendJeton(Jeton jeton) {
		System.out.println("Envoie d'un Jeton.");
		this.Outpacket = jeton;
	}
}
