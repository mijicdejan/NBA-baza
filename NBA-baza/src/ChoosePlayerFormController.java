
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
