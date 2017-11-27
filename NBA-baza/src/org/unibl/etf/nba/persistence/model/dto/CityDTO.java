package org.unibl.etf.nba.persistence.model.dto;

public class CityDTO {
	
	private int cityId;
	private String name;
	private String state;
	private String country;
	
	public CityDTO(int cityId, String name, String state, String country) {
		super();
		this.cityId = cityId;
		this.name = name;
		this.state = state;
		this.country = country;
	}

	public CityDTO(String name, String state, String country) {
		super();
		this.name = name;
		this.state = state;
		this.country = country;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		if(state == null) {
			return name + ", " + country;
		}
		return name + ", " + state + ", " + country;
	}

}
