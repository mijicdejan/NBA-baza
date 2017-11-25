package org.unibl.etf.nba.persistence.model.dto;

import java.util.ArrayList;
import java.util.Date;

public class PlayerDTO {
	
	private int id;
	private String firstName;
	private String lastName;
	private Date birthdate;
	private int height;
	private int weight;
	private ArrayList<String> positions;
	
	public PlayerDTO() {
		super();
	}
	
	public PlayerDTO(String firstName, String lastName, Date birthdate, int height, int weight, ArrayList<String> positions) {
		this.id = 0;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.height = height;
		this.weight = weight;
		this.positions = positions;
	}
	
	public PlayerDTO(int id, String firstName, String lastName, Date birthdate, int height, int weight) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.height = height;
		this.weight = weight;
		this.positions = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public ArrayList<String> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<String> positions) {
		this.positions = positions;
	}

}
