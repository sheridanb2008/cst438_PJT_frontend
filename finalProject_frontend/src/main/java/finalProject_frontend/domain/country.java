package finalProject_frontend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="country")
public class country {
	
	@Id
	private String name;
	private int population;
	
	
	
	public country(String name, int population) {
		super();
		this.name = name;
		this.population = population;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	
}
