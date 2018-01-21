package org.unibl.etf.nba.persistence.model.dto;

public class RefereeDTO {
	
	private int refereeId;
	private String firstName;
	private String lastName;
	private CityDTO city;
	
	public RefereeDTO() {
		super();
		refereeId = 0;
		firstName = null;
		lastName = null;
		city = null;
	}

	public RefereeDTO(int refereeId, String firstName, String lastName, CityDTO city) {
		super();
		this.refereeId = refereeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
	}

	public RefereeDTO(String firstName, String lastName, CityDTO city) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
	}

	public int getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(int refereeId) {
		this.refereeId = refereeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public CityDTO getCity() {
		return city;
	}

	public void setCity(CityDTO city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + refereeId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefereeDTO other = (RefereeDTO) obj;
		if (refereeId != other.refereeId)
			return false;
		return true;
	}

}
