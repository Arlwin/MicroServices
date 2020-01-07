package nc.arlwin.Moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieCatalogServiceApplication {

	//Create a spring bean > Will declare an object that can be accessible to any class
	@Bean //Could be anywhere as long as it is on the class path
	@LoadBalanced //This is used for Service Discovery / Eureka server. Don't go the service directly, rather, discover that service using the Eureka server
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@Bean //Bean for WebClient
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

}
