package com.codenotfound.ws.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.codenotfound.hello.Customer;
import com.codenotfound.hello.CustomerRepository;
import com.codenotfound.types.helloworld.Greeting;
import com.codenotfound.types.helloworld.ObjectFactory;
import com.codenotfound.types.helloworld.Person;

@Endpoint
public class HelloWorldEndpoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldEndpoint.class);

	@Autowired
	CustomerRepository customerRepository;

	@PayloadRoot(namespace = "http://codenotfound.com/types/helloworld", localPart = "person")
	@ResponsePayload
	public Greeting sayHello(@RequestPayload Person request) {
		LOGGER.info("Endpoint received person[firstName={},lastName={}]", request.getFirstName(),
				request.getLastName());

		String greeting = "Hello " + request.getFirstName() + " " + request.getLastName() + "! Your id is ";

		LOGGER.info("yahoooo");

		greeting = greeting + this.addCustomer(request.getFirstName(), request.getLastName());

		ObjectFactory factory = new ObjectFactory();
		Greeting response = factory.createGreeting();
		response.setGreeting(greeting);

		LOGGER.info("Endpoint sending greeting='{}'", response.getGreeting());
		return response;
	}
	
	public String addCustomer(String firstName, String lastName) {
		Customer newCustomer = customerRepository.save(new Customer(firstName, lastName));
		
		LOGGER.info("New customer - " +newCustomer);
		
		return newCustomer.getId().toString();
	}

	public void demo() {
		// save a couple of customers
		customerRepository.save(new Customer("Jack", "Bauer"));
		customerRepository.save(new Customer("Chloe", "O'Brian"));
		customerRepository.save(new Customer("Kim", "Bauer"));
		customerRepository.save(new Customer("David", "Palmer"));
		customerRepository.save(new Customer("Michelle", "Dessler"));

		// fetch all customers
		LOGGER.info("Customers found with findAll():");
		LOGGER.info("-------------------------------");
		for (Customer customer : customerRepository.findAll()) {
			LOGGER.info(customer.toString());
		}
		LOGGER.info("");

		// fetch an individual customer by ID
		customerRepository.findById(1L).ifPresent(customer -> {
			LOGGER.info("Customer found with findById(1L):");
			LOGGER.info("--------------------------------");
			LOGGER.info(customer.toString());
			LOGGER.info("");
		});

		// fetch customers by last name
		LOGGER.info("Customer found with findByLastName('Bauer'):");
		LOGGER.info("--------------------------------------------");
		customerRepository.findByLastName("Bauer").forEach(bauer -> {
			LOGGER.info(bauer.toString());
		});
		// for (Customer bauer : repository.findByLastName("Bauer")) {
		// LOGGER.info(bauer.toString());
		// }
		LOGGER.info("");
	}
}