package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Review;
import no.kristiania.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class MainController {

    @Autowired
    MovieService movieService;

    @Autowired
    UserInfoController userInfoController;

    private Movie movie;
    private Review userReview;

    public String toMovieDetailPage(Movie movie){
        setMovie(movie);
        return "/movie-detail.jsf?faces-redirect=true";
    }

    public String toMovieDetailPageSuccess(Movie movie){
        setMovie(movie);
        return "/movie-detail.jsf?faces-redirect=true&success=true";
    }

    public String toMovieDetailPageError(){
        return "/item-detail.jsf?faces-redirect=true&error=true";
    }

    public Boolean userHasRatedMovie(){
        if(movieService.userHasReviewedMovie(userInfoController.getUserEmail(), movie.getId())) {
            setUserReview(movieService.getMovieUserReview(userInfoController.getUserEmail(), movie.getId()));
            return true;
        }
        return false;
    }

    public Review getUserReview(){
        return this.userReview;
    }

    public Double getRatingByAverage(){
        return movieService.getMovieRatingByAverage(movie.getId());
    }

    public Double getRatingByAverage(Long id){
        return movieService.getMovieRatingByAverage(id);
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setUserReview(Review userReview) {
        this.userReview = userReview;
    }
}
