package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Review;
import no.kristiania.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class MovieService {

    @Autowired
    private EntityManager  entityManager;

    //Checked
    public long createMovie(
             String title,
             String director,
             int yearOfRelease){

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDirector(director);
        movie.setYearOfRelease(yearOfRelease);

        entityManager.persist(movie);
        return movie.getId();
    }

    //Checked
    public boolean deleteMovie(long id) {
        Movie movie = getMovie(id);
        entityManager.remove(movie);
        return true;
    }

    public Movie getMovie(long id){
        return entityManager.find(Movie.class, id);
    }

    public List<Movie> getAllMovies(){
        TypedQuery<Movie> query = entityManager.createQuery("select m from Movie m", Movie.class);
        return query.getResultList();
    }

    //Checked
    public Double getMovieRatingByAverage(long id){
        Movie movie = entityManager.find(Movie.class, id);
        if(movie.getReviewList().size() == 0) return 0d;
        return movie.getReviewList()
                .stream()
                .mapToDouble(Review::getStar)
                .average()
                .getAsDouble();
    }

    // Checked
   public List<Movie> getAllMoviesSortedByAverageRatings() {
        TypedQuery<Movie> query = entityManager.createQuery("select m from Movie m", Movie.class);
        List<Movie> movies = query.getResultList();

        movies.sort(Comparator.comparingDouble(movie -> getMovieRatingByAverage(movie.getId())));
        Collections.reverse(movies);
        return movies;
    }

    public boolean userHasReviewedMovie(String email, Long movieId){
        Boolean review = false;
        User user;
        user = entityManager.find(User.class, email);
        if(user == null){
            throw new IllegalArgumentException("User with " + email + " does not exist");
        }
        Movie movie = entityManager.find(Movie.class, movieId);
        if(movie == null){
            throw new IllegalArgumentException("Movie with " + movieId + " does not exist");
        }

        for(Review r : movie.getReviewList()){
            if(r.getUser().getEmail().equalsIgnoreCase(email)) review = true;
        }

        return review;
    }

    public Review getMovieUserReview(String email, Long movieId){
        User user = entityManager.find(User.class, email);
        if(user == null){
            throw new IllegalArgumentException("User with " + email + " does not exist");
        }
        Movie movie = entityManager.find(Movie.class, movieId);
        if(movie == null){
            throw new IllegalArgumentException("Movie with " + movieId + " does not exist");
        }

        for(Review r : movie.getReviewList()){
            if(r.getUser().getEmail().equalsIgnoreCase(email)) return r;
        }
        return null;
    }

}
