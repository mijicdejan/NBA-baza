package org.unibl.etf.nba.gui.view;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.nba.logic.control.MainFormController;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.FranchiseDAO;
import org.unibl.etf.nba.persistence.model.dao.GameDAO;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

@SuppressWarnings("serial")
public class PlayoffPictureForm extends JFrame {

	private JPanel contentPane;
	
	private JTextField firstRound1TF;
	private JTextField firstRound2TF;
	private JTextField firstRound3TF;
	private JTextField firstRound4TF;
	private JTextField firstRound5TF;
	private JTextField firstRound6TF;
	private JTextField firstRound7TF;
	private JTextField firstRound8TF;
	private JTextField firstRound9TF;
	private JTextField firstRound10TF;
	private JTextField firstRound11TF;
	private JTextField firstRound12TF;
	private JTextField firstRound13TF;
	private JTextField firstRound14TF;
	private JTextField firstRound15TF;
	private JTextField firstRound16TF;
	
	private JTextField firstRoundResult1TF;
	private JTextField firstRoundResult2TF;
	private JTextField firstRoundResult3TF;
	private JTextField firstRoundResult4TF;
	private JTextField firstRoundResult5TF;
	private JTextField firstRoundResult6TF;
	private JTextField firstRoundResult7TF;
	private JTextField firstRoundResult8TF;
	private JTextField firstRoundResult9TF;
	private JTextField firstRoundResult10TF;
	private JTextField firstRoundResult11TF;
	private JTextField firstRoundResult12TF;
	private JTextField firstRoundResult13TF;
	private JTextField firstRoundResult14TF;
	private JTextField firstRoundResult15TF;
	private JTextField firstRoundResult16TF;
	
	private JTextField conferenceSemis1TF;
	private JTextField conferenceSemis2TF;
	private JTextField conferenceSemis3TF;
	private JTextField conferenceSemis4TF;
	private JTextField conferenceSemis5TF;
	private JTextField conferenceSemis6TF;
	private JTextField conferenceSemis7TF;
	private JTextField conferenceSemis8TF;
	
	private JTextField conferenceSemisResult1TF;
	private JTextField conferenceSemisResult2TF;
	private JTextField conferenceSemisResult3TF;
	private JTextField conferenceSemisResult4TF;
	private JTextField conferenceSemisResult5TF;
	private JTextField conferenceSemisResult6TF;
	private JTextField conferenceSemisResult7TF;
	private JTextField conferenceSemisResult8TF;
	
	private JTextField conferenceFinals1TF;
	private JTextField conferenceFinals2TF;
	private JTextField conferenceFinals3TF;
	private JTextField conferenceFinals4TF;
	
	private JTextField conferenceFinalsResult1TF;
	private JTextField conferenceFinalsResult2TF;
	private JTextField conferenceFinalsResult3TF;
	private JTextField conferenceFinalsResult4TF;
	
	private JTextField finals1TF;
	private JTextField finals2TF;
	
	private JTextField finalsResult1TF;
	private JTextField finalsResult2TF;
	
	private SeasonDTO season;

	/**
	 * Create the frame.
	 */
	public PlayoffPictureForm(SeasonDTO season) {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetPlayoffPictureFormOpened();
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
		setBounds(100, 50, 975, 505);
		setBackground(new Color(255, 255, 255));
		setTitle("Playoff picture");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		this.season = season;
		
		initComponents();
		setComponentsValues();
	}
	
	private void initComponents() {		
		initFirstRound();
		initConferenceSemis();
		initConferenceFinals();
		initFinals();
	}
	
	private void initFirstRound() {
		firstRound1TF = new JTextField();
		firstRound1TF.setBounds(10, 10, 70, 30);
		firstRound1TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound1TF.setEnabled(false);
		firstRound1TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound1TF);
		
