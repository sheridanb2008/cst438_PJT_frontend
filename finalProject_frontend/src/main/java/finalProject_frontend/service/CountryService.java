package finalProject_frontend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

    public void requestReservation(String country) {

        String msg = "{\"country\": \"" + env.resolvePlaceholders(country) + "\"}";
        System.out.println("Sending message:" + msg);
        rabbitTemplate.convertSendAndReceive(fanout.getName(), "", // routing key none.
                msg);
    }

    public CovidStats getCountryCovidStats(String country) {

        ResponseEntity<JsonNode> response = restTemplate.getForEntity("http://localhost:8070/covid/latest/" + country,
                JsonNode.class);
        JsonNode json = response.getBody();
        CovidStats stats = new CovidStats(country, json);
        return stats;
    }

    public List<CovidStats> getCountryCovidStatsBetweenDates(String country, String startDate, String endDate) {

        String url = "http://localhost:8070/covid/daterange/country/" + country + "/?";
        url = url + "startDate=" + startDate + "&endDate=" + endDate;
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode json = response.getBody();
        System.out.print(json.toPrettyString());
        List<CovidStats> statsList = new ArrayList<CovidStats>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            statsList = mapper.readerFor(new TypeReference<List<CovidStats>>() {
            }).readValue(json);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //ObjectReader reader = mapper.readerFor(List.class);
        // use it
        //List<CovidStats> list = reader.readValue(json);
        return statsList;
    }

    public List<CovidStats> getStateCovidStatsBetweenDates(String state, String startDate, String endDate) {

        String url = "http://localhost:8070/covid/daterange/usstate/" + state + "/?";
        url = url + "startDate=" + startDate + "&endDate=" + endDate;
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode json = response.getBody();
        System.out.print(json.toPrettyString());
        List<CovidStats> statsList = new ArrayList<CovidStats>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            statsList = mapper.readerFor(new TypeReference<List<CovidStats>>() {
            }).readValue(json);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return statsList;
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

    public List<String> getCountryList() {
        String url = "http://localhost:8070/covid/countrylist";
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode json = response.getBody();
        System.out.print(json.toPrettyString());
        List<String> countryList = new ArrayList<String>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            countryList = mapper.readerFor(new TypeReference<List<String>>() {
            }).readValue(json);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return countryList;
    }

    public List<String> getStateList() {
        String url = "http://localhost:8070/covid/statelist";
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode json = response.getBody();
        System.out.print(json.toPrettyString());
        List<String> stateList = new ArrayList<String>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            stateList = mapper.readerFor(new TypeReference<List<String>>() {
            }).readValue(json);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stateList;
    }

}
