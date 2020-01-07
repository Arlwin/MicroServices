package nc.arlwin.Movieratingsservice.resources;

import nc.arlwin.Movieratingsservice.models.Rating;
import nc.arlwin.Movieratingsservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating(movieId, 10);
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId){

        //return new Rating(movieId, 10);
        List<Rating> ratings = Arrays.asList(
                new Rating("0001", 5),
                new Rating("0002", 7)
        );

        //Bad practice > NEVER return a list on API = Changing / adding fields will break the List thus breaking the API
        //return ratings;

        //Do this instead. Create a new object that contains a List property
        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings); //Add the created list to the object
        return userRating; //return the object itself
    }
}
