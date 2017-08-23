package com.my.company.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.my.company.rabbit.receiver.Receiver;


@Configuration
public class AmqpMessageListenerContainerConfig {
	final static String queueName = "QueueOne";
	final static String queueNameSecond = "QueueTwo";

    @Bean(name="queueOne")
    Queue queue() {
        return new Queue(queueName, false);
    }
    
    @Bean(name="queueTwo")
    Queue queueSecond() {
        return new Queue(queueNameSecond, false);
    }

    @Bean
    FanoutExchange exchange() {
    	FanoutExchange exchange = new FanoutExchange("spring-boot-fanout-exchange");
    	return exchange;
    }
    
    @Bean
    TopicExchange exchangeTopic() {
    	TopicExchange exchange = new TopicExchange("spring-boot-exchange");
    	return exchange;
    }
    
    @Bean
    HeadersExchange exchangeHeader() {
    	HeadersExchange exchange = new HeadersExchange("spring-boot-header-exchange");
    	return exchange;
    }
    
    @Bean
    DirectExchange exchangeDirect() {
    	DirectExchange exchange = new DirectExchange("spring-boot-direct-exchange");
    	return exchange;
    }

    @Bean
    Binding binding(@Qualifier("queueOne") Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
    
    @Bean
    Binding bindingTwo(@Qualifier("queueTwo")Queue queueSecond, FanoutExchange exchange) {
        return BindingBuilder.bind(queueSecond).to(exchange);
    }
    
    @Bean
    Binding bindingTopic(@Qualifier("queueOne") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }
    
    @Bean
    Binding bindingTwoTopic(@Qualifier("queueTwo")Queue queueSecond, TopicExchange exchange) {
        return BindingBuilder.bind(queueSecond).to(exchange).with(queueNameSecond);
    }
    
    @Bean
    Binding bindingHeader(@Qualifier("queueOne") Queue queue, HeadersExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).where("queueOneHeader").matches("queueOneHeader");
    }
    
    @Bean
    Binding bindingTwoHeader(@Qualifier("queueTwo")Queue queueSecond, HeadersExchange exchange) {
        return BindingBuilder.bind(queueSecond).to(exchange).where("queueTwoHeader").matches("queueTwoHeader");
    }
    
    @Bean(name="myCustomRabbitTemplate")
    public RabbitTemplate customRabbitTemplate(@Qualifier("myCustomFactory")ConnectionFactory customFactory){
    	RabbitTemplate template = new RabbitTemplate();
    	template.setConnectionFactory(customFactory);
    	return template;
    }

    @Bean
    SimpleMessageListenerContainer container(@Qualifier("myCustomFactory")ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    
    @Bean
    SimpleMessageListenerContainer containerTwo(@Qualifier("myCustomFactory")ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueNameSecond);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
    
      
    @Bean(name="myCustomFactory")
    public ConnectionFactory customFactory(){
		CachingConnectionFactory factory = new CachingConnectionFactory(
				"ec2-34-213-235-54.us-west-2.compute.amazonaws.com", 5672);
				//"localhost", 5672);
		//factory.setHost("http://ec2-34-213-235-54.us-west-2.compute.amazonaws.com");
    	//factory.setPort(5672);
    	return (ConnectionFactory)factory;
    }
    
    
    
}
