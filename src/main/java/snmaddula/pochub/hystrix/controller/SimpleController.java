package snmaddula.pochub.hystrix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimpleController {

	private RestTemplate restTemplate;

	public SimpleController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/hello")
	public String hello() {
		return restTemplate.getForObject("http://localhost:7777/hello", String.class);
	}
}
