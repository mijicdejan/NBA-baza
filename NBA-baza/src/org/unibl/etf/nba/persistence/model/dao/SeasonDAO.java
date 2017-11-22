package org.unibl.etf.nba.persistence.model.dao;

import java.util.Date;

public interface SeasonDAO {
	
	public boolean addSeason(Date start, Date end, int n, Date playoffStart);
	public boolean addCompleteSeason(Date start, Date end, int mvp, int dp, int smoty, int roty, int mip, int nog, Date playoffStart, Date playoffEnd);
	public int getSeasonId(int seasonStart);

}
