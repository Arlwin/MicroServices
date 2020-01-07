package nc.arlwin.Moviecatalogservice.resources;

import nc.arlwin.Moviecatalogservice.models.CatalogItem;
import nc.arlwin.Moviecatalogservice.models.Movie;
import nc.arlwin.Moviecatalogservice.models.Rating;
import nc.arlwin.Moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.WatchEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog") //When catalog is called, return this whole class
public class MovieCatalogResources {

    //1
    @Autowired //Check if there is any @bean currently declared to any method and injects it here when it finds one. Executes it only once
    private RestTemplate restTemplate;

    //2
    @Autowired //Check if there is any @bean currently declared to any method and injects it here when it finds one. Executes it only once
    private WebClient.Builder webClientBuilder;


    @RequestMapping("/{userId}") //When catalog/userId is called
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        //1
        //RestTemplate restTemplate = new RestTemplate(); //Rest Template is going to be depreciated
        //Movie movie = restTemplate.getForObject("http://localhost:8082/movies/test", Movie.class); //Gets the resource from URL and converts to Object (second argument)

        //2
        //Using WebClient => Web Reactive programming part of Spring Boot (WebFlux) => Asynchronous
        //WebClient.Builder builder = WebClient.builder(); //This is the builder of WebClient that you will call

        //Get all the rated movie Ids
        //Hardcoded
        //UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRating.class);
        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class); //Uses the Application Name of ratings to check with Eureka server

        //For each id, call the movie info and get details
        //API Call using REST template
        return ratings.getUserRating().stream().map(rating -> {
            //Using rest template
            //Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class); //For each rating, get the movie ID and gets the Movie object based on this
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class); //Same as above (uses Discovery Service)

            //WebClient builder version
            /*
            Movie movie = webClientBuilder.build()
                    .get() //The method
                    .uri("http://localhost:8082/movies/" + rating.getMovieId()) //The URL to be called
                    .retrieve() //Fetch the data
                    .bodyToMono(Movie.class) //What ever body you get, convert it to a Movie Class. Mono = getting an object but not immediately (asynchronous)
                    .block(); //Block the execution UNTIL the Mono container gives something back
            */

            //The movie object NEEDS an empty constructor (Put all together)
            return new CatalogItem(movie.getName(), "This is best animation", rating.getRating()); //Create a catalog object based on Movie and Rating
        }).collect(Collectors.toList());


        //Put them all together
        /*return Collections.singletonList(
                new CatalogItem("SpiderVerse", "This is the best animation", 10)
        );*/
    }
}
