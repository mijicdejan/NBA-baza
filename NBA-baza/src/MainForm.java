import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MainForm extends JFrame {

	private JTabbedPane tabbedPane;
	private JPanel playersPane;
	private JPanel teamsPane;
	
	private JLabel playerStatusLbl;
	private JComboBox<String> playerStatusCB;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1000, 700);
		setBackground(new Color(255, 255, 255));
		setTitle("NBA");
		setResizable(false);
		playersPane = new JPanel();
		playersPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		playersPane.setBackground(new Color(0, 102, 204));
		playersPane.setLayout(null);
		
		teamsPane = new JPanel();
		teamsPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		teamsPane.setBackground(new Color(0, 102, 204));
		teamsPane.setLayout(null);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		tabbedPane.setBackground(new Color(255, 255, 255));
		tabbedPane.add("Players", playersPane);
		tabbedPane.addTab("Teams", teamsPane);
		setContentPane(tabbedPane);
		
		initPlayersPane();
	}
	
	private void initPlayersPane() {
		playerStatusLbl = new JLabel("Player status: ");
		playerStatusLbl.setBounds(10, 0, 300, 30);
		playerStatusLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		playersPane.add(playerStatusLbl);
		
		playerStatusCB = new JComboBox<>();
		playerStatusCB.setBounds(10, 30, 300, 30);
		playerStatusCB.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		playerStatusCB.addItem("All players");
		playerStatusCB.addItem("Active players");
		playerStatusCB.addItem("Retired players");
		playersPane.add(playerStatusCB);
	}
	
	private void initComboBoxListeners() {
		
		playerStatusCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
	}

}
