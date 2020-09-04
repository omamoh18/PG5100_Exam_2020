package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private FilterController filterController;

    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }

    public List<Movie> getAllMoviesSortedByRating(){
        if(filterController.getDropDownSelect() == null) {
            return movieService.getAllMoviesSortedByAverageRatings();
        }
        return movieService.getAllMoviesSortedByAverageRatings();
    }
}
