package snmaddula.pochub.hystrix.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

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
	@HystrixCommand(
		fallbackMethod="helloFailOver", 
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
	})
	public String hello() {
		return restTemplate.getForObject(first, String.class);
	}
	
	public String helloFailOver() {
		System.out.println("-- " + first +" -- failed -- contacting -- " + next);
		return restTemplate.getForObject(next, String.class);
	}
}
