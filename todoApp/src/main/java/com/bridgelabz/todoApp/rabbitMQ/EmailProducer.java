package com.bridgelabz.todoApp.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.todoApp.mailUtility.Email;

@Component
public class EmailProducer {

	@Autowired
	RabbitTemplate rabbitTemplate;

	public void enqueue(Email email) {

		rabbitTemplate.convertAndSend(email);
	}

}
