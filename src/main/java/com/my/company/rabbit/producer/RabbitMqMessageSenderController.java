package com.my.company.rabbit.producer;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RabbitMqMessageSenderController {
	
	@Resource(name="myCustomRabbitTemplate")
	private RabbitTemplate rabbitTemplate;
	
	@Value("${routing.pattern}")
	private String routingKey;
		
	
	@RequestMapping(path="/sendmessage/{message}",method=RequestMethod.POST)
	public Boolean sendMessageToQueue(@PathVariable String message){
		System.out.println("Host " + rabbitTemplate.getConnectionFactory().getHost() + "Port " +
				rabbitTemplate.getConnectionFactory().getPort()+"routing Key " + rabbitTemplate.getRoutingKey()
				+"Exchange " + rabbitTemplate.getExchange() +" "+rabbitTemplate.getConnectionFactory().getUsername());
		//rabbitTemplate.convertAndSend("spring-boot", "Hello "+message);
		
		rabbitTemplate.convertAndSend("spring-boot-fanout-exchange", "","Hello "+message);
		return true;
	}
}
