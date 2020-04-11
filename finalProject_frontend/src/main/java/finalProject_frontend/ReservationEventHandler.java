package finalProject_frontend;

import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RabbitListener(queues = "country-population-q1")
	public class ReservationEventHandler {

		ObjectMapper objectMapper = new ObjectMapper();
		
		private static final Logger log = 
	          LoggerFactory.getLogger(ReservationEventHandler.class);

		@RabbitHandler
		public void receive(String in) {
			System.out.println(in);
			try {
				com.fasterxml.jackson.databind.JsonNode jsonNode = objectMapper.readTree(in);
				com.fasterxml.jackson.databind.JsonNode populationNode = jsonNode.findValue("Population");
				int population = populationNode.asInt();
				System.out.println("The population is " + population);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			

		}
	}


