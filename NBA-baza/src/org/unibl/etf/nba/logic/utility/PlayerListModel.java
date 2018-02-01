package org.unibl.etf.nba.logic.utility;
import java.util.ArrayList;

import javax.swing.AbstractListModel;

import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;

@SuppressWarnings("serial")
public class PlayerListModel extends AbstractListModel<PlayerDTO> {
	
	private ArrayList<PlayerDTO> data = new ArrayList<>();
	
	public PlayerListModel() {
		
	}
	
	public PlayerListModel(ArrayList<PlayerDTO> data) {
		setData(data);
	}
	
	public void setData(ArrayList<PlayerDTO> data) {
		this.data = data;
		fireContentsChanged(this, 0, data.size() - 1);
	}
	
	public ArrayList<PlayerDTO> getData() {
		return data;
	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public PlayerDTO getElementAt(int index) {
		if (index >= 0 && index < data.size())
			return data.get(index);
		return null;
	}
	
	@Override
	public void fireContentsChanged(Object obj, int index1, int index2) {
		super.fireContentsChanged(obj, index1, index2);
	}

}