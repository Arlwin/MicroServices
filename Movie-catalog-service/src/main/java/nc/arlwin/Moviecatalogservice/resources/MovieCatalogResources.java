package nc.arlwin.Moviecatalogservice.resources;

import nc.arlwin.Moviecatalogservice.models.CatalogItem;
import nc.arlwin.Moviecatalogservice.models.Movie;
import nc.arlwin.Moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog") //When catalog is called, return this whole class
public class MovieCatalogResources {

    @Autowired //Check if there is any @bean currently declared to any method and injects it here when it finds one. Executes it only once
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}") //When catalog/userId is called
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        //RestTemplate restTemplate = new RestTemplate();
        //Movie movie = restTemplate.getForObject("http://localhost:8082/movies/test", Movie.class); //Gets the resource from URL and converts to Object (second argument)

        //Get all the rated movie Ids
        //Hardcoded
        List<Rating> ratings = Arrays.asList(
                new Rating("0001", 5),
                new Rating("0002", 7)
        );

        //For each id, call the movie info and get details
        //API Call using REST template
        return ratings.stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class); //For each rating, get the movie ID and gets the Movie object based on this
            //The movie object NEEDS an empty constructor
            return new CatalogItem(movie.getName(), "This is best animation", rating.getRating()); //Create a catalog object based on Movie and Rating
        }).collect(Collectors.toList());


        //Put them all together
        /*return Collections.singletonList(
                new CatalogItem("SpiderVerse", "This is the best animation", 10)
        );*/
    }
}
