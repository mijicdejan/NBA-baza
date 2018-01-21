
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;

import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.RefereeDAO;
import org.unibl.etf.nba.persistence.model.dto.GameDTO;
import org.unibl.etf.nba.persistence.model.dto.RefereeDTO;

@SuppressWarnings("serial")
public class EditGameForm extends AddGameForm {
	
	private GameDTO game;
	
	private EditGameFormController editGameFormController;

	/**
	 * Create the frame.
	 */
	public EditGameForm(MainForm mainForm) {
		super(mainForm);
		
		editGameFormController = new EditGameFormController(this);
		
		game = mainForm.getSelectedGame();
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(game.getGameTime());
		SpinnerDateModel sdm = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.HOUR_OF_DAY);
		
		try {
			contentPane.remove(timeSpin);
			contentPane.revalidate();
			contentPane.repaint();
		} catch (Exception e) {
			
		}
		timeSpin = new JSpinner(sdm);
		JSpinner.DateEditor de = new JSpinner.DateEditor(timeSpin, "HH:mm");
		timeSpin.setEditor(de);
		timeSpin.setBounds(10, 35, 100, 30);
		timeSpin.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(timeSpin);
		
		homeTeamCB.setEnabled(false);
		homeTeamCB.addItem(game.getHomeTeam().getTeamNames().get(game.getSeason()));
		homeTeamCB.setSelectedItem(game.getHomeTeam().getTeamNames().get(game.getSeason()));
		awayTeamCB.setEnabled(false);
		awayTeamCB.addItem(game.getAwayTeam().getTeamNames().get(game.getSeason()));
		awayTeamCB.setSelectedItem(game.getAwayTeam().getTeamNames().get(game.getSeason()));
		
		DAOFactory factory = new MySQLDAOFactory();
		RefereeDAO refereeDAO = factory.getRefereeDAO();
		ArrayList<RefereeDTO> chosen = refereeDAO.getRefereesForGame(game);
		chosenRefereesList = new JList<>(new RefereeListModel(chosen));
		chosenRefereesList.setFont(new Font("Century Gothic", Font.BOLD, 15));
		chosenRefereesList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		chosenScroll.setViewportView(chosenRefereesList);
		contentPane.add(chosenScroll);
		addRefereeBtn.setEnabled(false);
		removeRefereeBtn.setEnabled(true);
		
		for(ActionListener a : saveBtn.getActionListeners()) {
			saveBtn.removeActionListener(a);
		}
		
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editGameFormController.save();
			}
		});
	}
	
	@Override
	protected void initAwayTeamCBValues() {
		
	}
	
	public GameDTO getGame() {
		return game;
	}

}
