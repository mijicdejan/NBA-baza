import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

@SuppressWarnings("serial")
public class AddPlayoffGameForm extends AddGameForm {
	
	private AddPlayoffGameFormController addPlayoffGameFormController;
	
	private PlayoffForm playoffForm;
	
	private FranchiseDTO homeTeam;
	private FranchiseDTO awayTeam;

	/**
	 * Create the frame.
	 */
	public AddPlayoffGameForm(SeasonDTO season, Date date, FranchiseDTO homeTeam, FranchiseDTO awayTeam, PlayoffForm playoffForm) {
		super(season, date);
		
		addPlayoffGameFormController = new AddPlayoffGameFormController(this);
		this.playoffForm = playoffForm;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		
		homeTeamCB.removeAllItems();
		awayTeamCB.removeAllItems();
		homeTeamCB.setEnabled(false);
		homeTeamCB.addItem(homeTeam.getTeamNames().get(season));
		homeTeamCB.setSelectedItem(homeTeam.getTeamNames().get(season));
		awayTeamCB.setEnabled(false);
		awayTeamCB.addItem(awayTeam.getTeamNames().get(season));
		awayTeamCB.setSelectedItem(awayTeam.getTeamNames().get(season));
		
		for(ActionListener a : saveBtn.getActionListeners()) {
			saveBtn.removeActionListener(a);
		}
		
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addPlayoffGameFormController.save();
			}
		});
	}
	
	public PlayoffForm getPlayoffForm() {
		return playoffForm;
	}
	
	@Override
	public FranchiseDTO getHomeTeam() {
		return homeTeam;
	}
	
	@Override
	public FranchiseDTO getAwayTeam() {
		return awayTeam;
	}

}
