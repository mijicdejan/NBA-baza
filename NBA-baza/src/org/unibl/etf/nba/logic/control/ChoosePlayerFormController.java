package org.unibl.etf.nba.logic.control;
import org.unibl.etf.nba.gui.view.ChoosePlayerForm;
import org.unibl.etf.nba.gui.view.EditPlayerForm;

public class ChoosePlayerFormController {
	
	private ChoosePlayerForm choosePlayerForm;
	
	public ChoosePlayerFormController(ChoosePlayerForm choosePlayerForm) {
		this.choosePlayerForm = choosePlayerForm;
	}
	
	public void edit() {
		EditPlayerForm epf = new EditPlayerForm(choosePlayerForm.getSelectedPlayer());
		epf.setVisible(true);
		choosePlayerForm.setVisible(false);
	}

}
