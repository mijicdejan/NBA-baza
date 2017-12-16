import java.awt.event.ItemEvent;

public class AddGameFormController {
	
	private AddGameForm addGameForm;
	
	public AddGameFormController(AddGameForm addGameForm) {
		this.addGameForm = addGameForm;
	}
	
	public void scoreItemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.DESELECTED) {
			addGameForm.setEnabledScore(false);
		} else {
			addGameForm.setEnabledScore(true);
		}
	}
	
	public void arenaItemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.DESELECTED) {
			addGameForm.setEnabledArena(false);
		} else {
			addGameForm.setEnabledArena(true);
		}
	}

}
