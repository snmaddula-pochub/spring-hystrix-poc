package snmaddula.pochub.hystrix.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class SimpleController {

	private RestTemplate restTemplate;
	
	@Value("${FIRST}")
	private String first;

	@Value("${NEXT}")
	private String next;
	
	
	public SimpleController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/hello")
	@HystrixCommand(fallbackMethod="helloFailOver")
	public String hello() {
		return restTemplate.getForObject(first, String.class);
	}
	
	public String helloFailOver() {
		System.out.println("-- " + first +" -- failed -- contacting -- " + next);
		return restTemplate.getForObject(next, String.class);
	}
}
