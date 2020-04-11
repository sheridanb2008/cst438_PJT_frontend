package finalProject_frontend;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigReceiver {
	@Bean
	public FanoutExchange fanReturnPopulation() {
		return new FanoutExchange("country-population");
	}
	@Bean
	public Queue queue1() {
		return new Queue("country-population-q1");
	}
	@Bean
	public Binding binding1(FanoutExchange fanReturnPopulation, Queue queue1) {
		return BindingBuilder.bind(queue1).to(fanReturnPopulation);
	}
	@Bean
	public ReservationEventHandler receiver() {
		return new ReservationEventHandler();
	}
}
