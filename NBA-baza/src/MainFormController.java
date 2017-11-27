
public class MainFormController {
	
	private static boolean addPlayerFormOpened = false;
	
	public MainFormController() {
		
	}
	
	public static void createAddPlayerForm() {
		if(addPlayerFormOpened)
			return;
		
		AddPlayerForm apf = new AddPlayerForm();
		apf.setVisible(true);
		addPlayerFormOpened = true;
	}
	
	public static void resetAddPlayerFormOpened() {
		addPlayerFormOpened = false;
	}

}
