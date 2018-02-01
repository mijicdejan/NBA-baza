package org.unibl.etf.nba.logic.utility;
import java.util.ArrayList;

import javax.swing.AbstractListModel;

import org.unibl.etf.nba.persistence.model.dto.RefereeDTO;

@SuppressWarnings("serial")
public class RefereeListModel extends AbstractListModel<RefereeDTO> {
	
	private ArrayList<RefereeDTO> data = new ArrayList<>();
	
	public RefereeListModel() {
		
	}
	
	public RefereeListModel(ArrayList<RefereeDTO> data) {
		setData(data);
	}
	
	public void setData(ArrayList<RefereeDTO> data) {
		this.data = data;
		fireContentsChanged(this, 0, data.size() - 1);
	}
	
	public ArrayList<RefereeDTO> getData() {
		return data;
	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public RefereeDTO getElementAt(int index) {
		if (index >= 0 && index < data.size())
			return data.get(index);
		return null;
	}
	
	@Override
	public void fireContentsChanged(Object obj, int index1, int index2) {
		super.fireContentsChanged(obj, index1, index2);
	}

}
