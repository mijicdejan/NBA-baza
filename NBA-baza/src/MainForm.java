import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class MainForm extends JFrame {

	private JPanel contentPane;
	
	private JLabel seasonLbl;
	private JComboBox<String> seasonCB;
	
	private JLabel dateLbl;
	private JComboBox<Integer> dayCB;
	private JComboBox<String> monthCB;
	
	private JTable gamesTbl;
	private JScrollPane scroll;
	private DefaultTableModel dtm;

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
		setBounds(100, 50, 900, 520);
		setBackground(new Color(255, 255, 255));
		setTitle("NBA");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		seasonLbl = new JLabel("Season: ");
		seasonLbl.setBounds(10, 10, 200, 25);
		seasonLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(seasonLbl);
		
		seasonCB = new JComboBox<>();
		seasonCB.setBounds(10, 35, 200, 30);
		seasonCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		seasonCB.setBackground(Color.WHITE);
		contentPane.add(seasonCB);
		
		dateLbl = new JLabel("Date: ");
		dateLbl.setBounds(10, 70, 200, 25);
		dateLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(dateLbl);
		
		dayCB = new JComboBox<>();
		dayCB.setBounds(10, 95, 70, 30);
		dayCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		dayCB.setBackground(Color.WHITE);
		contentPane.add(dayCB);
		
		monthCB = new JComboBox<>();
		monthCB.setBounds(90, 95, 120, 30);
		monthCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		monthCB.setBackground(Color.WHITE);
		contentPane.add(monthCB);
		
		initTable();
	}
	
	private void initTable() {
		dtm = new DefaultTableModel();
		dtm.addColumn("Gametime");
		dtm.addColumn("Home team");
		dtm.addColumn("Home team score");
		dtm.addColumn("Away team score");
		dtm.addColumn("Away team");
		
		Object[] rowData = { "1", "2", "3", "4", "5" };
		for(int i = 0; i < 7; i++) {
			dtm.addRow(rowData);
		}
		
		gamesTbl = new JTable(dtm);
		gamesTbl.setAutoCreateRowSorter(true);
		gamesTbl.setFont(new Font("Century Gothic", Font.BOLD, 12));
		gamesTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gamesTbl.setForeground(new Color(0, 0, 139));
//		gamesTbl.setBackground(new Color(173, 216, 230));
		gamesTbl.setBackground(Color.white);
		gamesTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 12));
		gamesTbl.getTableHeader().setBackground(Color.white);
		
		scroll = new JScrollPane(gamesTbl);
		scroll.setBounds(10, 150, 600, 263);
		scroll.setBackground(new Color(173, 216, 230));
//		scroll.getViewport().setBackground(new Color(173, 216, 230));
		scroll.getViewport().setBackground(Color.white);
		contentPane.add(scroll);
	}

}
