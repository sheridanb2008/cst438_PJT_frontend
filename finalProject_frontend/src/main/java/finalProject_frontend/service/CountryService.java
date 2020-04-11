package finalProject_frontend.service;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
@Service
public class CountryService {
	@Autowired
	private Environment env;
	 @Autowired
	    private RabbitTemplate rabbitTemplate;
		
	    @Autowired
	    private FanoutExchange fanout;

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


}
