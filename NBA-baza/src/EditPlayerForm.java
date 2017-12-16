import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;

@SuppressWarnings("serial")
public class EditPlayerForm extends AddPlayerForm {
	
	private JButton saveEditBtn;
	
	private PlayerDTO player;
	private EditPlayerFormController editPlayerFormController;

	/**
	 * Create the frame.
	 */
	public EditPlayerForm(PlayerDTO player) {
		super();
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetChoosePlayerFormOpened();
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
		
		setTitle("Edit player");
		
		this.player = player;
		editPlayerFormController = new EditPlayerFormController(this);
		
		contentPane.remove(saveBtn);
		
		saveEditBtn = new JButton("Save");
		saveEditBtn.setBounds(85, 490, 150, 30);
		saveEditBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		saveEditBtn.setBackground(Color.WHITE);
		contentPane.add(saveEditBtn);
		
		firstNameTF.setText(player.getFirstName());
		lastNameTF.setText(player.getLastName());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(player.getBirthdate());
		dateChooserCombo.setSelectedDate(calendar);
		heightCB.setSelectedItem(player.getHeight());
		weightCB.setSelectedItem(player.getWeight());
		if(player.getBirthplace() == null) {
			birthplaceCB.setSelectedItem("");
		} else {
			birthplaceCB.setSelectedItem(player.getBirthplace().toString());
		}
		for(String position : player.getPositions()) {
			if("PG".equals(position)) {
				pgBox.setSelected(true);
			} else if("SG".equals(position)) {
				sgBox.setSelected(true);
			} else if("SF".equals(position)) {
				sfBox.setSelected(true);
			} else if("PF".equals(position)) {
				pfBox.setSelected(true);
			} else if("C".equals(position)) {
				cBox.setSelected(true);
			}
		}
		saveEditBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editPlayerFormController.save();
			}
		});
	}
	
	public PlayerDTO getPlayer() {
		player.setFirstName(firstNameTF.getText());
		player.setLastName(lastNameTF.getText());
		player.setBirthdate(dateChooserCombo.getSelectedDate().getTime());
		player.setHeight((Integer) heightCB.getSelectedItem());
		player.setWeight((Integer) weightCB.getSelectedItem());
		player.setBirthplace(citiesMap.get(birthplaceCB.getSelectedItem()));
		ArrayList<String> positions = new ArrayList<>();
		for(JCheckBox cb : checkedList) {
			positions.add(cb.getText());
		}
		player.setPositions(positions);
		return player;
	}

}
