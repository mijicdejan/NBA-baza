package org.unibl.etf.nba.persistence.model.dto;

public class ArenaDTO {
	
	private int arenaId;
	private String name;
	private int capacity;
	private CityDTO city;
	
	public ArenaDTO(int arenaId, String name, int capacity, CityDTO city) {
		super();
		this.arenaId = arenaId;
		this.name = name;
		this.capacity = capacity;
		this.city = city;
	}

	public ArenaDTO(String name, int capacity, CityDTO city) {
		super();
		this.name = name;
		this.capacity = capacity;
		this.city = city;
	}

	public int getArenaId() {
		return arenaId;
	}

	public void setArenaId(int arenaId) {
		this.arenaId = arenaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public CityDTO getCity() {
		return city;
	}

	public void setCity(CityDTO city) {
		this.city = city;
	}

}
