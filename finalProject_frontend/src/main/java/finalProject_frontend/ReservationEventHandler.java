package finalProject_frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.jayway.jsonpath.internal.filter.ValueNode.JsonNode;

@RabbitListener(queues = "country-population-q1")
	public class ReservationEventHandler {

		private static final Logger log = 
	          LoggerFactory.getLogger(ReservationEventHandler.class);

		@RabbitHandler
		public void receive(String in) {
			System.out.println(in);
			log.info(" [ReservationEventHandler] Received '" + 
	                    in + "'");
			int test = in.indexOf(1);
			
			System.out.println(in);
			log.info(" [ReservationEventHandler] Received test value of '" + 
	                    test + "'");
		}
	}


