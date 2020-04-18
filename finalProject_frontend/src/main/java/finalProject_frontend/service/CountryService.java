package finalProject_frontend.service;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import finalProject_frontend.domain.CovidStats;
@Service
public class CountryService {
    
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;
     @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private FanoutExchange fanout;

    public CountryService() {
        this.restTemplate = new RestTemplate();
    }

    public void requestReservation(String countryName1, String countryName2) {			
        
        String msg  = "{\"cityName1\": \""+ countryName1 + 
                "\", \"cityName2\": \"" + env.resolvePlaceholders(countryName2)+
                "\"}" ;
        System.out.println("Sending message:"+msg);
        rabbitTemplate.convertSendAndReceive(
                fanout.getName(), 
                "",   // routing key none.
                msg);
    }

    public CovidStats getCountryCovidStats(String country) {
        
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(
            "http://localhost:8070/covid/latest/" + country,
            JsonNode.class);
        JsonNode json = response.getBody();
        CovidStats stats = new CovidStats(country,json);

        return stats;
    }

    public CovidStats getUSStateCovidStats(String state) {
        
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(
            "http://localhost:8070/covid/bydate/usstate/" + state,
            JsonNode.class);
        JsonNode json = response.getBody();
        //We'll cheat and use the same object for State instead of the country name
        CovidStats stats = new CovidStats(state,json);

        return stats;
    }

}
