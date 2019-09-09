import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IPdialog extends JDialog{
	
	 private JFormattedTextField Field1 = new JFormattedTextField();
	 private JButton Lancer = new JButton("Sauvegarder"); 
	 private JPanel Container = new JPanel(); 
	 private JPanel CenterContainer = new JPanel(); 
	 private JPanel SouthContainer = new JPanel(); 
	
	public IPdialog(JFrame parent) {
		super(parent);
		this.setSize(300,100);
		this.setTitle("IP adress");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		this.InitComponent();

	}

	private void InitComponent() {
		Field1.setText("localhost");
		Field1.setPreferredSize(new Dimension(60,20));
		
		Container.setLayout(new BorderLayout());
		SouthContainer.setLayout(new BorderLayout());
		
		CenterContainer.add(Field1);
		SouthContainer.add(Lancer,BorderLayout.SOUTH);
		Container.add(CenterContainer,BorderLayout.CENTER);
		Container.add(SouthContainer,BorderLayout.SOUTH);
		
		Lancer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ipAdress = Field1.getText();
				SetupFrame.setIP(ipAdress);
				setVisible(false);
				
			}
		});
	
		this.setContentPane(Container);
	}
	
}
