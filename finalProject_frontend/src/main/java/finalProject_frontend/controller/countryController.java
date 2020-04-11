package finalProject_frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import finalProject_frontend.service.CountryService;
@Controller
public class countryController {
	@Autowired
	private CountryService countryService;
	
	@GetMapping("/select")
	public String SelectCountry( Model model) {
		
		
		
		return "selectCountry";
		
	}
	
	@PostMapping("/country/post")
	public String createReservation(@RequestParam("country") String countryName1, Model model) {
		
		model.addAttribute("country", countryName1);
		
		String countryName2 = ("${db."+ countryName1 + "}");
		
		countryService.requestReservation(countryName1, countryName2);
		
		return "Stats";
	}
}
