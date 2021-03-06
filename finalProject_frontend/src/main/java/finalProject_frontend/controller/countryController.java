package finalProject_frontend.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import finalProject_frontend.ReservationEventHandler;
import finalProject_frontend.domain.CovidStats;
import finalProject_frontend.service.CountryService;
@Controller
public class countryController {
    @Autowired
    private CountryService countryService;
    
    @Autowired
    private ReservationEventHandler eventHandler ;
    
    @GetMapping("/select")
    public String SelectCountry( Model model) {
        
        List<String> cList = countryService.getCountryList();
        cList.sort( Comparator.comparing( String::toString ) );
        model.addAttribute("countries", cList);
        return "selectCountry";
        
    }
    
    //http://localhost:8080/countrystats?country=France
    @GetMapping("/countrystats")
    public String GetCountryStats( Model model, @RequestParam(required = true) String country) {
        CovidStats stats = countryService.getCountryCovidStats(country);
        model.addAttribute("covidstats", stats);
        model.addAttribute("stateorcountry", "Country");
        System.out.println(stats.toString());
        
        countryService.requestReservation(country);
        stats.setPopulation(eventHandler.getPopulation());
        stats.setActivePercent(eventHandler.getPopulation());
        stats.setConfirmedPercent(eventHandler.getPopulation());
        stats.setDeathPercent(eventHandler.getPopulation());
        
        
        return "CovidStats";
        
    }

    // year-month-day (0 padded)
    //http://localhost:8080/countryondate/?country=US&date=2020-04-01
    @GetMapping("/countryondate")
    public String getCountryStatsOnDate( Model model, 
                                    @RequestParam(required = true) String country,
                                    @RequestParam(required = true) String date) {

        List<CovidStats> stats = countryService.getCountryCovidStatsBetweenDates(country,date,date);
        model.addAttribute("covidstats", stats);
        model.addAttribute("stateorcountry", "country");
        System.out.println(stats.toString());
        return "StatsList";
    }

    // year-month-day (0 padded)
    //http://localhost:8080/countrydaterange/?country=US&startDate=2020-04-01&endDate=2020-04-12
    @GetMapping("/countrydaterange")
    public String getCountryDateRange( Model model, 
                                    @RequestParam(required = true) String country,
                                    @RequestParam(required = true) String startDate,
                                    @RequestParam(required = true) String endDate) {

        List<CovidStats> stats = countryService.getCountryCovidStatsBetweenDates(country,startDate,endDate);
        model.addAttribute("covidstats", stats);
        model.addAttribute("stateorcountry", "Country");
        System.out.println(stats.toString());
        return "StatsList";
    }

    //http://localhost:8080/stateondate/?state=Florida&date=2020-04-01
    @GetMapping("/stateondate")
    public String getStateStatsOnDate( Model model, 
                                    @RequestParam(required = true) String state,
                                    @RequestParam(required = true) String date) {

        List<CovidStats> stats = countryService.getStateCovidStatsBetweenDates(state,date,date);
        model.addAttribute("covidstats", stats);
        model.addAttribute("stateorcountry", "State");
        System.out.println(stats.toString());
        return "StatsList";
    }

    // year-month-day (0 padded)
    //http://localhost:8080/statedaterange/?state=Florida&startDate=2020-04-01&endDate=2020-04-12
    @GetMapping("/statedaterange")
    public String getStateDateRange( Model model, 
                                    @RequestParam(required = true) String state,
                                    @RequestParam(required = true) String startDate,
                                    @RequestParam(required = true) String endDate) {

        List<CovidStats> stats = countryService.getStateCovidStatsBetweenDates(state,startDate,endDate);
        model.addAttribute("covidstats", stats);
        model.addAttribute("stateorcountry", "State");
        System.out.println(stats.toString());
        return "StatsList";
    }

    //http://localhost:8080/statestats?state=Idaho
    @GetMapping("/statestats")
    public String GetUSStateStats( Model model, @RequestParam(required = true) String state) {
        CovidStats stats = countryService.getUSStateCovidStats(state);
        model.addAttribute("covidstats", stats);
        model.addAttribute("stateorcountry", "State");
        System.out.println(stats.toString());
        return "CovidStats";
    }
    


    @GetMapping("/covid/countrylist")
    public ResponseEntity<List<String>> getCountryList() {

        List<String> countries = new ArrayList<String>();
        countries.sort( Comparator.comparing( String::toString ) );
        return new ResponseEntity<List<String>>(countries, HttpStatus.OK);	
    }
    
    @GetMapping("/covid/usstatelist")
    public ResponseEntity<List<String>> getUSStateList() {

        List<String> states = new ArrayList<String>();
        states.sort( Comparator.comparing( String::toString ) );
        return new ResponseEntity<List<String>>(states, HttpStatus.OK);	
    }
}
