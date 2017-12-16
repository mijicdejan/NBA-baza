import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import datechooser.beans.DateChooserCombo;
import datechooser.model.multiple.MultyModelBehavior;

@SuppressWarnings("serial")
public class AddSeasonForm extends JFrame {

	private JPanel contentPane;
	
	private JLabel startLbl;
	private DateChooserCombo startDCC;
	private JLabel endLbl;
	private DateChooserCombo endDCC;
	private JLabel playoffStartLbl;
	private DateChooserCombo playoffStartDCC;
	private JLabel playoffEndLbl;
	private DateChooserCombo playoffEndDCC;
	private JLabel numberOfGamesLbl;
	private JComboBox<Integer> numberOfGamesCB;
	private JButton saveBtn;
	
	private AddSeasonFormController addSeasonFormController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddSeasonForm frame = new AddSeasonForm();
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
	public AddSeasonForm() {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetAddSeasonFormOpened();
			}

			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 50, 330, 425);
		setBackground(new Color(255, 255, 255));
		setTitle("Add season");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		addSeasonFormController = new AddSeasonFormController(this);
		
		initComponents();
		initButtonListener();
	}
	
	private void initComponents() {
		startLbl = new JLabel("Start date: ");
		startLbl.setBounds(10, 10, 300, 25);
		startLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(startLbl);
		
		startDCC = new DateChooserCombo();
		startDCC.setBehavior(MultyModelBehavior.SELECT_SINGLE);
		startDCC.setBounds(10, 35, 300, 30);
		startDCC.setFont(new Font("Century Gothic", Font.BOLD, 18));
		startDCC.setCalendarBackground(Color.WHITE);
		startDCC.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
		contentPane.add(startDCC);
		
		endLbl = new JLabel("End date: ");
		endLbl.setBounds(10, 75, 300, 25);
		endLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(endLbl);
		
		endDCC = new DateChooserCombo();
		endDCC.setBehavior(MultyModelBehavior.SELECT_SINGLE);
		endDCC.setBounds(10, 100, 300, 30);
		endDCC.setFont(new Font("Century Gothic", Font.BOLD, 18));
		endDCC.setCalendarBackground(Color.WHITE);
		endDCC.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
		contentPane.add(endDCC);
		
		playoffStartLbl = new JLabel("Playoff start date: ");
		playoffStartLbl.setBounds(10, 140, 300, 25);
		playoffStartLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(playoffStartLbl);
		
		playoffStartDCC = new DateChooserCombo();
		playoffStartDCC.setBehavior(MultyModelBehavior.SELECT_SINGLE);
		playoffStartDCC.setBounds(10, 165, 300, 30);
		playoffStartDCC.setFont(new Font("Century Gothic", Font.BOLD, 18));
		playoffStartDCC.setCalendarBackground(Color.WHITE);
		playoffStartDCC.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
		contentPane.add(playoffStartDCC);
		
		playoffEndLbl = new JLabel("Playoff end date: ");
		playoffEndLbl.setBounds(10, 215, 300, 25);
		playoffEndLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(playoffEndLbl);
		
		playoffEndDCC = new DateChooserCombo();
		playoffEndDCC.setBehavior(MultyModelBehavior.SELECT_SINGLE);
		playoffEndDCC.setBounds(10, 240, 300, 30);
		playoffEndDCC.setFont(new Font("Century Gothic", Font.BOLD, 18));
		playoffEndDCC.setCalendarBackground(Color.WHITE);
		playoffEndDCC.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
		contentPane.add(playoffEndDCC);
		
		numberOfGamesLbl = new JLabel("Number of games: ");
		numberOfGamesLbl.setBounds(10, 280, 300, 25);
		numberOfGamesLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(numberOfGamesLbl);
		
		Integer[] numbers = {50, 66, 72, 80, 82};
		
		numberOfGamesCB = new JComboBox<>(numbers);
		numberOfGamesCB.setBounds(10, 305, 300, 30);
		numberOfGamesCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		numberOfGamesCB.setSelectedItem(82);
		contentPane.add(numberOfGamesCB);
		
		saveBtn = new JButton("Save");
		saveBtn.setBounds(85, 345, 150, 30);
		saveBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		saveBtn.setBackground(Color.WHITE);
		contentPane.add(saveBtn);
	}
	
	private void initButtonListener() {
		
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addSeasonFormController.save();
			}
		});
		
	}
	
	public Date getStartDate() {
		return startDCC.getSelectedDate().getTime();
	}
	
	public Date getEndDate() {
		return endDCC.getSelectedDate().getTime();
	}
	
	public Date getPlayoffStartDate() {
		return playoffStartDCC.getSelectedDate().getTime();
	}
	
	public Date getPlayoffEndDate() {
		return playoffEndDCC.getSelectedDate().getTime();
	}
	
	public int getNumberOfGames() {
		return (Integer) numberOfGamesCB.getSelectedItem();
	}

}