		firstRoundResult1TF = new JTextField();
		firstRoundResult1TF.setBounds(90, 10, 40, 30);
		firstRoundResult1TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult1TF.setEnabled(false);
		firstRoundResult1TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult1TF);
		
		firstRound2TF = new JTextField();
		firstRound2TF.setBounds(10, 40, 70, 30);
		firstRound2TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound2TF.setEnabled(false);
		firstRound2TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound2TF);
		
		firstRoundResult2TF = new JTextField();
		firstRoundResult2TF.setBounds(90, 40, 40, 30);
		firstRoundResult2TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult2TF.setEnabled(false);
		firstRoundResult2TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult2TF);
		
		firstRound3TF = new JTextField();
		firstRound3TF.setBounds(10, 140, 70, 30);
		firstRound3TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound3TF.setEnabled(false);
		firstRound3TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound3TF);
		
		firstRoundResult3TF = new JTextField();
		firstRoundResult3TF.setBounds(90, 140, 40, 30);
		firstRoundResult3TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult3TF.setEnabled(false);
		firstRoundResult3TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult3TF);
		
		firstRound4TF = new JTextField();
		firstRound4TF.setBounds(10, 170, 70, 30);
		firstRound4TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound4TF.setEnabled(false);
		firstRound4TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound4TF);
		
		firstRoundResult4TF = new JTextField();
		firstRoundResult4TF.setBounds(90, 170, 40, 30);
		firstRoundResult4TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult4TF.setEnabled(false);
		firstRoundResult4TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult4TF);
		
		firstRound5TF = new JTextField();
		firstRound5TF.setBounds(10, 270, 70, 30);
		firstRound5TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound5TF.setEnabled(false);
		firstRound5TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound5TF);
		
		firstRoundResult5TF = new JTextField();
		firstRoundResult5TF.setBounds(90, 270, 40, 30);
		firstRoundResult5TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult5TF.setEnabled(false);
		firstRoundResult5TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult5TF);
		
		firstRound6TF = new JTextField();
		firstRound6TF.setBounds(10, 300, 70, 30);
		firstRound6TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound6TF.setEnabled(false);
		firstRound6TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound6TF);
		
		firstRoundResult6TF = new JTextField();
		firstRoundResult6TF.setBounds(90, 300, 40, 30);
		firstRoundResult6TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult6TF.setEnabled(false);
		firstRoundResult6TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult6TF);
		
		firstRound7TF = new JTextField();
		firstRound7TF.setBounds(10, 400, 70, 30);
		firstRound7TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound7TF.setEnabled(false);
		firstRound7TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound7TF);
		
		firstRoundResult7TF = new JTextField();
		firstRoundResult7TF.setBounds(90, 400, 40, 30);
		firstRoundResult7TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult7TF.setEnabled(false);
		firstRoundResult7TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult7TF);
		
		firstRound8TF = new JTextField();
		firstRound8TF.setBounds(10, 430, 70, 30);
		firstRound8TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound8TF.setEnabled(false);
		firstRound8TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound8TF);
		
		firstRoundResult8TF = new JTextField();
		firstRoundResult8TF.setBounds(90, 430, 40, 30);
		firstRoundResult8TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult8TF.setEnabled(false);
		firstRoundResult8TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult8TF);
		
		firstRound9TF = new JTextField();
		firstRound9TF.setBounds(890, 10, 70, 30);
		firstRound9TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound9TF.setEnabled(false);
		firstRound9TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound9TF);
		
		firstRoundResult9TF = new JTextField();
		firstRoundResult9TF.setBounds(840, 10, 40, 30);
		firstRoundResult9TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult9TF.setEnabled(false);
		firstRoundResult9TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult9TF);
		
		firstRound10TF = new JTextField();
		firstRound10TF.setBounds(890, 40, 70, 30);
		firstRound10TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound10TF.setEnabled(false);
		firstRound10TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound10TF);
		
		firstRoundResult10TF = new JTextField();
		firstRoundResult10TF.setBounds(840, 40, 40, 30);
		firstRoundResult10TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult10TF.setEnabled(false);
		firstRoundResult10TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult10TF);
		
		firstRound11TF = new JTextField();
		firstRound11TF.setBounds(890, 140, 70, 30);
		firstRound11TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound11TF.setEnabled(false);
		firstRound11TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound11TF);
		
		firstRoundResult11TF = new JTextField();
		firstRoundResult11TF.setBounds(840, 140, 40, 30);
		firstRoundResult11TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult11TF.setEnabled(false);
		firstRoundResult11TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult11TF);
		
		firstRound12TF = new JTextField();
		firstRound12TF.setBounds(890, 170, 70, 30);
		firstRound12TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound12TF.setEnabled(false);
		firstRound12TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound12TF);
		
		firstRoundResult12TF = new JTextField();
		firstRoundResult12TF.setBounds(840, 170, 40, 30);
		firstRoundResult12TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult12TF.setEnabled(false);
		firstRoundResult12TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult12TF);
		
		firstRound13TF = new JTextField();
		firstRound13TF.setBounds(890, 270, 70, 30);
		firstRound13TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound13TF.setEnabled(false);
		firstRound13TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound13TF);
		
		firstRoundResult13TF = new JTextField();
		firstRoundResult13TF.setBounds(840, 270, 40, 30);
		firstRoundResult13TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult13TF.setEnabled(false);
		firstRoundResult13TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult13TF);
		
		firstRound14TF = new JTextField();
		firstRound14TF.setBounds(890, 300, 70, 30);
		firstRound14TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound14TF.setEnabled(false);
		firstRound14TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound14TF);
		
		firstRoundResult14TF = new JTextField();
		firstRoundResult14TF.setBounds(840, 300, 40, 30);
		firstRoundResult14TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult14TF.setEnabled(false);
		firstRoundResult14TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult14TF);
		
		firstRound15TF = new JTextField();
		firstRound15TF.setBounds(890, 400, 70, 30);
		firstRound15TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound15TF.setEnabled(false);
		firstRound15TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound15TF);
		
		firstRoundResult15TF = new JTextField();
		firstRoundResult15TF.setBounds(840, 400, 40, 30);
		firstRoundResult15TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult15TF.setEnabled(false);
		firstRoundResult15TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult15TF);
		
		firstRound16TF = new JTextField();
		firstRound16TF.setBounds(890, 430, 70, 30);
		firstRound16TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRound16TF.setEnabled(false);
		firstRound16TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRound16TF);
		
		firstRoundResult16TF = new JTextField();
		firstRoundResult16TF.setBounds(840, 430, 40, 30);
		firstRoundResult16TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		firstRoundResult16TF.setEnabled(false);
		firstRoundResult16TF.setDisabledTextColor(Color.black);
		contentPane.add(firstRoundResult16TF);
	}
	
	private void initConferenceSemis() {
		conferenceSemis1TF = new JTextField();
		conferenceSemis1TF.setBounds(150, 75, 70, 30);
		conferenceSemis1TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemis1TF.setEnabled(false);
		conferenceSemis1TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemis1TF);
		
		conferenceSemisResult1TF = new JTextField();
		conferenceSemisResult1TF.setBounds(230, 75, 40, 30);
		conferenceSemisResult1TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemisResult1TF.setEnabled(false);
		conferenceSemisResult1TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemisResult1TF);
		
		conferenceSemis2TF = new JTextField();
		conferenceSemis2TF.setBounds(150, 105, 70, 30);
		conferenceSemis2TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemis2TF.setEnabled(false);
		conferenceSemis2TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemis2TF);
		
		conferenceSemisResult2TF = new JTextField();
		conferenceSemisResult2TF.setBounds(230, 105, 40, 30);
		conferenceSemisResult2TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemisResult2TF.setEnabled(false);
		conferenceSemisResult2TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemisResult2TF);
		
		conferenceSemis3TF = new JTextField();
		conferenceSemis3TF.setBounds(150, 335, 70, 30);
		conferenceSemis3TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemis3TF.setEnabled(false);
		conferenceSemis3TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemis3TF);
		
		conferenceSemisResult3TF = new JTextField();
		conferenceSemisResult3TF.setBounds(230, 335, 40, 30);
		conferenceSemisResult3TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemisResult3TF.setEnabled(false);
		conferenceSemisResult3TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemisResult3TF);
		
		conferenceSemis4TF = new JTextField();
		conferenceSemis4TF.setBounds(150, 365, 70, 30);
		conferenceSemis4TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemis4TF.setEnabled(false);
		conferenceSemis4TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemis4TF);
		
		conferenceSemisResult4TF = new JTextField();
		conferenceSemisResult4TF.setBounds(230, 365, 40, 30);
		conferenceSemisResult4TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemisResult4TF.setEnabled(false);
		conferenceSemisResult4TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemisResult4TF);
		
		conferenceSemis5TF = new JTextField();
		conferenceSemis5TF.setBounds(740, 75, 70, 30);
		conferenceSemis5TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemis5TF.setEnabled(false);
		conferenceSemis5TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemis5TF);
		
		conferenceSemisResult5TF = new JTextField();
		conferenceSemisResult5TF.setBounds(690, 75, 40, 30);
		conferenceSemisResult5TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemisResult5TF.setEnabled(false);
		conferenceSemisResult5TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemisResult5TF);
		
		conferenceSemis6TF = new JTextField();
		conferenceSemis6TF.setBounds(740, 105, 70, 30);
		conferenceSemis6TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemis6TF.setEnabled(false);
		conferenceSemis6TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemis6TF);
		
		conferenceSemisResult6TF = new JTextField();
		conferenceSemisResult6TF.setBounds(690, 105, 40, 30);
		conferenceSemisResult6TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemisResult6TF.setEnabled(false);
		conferenceSemisResult6TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemisResult6TF);
		
		conferenceSemis7TF = new JTextField();
		conferenceSemis7TF.setBounds(740, 335, 70, 30);
		conferenceSemis7TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemis7TF.setEnabled(false);
		conferenceSemis7TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemis7TF);
		
		conferenceSemisResult7TF = new JTextField();
		conferenceSemisResult7TF.setBounds(690, 335, 40, 30);
		conferenceSemisResult7TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemisResult7TF.setEnabled(false);
		conferenceSemisResult7TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemisResult7TF);
		
		conferenceSemis8TF = new JTextField();
		conferenceSemis8TF.setBounds(740, 365, 70, 30);
		conferenceSemis8TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemis8TF.setEnabled(false);
		conferenceSemis8TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemis8TF);
		
		conferenceSemisResult8TF = new JTextField();
		conferenceSemisResult8TF.setBounds(690, 365, 40, 30);
		conferenceSemisResult8TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceSemisResult8TF.setEnabled(false);
		conferenceSemisResult8TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceSemisResult8TF);
	}
	
	private void initConferenceFinals() {
		conferenceFinals1TF = new JTextField();
		conferenceFinals1TF.setBounds(290, 205, 70, 30);
		conferenceFinals1TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceFinals1TF.setEnabled(false);
		conferenceFinals1TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceFinals1TF);
		
		conferenceFinalsResult1TF = new JTextField();
		conferenceFinalsResult1TF.setBounds(370, 205, 40, 30);
		conferenceFinalsResult1TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceFinalsResult1TF.setEnabled(false);
		conferenceFinalsResult1TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceFinalsResult1TF);
		
		conferenceFinals2TF = new JTextField();
		conferenceFinals2TF.setBounds(290, 235, 70, 30);
		conferenceFinals2TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceFinals2TF.setEnabled(false);
		conferenceFinals2TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceFinals2TF);
		
		conferenceFinalsResult2TF = new JTextField();
		conferenceFinalsResult2TF.setBounds(370, 235, 40, 30);
		conferenceFinalsResult2TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceFinalsResult2TF.setEnabled(false);
		conferenceFinalsResult2TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceFinalsResult2TF);
		
		conferenceFinals3TF = new JTextField();
		conferenceFinals3TF.setBounds(590, 205, 70, 30);
		conferenceFinals3TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceFinals3TF.setEnabled(false);
		conferenceFinals3TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceFinals3TF);
		
		conferenceFinalsResult3TF = new JTextField();
		conferenceFinalsResult3TF.setBounds(540, 205, 40, 30);
		conferenceFinalsResult3TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceFinalsResult3TF.setEnabled(false);
		conferenceFinalsResult3TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceFinalsResult3TF);
		
		conferenceFinals4TF = new JTextField();
		conferenceFinals4TF.setBounds(590, 235, 70, 30);
		conferenceFinals4TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceFinals4TF.setEnabled(false);
		conferenceFinals4TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceFinals4TF);
		
		conferenceFinalsResult4TF = new JTextField();
		conferenceFinalsResult4TF.setBounds(540, 235, 40, 30);
		conferenceFinalsResult4TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		conferenceFinalsResult4TF.setEnabled(false);
		conferenceFinalsResult4TF.setDisabledTextColor(Color.black);
		contentPane.add(conferenceFinalsResult4TF);
	}
	
	private void initFinals() {
		finals1TF = new JTextField();
		finals1TF.setBounds(430, 170, 90, 30);
		finals1TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		finals1TF.setEnabled(false);
		finals1TF.setDisabledTextColor(Color.black);
		contentPane.add(finals1TF);
		
		finalsResult1TF = new JTextField();
		finalsResult1TF.setBounds(455, 205, 40, 30);
		finalsResult1TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		finalsResult1TF.setEnabled(false);
		finalsResult1TF.setDisabledTextColor(Color.black);
		contentPane.add(finalsResult1TF);
		
		finals2TF = new JTextField();
		finals2TF.setBounds(430, 270, 90, 30);
		finals2TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		finals2TF.setEnabled(false);
		finals2TF.setDisabledTextColor(Color.black);
		contentPane.add(finals2TF);
		
		finalsResult2TF = new JTextField();
		finalsResult2TF.setBounds(455, 235, 40, 30);
		finalsResult2TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		finalsResult2TF.setEnabled(false);
		finalsResult2TF.setDisabledTextColor(Color.black);
		contentPane.add(finalsResult2TF);
	}
	
	private void setComponentsValues() {
		DAOFactory factory = new MySQLDAOFactory();
		FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
		GameDAO gameDAO = factory.getGameDAO();
		
		ArrayList<FranchiseDTO> westernFranchises = franchiseDAO.getAllFranchisesFromConference("Western");
		Collections.sort(westernFranchises, new Comparator<FranchiseDTO>() {
			@Override
			public int compare(FranchiseDTO o1, FranchiseDTO o2) {
				int o1played = gameDAO.getNumberOfGamesForTeamInSeason(o1, season);
				int o2played = gameDAO.getNumberOfGamesForTeamInSeason(o2, season);
				int o1wins = gameDAO.getNumberOfWinsForTeamInSeason(o1, season);
				int o2wins = gameDAO.getNumberOfWinsForTeamInSeason(o2, season);
				double o1pct = (o1played == 0) ? 0 : (double) o1wins / o1played;
				double o2pct = (o2played == 0) ? 0 : (double) o2wins / o2played;
				if(o1pct > o2pct) {
					return -1;
				} else if(o1pct < o2pct) {
					return 1;
				}
				return 0;
			}
		});
		firstRound1TF.setText(westernFranchises.get(0).getFranchiseAbrv());
		firstRound2TF.setText(westernFranchises.get(7).getFranchiseAbrv());
		firstRoundResult1TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(westernFranchises.get(0), westernFranchises.get(7), season)));
		firstRoundResult2TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(westernFranchises.get(7), westernFranchises.get(0), season)));
		
		firstRound3TF.setText(westernFranchises.get(4).getFranchiseAbrv());
		firstRound4TF.setText(westernFranchises.get(3).getFranchiseAbrv());
		firstRoundResult3TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(westernFranchises.get(4), westernFranchises.get(3), season)));
		firstRoundResult4TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(westernFranchises.get(3), westernFranchises.get(4), season)));
		
		firstRound5TF.setText(westernFranchises.get(2).getFranchiseAbrv());
		firstRound6TF.setText(westernFranchises.get(5).getFranchiseAbrv());
		firstRoundResult5TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(westernFranchises.get(2), westernFranchises.get(5), season)));
		firstRoundResult6TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(westernFranchises.get(5), westernFranchises.get(2), season)));
		
		firstRound7TF.setText(westernFranchises.get(6).getFranchiseAbrv());
		firstRound8TF.setText(westernFranchises.get(1).getFranchiseAbrv());
		firstRoundResult7TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(westernFranchises.get(6), westernFranchises.get(1), season)));
		firstRoundResult8TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(westernFranchises.get(1), westernFranchises.get(6), season)));
		
		if(gameDAO.getWinnerOfPlayoffSeries(westernFranchises.get(0), westernFranchises.get(7), season) != null) {
			conferenceSemis1TF.setText(gameDAO.getWinnerOfPlayoffSeries(westernFranchises.get(0), westernFranchises.get(7), season).getFranchiseAbrv());
		}
		
		if(gameDAO.getWinnerOfPlayoffSeries(westernFranchises.get(4), westernFranchises.get(3), season) != null) {
			conferenceSemis2TF.setText(gameDAO.getWinnerOfPlayoffSeries(westernFranchises.get(4), westernFranchises.get(3), season).getFranchiseAbrv());
		}
		
		if(gameDAO.getWinnerOfPlayoffSeries(westernFranchises.get(2), westernFranchises.get(5), season) != null) {
			conferenceSemis3TF.setText(gameDAO.getWinnerOfPlayoffSeries(westernFranchises.get(2), westernFranchises.get(5), season).getFranchiseAbrv());
		}
		
		if(gameDAO.getWinnerOfPlayoffSeries(westernFranchises.get(1), westernFranchises.get(6), season) != null) {
			conferenceSemis4TF.setText(gameDAO.getWinnerOfPlayoffSeries(westernFranchises.get(1), westernFranchises.get(6), season).getFranchiseAbrv());
		}
		
		if(!"".equals(conferenceSemis1TF.getText()) && !"".equals(conferenceSemis2TF.getText())) {
			conferenceSemisResult1TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis1TF.getText()), franchiseDAO.getFranchise(conferenceSemis2TF.getText()), season)));
			conferenceSemisResult2TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis2TF.getText()), franchiseDAO.getFranchise(conferenceSemis1TF.getText()), season)));
		}
		
		if(!"".equals(conferenceSemis3TF.getText()) && !"".equals(conferenceSemis4TF.getText())) {
			conferenceSemisResult3TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis3TF.getText()), franchiseDAO.getFranchise(conferenceSemis4TF.getText()), season)));
			conferenceSemisResult4TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis4TF.getText()), franchiseDAO.getFranchise(conferenceSemis3TF.getText()), season)));
		}
		
		if(!"".equals(conferenceSemis1TF.getText()) && !"".equals(conferenceSemis2TF.getText()) && gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis1TF.getText()), franchiseDAO.getFranchise(conferenceSemis2TF.getText()), season) != null) {
			conferenceFinals1TF.setText(gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis1TF.getText()), franchiseDAO.getFranchise(conferenceSemis2TF.getText()), season).getFranchiseAbrv());
		}
		
		if(!"".equals(conferenceSemis3TF.getText()) && !"".equals(conferenceSemis4TF.getText()) && gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis3TF.getText()), franchiseDAO.getFranchise(conferenceSemis4TF.getText()), season) != null) {
			conferenceFinals2TF.setText(gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis3TF.getText()), franchiseDAO.getFranchise(conferenceSemis4TF.getText()), season).getFranchiseAbrv());
		}
		
		if(!"".equals(conferenceFinals1TF.getText()) && !"".equals(conferenceFinals2TF.getText())) {
			conferenceFinalsResult1TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceFinals1TF.getText()), franchiseDAO.getFranchise(conferenceFinals2TF.getText()), season)));
			conferenceFinalsResult2TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceFinals2TF.getText()), franchiseDAO.getFranchise(conferenceFinals1TF.getText()), season)));
		}
		
		if(!"".equals(conferenceFinals1TF.getText()) && !"".equals(conferenceFinals2TF.getText()) && gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceFinals1TF.getText()), franchiseDAO.getFranchise(conferenceFinals2TF.getText()), season) != null) {
			finals1TF.setText(gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceFinals1TF.getText()), franchiseDAO.getFranchise(conferenceFinals2TF.getText()), season).getFranchiseAbrv());
		}
		
		ArrayList<FranchiseDTO> easternFranchises = franchiseDAO.getAllFranchisesFromConference("Eastern");
		Collections.sort(easternFranchises, new Comparator<FranchiseDTO>() {
			@Override
			public int compare(FranchiseDTO o1, FranchiseDTO o2) {
				int o1played = gameDAO.getNumberOfGamesForTeamInSeason(o1, season);
				int o2played = gameDAO.getNumberOfGamesForTeamInSeason(o2, season);
				int o1wins = gameDAO.getNumberOfWinsForTeamInSeason(o1, season);
				int o2wins = gameDAO.getNumberOfWinsForTeamInSeason(o2, season);
				double o1pct = (o1played == 0) ? 0 : (double) o1wins / o1played;
				double o2pct = (o2played == 0) ? 0 : (double) o2wins / o2played;
				if(o1pct > o2pct) {
					return -1;
				} else if(o1pct < o2pct) {
					return 1;
				}
				return 0;
			}
		});
		firstRound9TF.setText(easternFranchises.get(0).getFranchiseAbrv());
		firstRound10TF.setText(easternFranchises.get(7).getFranchiseAbrv());
		firstRoundResult9TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(easternFranchises.get(0), easternFranchises.get(7), season)));
		firstRoundResult10TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(easternFranchises.get(7), easternFranchises.get(0), season)));
		
		firstRound11TF.setText(easternFranchises.get(4).getFranchiseAbrv());
		firstRound12TF.setText(easternFranchises.get(3).getFranchiseAbrv());
		firstRoundResult11TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(easternFranchises.get(4), easternFranchises.get(3), season)));
		firstRoundResult12TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(easternFranchises.get(3), easternFranchises.get(4), season)));
		
		firstRound13TF.setText(easternFranchises.get(2).getFranchiseAbrv());
		firstRound14TF.setText(easternFranchises.get(5).getFranchiseAbrv());
		firstRoundResult13TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(easternFranchises.get(2), easternFranchises.get(5), season)));
		firstRoundResult14TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(easternFranchises.get(5), easternFranchises.get(2), season)));
		
		firstRound15TF.setText(easternFranchises.get(6).getFranchiseAbrv());
		firstRound16TF.setText(easternFranchises.get(1).getFranchiseAbrv());
		firstRoundResult15TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(easternFranchises.get(6), easternFranchises.get(1), season)));
		firstRoundResult16TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(easternFranchises.get(1), easternFranchises.get(6), season)));
		
		if(gameDAO.getWinnerOfPlayoffSeries(easternFranchises.get(0), easternFranchises.get(7), season) != null) {
			conferenceSemis5TF.setText(gameDAO.getWinnerOfPlayoffSeries(easternFranchises.get(0), easternFranchises.get(7), season).getFranchiseAbrv());
		}
		
		if(gameDAO.getWinnerOfPlayoffSeries(easternFranchises.get(4), easternFranchises.get(3), season) != null) {
			conferenceSemis6TF.setText(gameDAO.getWinnerOfPlayoffSeries(easternFranchises.get(4), easternFranchises.get(3), season).getFranchiseAbrv());
		}
		
		if(gameDAO.getWinnerOfPlayoffSeries(easternFranchises.get(2), easternFranchises.get(5), season) != null) {
			conferenceSemis7TF.setText(gameDAO.getWinnerOfPlayoffSeries(easternFranchises.get(2), easternFranchises.get(5), season).getFranchiseAbrv());
		}
		
		if(gameDAO.getWinnerOfPlayoffSeries(easternFranchises.get(1), easternFranchises.get(6), season) != null) {
			conferenceSemis8TF.setText(gameDAO.getWinnerOfPlayoffSeries(easternFranchises.get(1), easternFranchises.get(6), season).getFranchiseAbrv());
		}
		
		if(!"".equals(conferenceSemis5TF.getText()) && !"".equals(conferenceSemis6TF.getText())) {
			conferenceSemisResult5TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis5TF.getText()), franchiseDAO.getFranchise(conferenceSemis6TF.getText()), season)));
			conferenceSemisResult6TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis6TF.getText()), franchiseDAO.getFranchise(conferenceSemis5TF.getText()), season)));
		}
		
		if(!"".equals(conferenceSemis7TF.getText()) && !"".equals(conferenceSemis8TF.getText())) {
			conferenceSemisResult7TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis7TF.getText()), franchiseDAO.getFranchise(conferenceSemis8TF.getText()), season)));
			conferenceSemisResult8TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis8TF.getText()), franchiseDAO.getFranchise(conferenceSemis7TF.getText()), season)));
		}
		
		if(!"".equals(conferenceSemis5TF.getText()) && !"".equals(conferenceSemis6TF.getText()) && gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis5TF.getText()), franchiseDAO.getFranchise(conferenceSemis6TF.getText()), season) != null) {
			conferenceFinals3TF.setText(gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis5TF.getText()), franchiseDAO.getFranchise(conferenceSemis6TF.getText()), season).getFranchiseAbrv());
		}
		
		if(!"".equals(conferenceSemis7TF.getText()) && !"".equals(conferenceSemis8TF.getText()) && gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis7TF.getText()), franchiseDAO.getFranchise(conferenceSemis8TF.getText()), season) != null) {
			conferenceFinals4TF.setText(gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceSemis7TF.getText()), franchiseDAO.getFranchise(conferenceSemis8TF.getText()), season).getFranchiseAbrv());
		}
		
		if(!"".equals(conferenceFinals3TF.getText()) && !"".equals(conferenceFinals4TF.getText())) {
			conferenceFinalsResult3TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceFinals3TF.getText()), franchiseDAO.getFranchise(conferenceFinals4TF.getText()), season)));
			conferenceFinalsResult4TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(conferenceFinals4TF.getText()), franchiseDAO.getFranchise(conferenceFinals3TF.getText()), season)));
		}
		
		if(!"".equals(conferenceFinals3TF.getText()) && !"".equals(conferenceFinals4TF.getText()) && gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceFinals3TF.getText()), franchiseDAO.getFranchise(conferenceFinals4TF.getText()), season) != null) {
			finals2TF.setText(gameDAO.getWinnerOfPlayoffSeries(franchiseDAO.getFranchise(conferenceFinals3TF.getText()), franchiseDAO.getFranchise(conferenceFinals4TF.getText()), season).getFranchiseAbrv());
		}
		
		if(!"".equals(finals1TF.getText()) && !"".equals(finals2TF.getText())) {
			finalsResult1TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(finals1TF.getText()), franchiseDAO.getFranchise(finals2TF.getText()), season)));
			finalsResult2TF.setText(String.valueOf(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(franchiseDAO.getFranchise(finals2TF.getText()), franchiseDAO.getFranchise(finals1TF.getText()), season)));
		}
	}

}
