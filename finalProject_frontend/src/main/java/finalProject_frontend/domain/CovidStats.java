package finalProject_frontend.domain;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.JsonNode;

@Entity

public class CovidStats {

    @Id
	@GeneratedValue
	@Column(name="ID")
    private long id;
    
    String country;
    int active = 0;
    int confirmed = 0;
    int deaths = 0;
    int recovered = 0;
    java.sql.Date lastUpdated;

    public CovidStats(String country, JsonNode json) {
        this.country = country;
        confirmed = json.get("confirmed").asInt();
        active = json.get("confirmed").asInt();
        deaths = json.get("recovered").asInt();
        String dateStr = json.get("lastUpdated").asText("2020-01-01");
        
        try {
          Date jDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
          lastUpdated = new java.sql.Date(jDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getActive() {
        return active;
    }


    public int getConfirmed() {
        return confirmed;
    }


    public int getDeaths() {
        return deaths;
    }


    public int getRecovered() {
        return recovered;
    }


    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getCountry() {
        return country;
    }

    public void setActive(int active) {
        this.active = active;
    }


    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }


    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }


    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }


    public void setLastUpdated(java.sql.Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
