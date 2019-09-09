import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class SetupFrame extends JFrame{
	private JPanel Panel = new JPanel();
	private JButton Jouer = new JButton("Jouer");
	private JCheckBox Rouges = new JCheckBox("Equipe rouge");
	private JCheckBox Jaunes = new JCheckBox("Equipe jaune");
	private JMenuBar MenuBar = new JMenuBar();
	private JMenu Options= new JMenu("Options");
	private JMenuItem IpMenuItem = new JMenuItem("IP");
	private JCheckBox ServeurMenuItem = new JCheckBox("Serveur");
	private IPdialog IpDiag;
	static boolean server = false;
	static String IPAdress;
	static Color Equipe;
	Frame Logic;
	
public SetupFrame() {
	this.setTitle("Setup");
	this.setSize(300, 150);
	this.setResizable(false);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLocationRelativeTo(null);
	
	Options.add(IpMenuItem);
	Options.add(ServeurMenuItem);		
	MenuBar.add(Options);
	this.setJMenuBar(MenuBar);
	Jouer.setEnabled(false);
	
	Panel.setLayout(new BorderLayout());
	Panel.add(Jouer,new BorderLayout().SOUTH);
	Panel.add(Rouges,new BorderLayout().WEST);
	Panel.add(Jaunes,new BorderLayout().EAST);
	this.setContentPane(Panel);
	IpDiag = new IPdialog(this);
	
	IpMenuItem.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			IpDiag.setVisible(true);
		}
	});
	
	ServeurMenuItem.addItemListener(new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange()==ItemEvent.SELECTED) {
				IpMenuItem.setEnabled(false);
				server = true;
			}else {
				IpMenuItem.setEnabled(true);
				server = false;
			}
			
		}
	});
	
	Jouer.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Logic = new Frame();
			Logic.startNetworkComponent(server,IPAdress);

			int i=0;
			while(i<=30) {
				if(Frame.NetworkInterface.getNetworkState()) {
					i=60;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				i++;
			}
			
			if(i>=60) {
				Logic.setVisible(true);
				Logic.createDiag();
				dispose();
				Logic.startGameEngine();
			}else {
				JOptionPane.showMessageDialog( null, "Aucune connection n'a été établie","Erreur" , JOptionPane.ERROR_MESSAGE);
			}
		}
	});
	
	Rouges.addItemListener(new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange()==ItemEvent.SELECTED) {
				Jaunes.setSelected(false);
				Jaunes.setEnabled(false);
				Equipe = Color.RED;
				
				Jouer.setEnabled(true);
			}else {
				Jaunes.setEnabled(true);
				Equipe = null;
				Jouer.setEnabled(false);
			}
			
		}
	});
	
	Jaunes.addItemListener(new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange()==ItemEvent.SELECTED) {
				Rouges.setSelected(false);
				Rouges.setEnabled(false);
				Equipe = Color.YELLOW;
				Jouer.setEnabled(true);
			}else {
				Rouges.setEnabled(true);
				Equipe = null;
				Jouer.setEnabled(false);
			}
			
		}
	});
	
	this.setVisible(true);
	
}

public static Color getEquipeColor() {
	System.out.print("Couleur de l'équipe : "+Equipe);
	return Equipe;
}

public static void setIP(String ipAdress) {
	IPAdress = ipAdress;
	
}
}
