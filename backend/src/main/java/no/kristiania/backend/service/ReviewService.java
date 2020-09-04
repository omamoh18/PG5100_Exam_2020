package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Review;
import no.kristiania.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MovieService movieService;


    public long createReview(String email, Long movieId, String userReview, int star, LocalDate localDate){

        User user = entityManager.find(User.class, email);
        if(user == null){
            throw new IllegalArgumentException("User with " + email + " does not exist");
        }
        Movie movie = entityManager.find(Movie.class, movieId);
        if(movie == null){
            throw new IllegalArgumentException("Movie with " + movieId + " does not exist");
        }
        if(star < 1 || star > 5){
            throw new IllegalArgumentException("star with given size " + star + " cannot be created"); //Star must be in the range 1-5. cannot be smaller than 1 or bigger than 5.
        }

        Review review = new Review();
        review.setUser(user);
        review.setMovie(movie);
        review.setUserReview(userReview);
        review.setStar(star);
        review.setLocalDate(localDate);

        entityManager.persist(review);
        return review.getId();
    }

    public long createReview(String email, Long movieId, String userReview, int star) {
         return createReview(email, movieId, userReview, star, LocalDate.now());
    }

    public List<Review> getAllReviews(){
        TypedQuery<Review> query = entityManager.createQuery("select r from Review r", Review.class);
        return query.getResultList();
    }

    public Review getReview(long id){
        return entityManager.find(Review.class, id);
    }

    //Checked sorted by stars
    public List<Review> getAllReviewsSortedByRatings() {
        TypedQuery<Review> query = entityManager.createQuery("select r from Review r order by r.star desc", Review.class);
        return query.getResultList();
    }

    // for sorting timestamp on detail site.
     public List<Review> getAllReviewsSortedByTimeOfCreation(Long id){
        Movie movie = movieService.getMovie(id);
        List<Review> list = movie.getReviewList();
        list.sort(Comparator.comparing(Review::getLocalDate));
        return list;
    }

    public void updateReview(Review review){
        entityManager.merge(review);
    }
}
