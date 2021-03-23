package com.example.clientconnectivity;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ClientConnectivityApplication{

//	@Bean
//	public RestTemplate getRestTemplate(){
//		return new RestTemplate();
//	}
//
//	@Autowired
//	private RestTemplate restTemplate;


	@RequestMapping("/")
	public String home() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", "Hello from Client-Connectivity");
//		jsonObject.put("message-2",restTemplate.exchange("http://localhost:5002", HttpMethod.GET,
//				null, String.class).getBody());
		return jsonObject.toString();
	}

	public static void main(String[] args) {
		SpringApplication.run(ClientConnectivityApplication.class, args);
	}

}
