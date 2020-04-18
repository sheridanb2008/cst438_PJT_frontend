package finalProject_frontend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class country {
	
	@Id
	private int population;
	
	
	
	public country(int population) {
		super();
		this.population = population;
	}
	
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	
}
