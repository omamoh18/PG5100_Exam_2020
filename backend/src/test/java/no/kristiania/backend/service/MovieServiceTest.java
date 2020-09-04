package no.kristiania.backend.service;

import no.kristiania.backend.StubApplication;
import no.kristiania.backend.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MovieServiceTest extends ServiceTestBase {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;


    public boolean createUser(String email){
        return userService.createUser(email, "foo", "bar", "1234", false);
    }

    @Test
    public void testCreateMovie(){
        int sizeOfMovies = movieService.getAllMovies().size();
        String movieTitle = "test";
        long id = movieService.createMovie(movieTitle, "test", 1799);
        assertEquals(sizeOfMovies + 1, movieService.getAllMovies().size());
        assertEquals(movieTitle, movieService.getMovie(id).getTitle());
    }

    @Test
    public void testDeleteMovie() {
        String movieTitle = "test";
        long id = movieService.createMovie(movieTitle, "test", 1979);
        assertTrue(movieService.deleteMovie(id));
    }

    @Test
    public void testListAllMovies(){
        int sizeOfMovies = movieService.getAllMovies().size();
        String movieTitle = "test";
        movieService.createMovie(movieTitle, "test", 1899);
        List<Movie> list = movieService.getAllMovies();
        assertEquals(sizeOfMovies + 1, list.size());
    }

    @Test
    public void testGetMovie(){
        String movieTitle = "test";
        long id = movieService.createMovie(movieTitle, "test", 1979);
        Movie movie = movieService.getMovie(id);
        assertEquals(movieTitle, movie.getTitle());
    }

    @Test
    public void testGetMovieReviews(){
        String movieTitle = "test";
        String email = "test@test.no";
        String userReview = "review";
        int score = 3;
        boolean createdUser = createUser(email);
        long id = movieService.createMovie(movieTitle, "test", 1989);
        int sizeOfReviews = movieService.getMovie(id).getReviewList().size();
        reviewService.createReview(email, id, userReview, score);
        Movie movie = movieService.getMovie(id);
        assertEquals(movieTitle, movie.getTitle());
        assertEquals(sizeOfReviews + 1, movieService.getMovie(id).getReviewList().size());

    }

    @Test
    public void testGetMovieAverage(){
        String movieTitle = "test";
        String email = "test@test.com";
        String emailTwo = "test2@test.com";
        String emailThree = "test3@test.com";
        String userReview = "review";
        int star = 3;
        int starTwo = 5;
        int starThree = 2;
        createUser(email);
        createUser(emailTwo);
        createUser(emailThree);
        long id = movieService.createMovie(movieTitle, "test", 1999);
        int sizeOfReviews = movieService.getMovie(id).getReviewList().size();
        reviewService.createReview(email, id, userReview, star);
        reviewService.createReview(emailTwo, id, userReview, starThree);
        reviewService.createReview(emailThree, id, userReview, starTwo);
        reviewService.createReview(emailThree, id, userReview, starTwo);
        Movie movie = movieService.getMovie(id);

        assertEquals(movieTitle, movie.getTitle());
        assertEquals(sizeOfReviews + 4, movieService.getMovie(id).getReviewList().size());
        assertEquals(3.75,movieService.getMovieRatingByAverage(movie.getId()).doubleValue());
    }


    @Test
    public void testGetUserMovieReview(){
        String movieTitle = "test";
        String email = "test@test.com";
        String emailTwo = "testTwo@test.com";
        String emailThree = "testThree@test.com";
        String emailFour = "testFour@test.com";
        String userReview = "review";
        int star = 4;
        int starTwo = 2;
        int starThree = 5;
        int starFour = 1;
        createUser(email);
        createUser(emailTwo);
        createUser(emailThree);
        createUser(emailFour);
        long id = movieService.createMovie(movieTitle, "test", 1999);
        int sizeOfReviews = movieService.getMovie(id).getReviewList().size();
        long reviewId = reviewService.createReview(email, id, userReview, star);
        long reviewIdTwo = reviewService.createReview(emailTwo, id, userReview, starTwo);
        long reviewIdThree = reviewService.createReview(emailThree, id, userReview, starThree);
        long reviewIdFour = reviewService.createReview(emailFour, id, userReview, starFour);
        Movie movie = movieService.getMovie(id);

        assertEquals(movieTitle, movie.getTitle());
        assertEquals(sizeOfReviews + 4, movieService.getMovie(id).getReviewList().size());
        assertTrue(movieService.userHasReviewedMovie(email, movie.getId()));
        assertTrue(movieService.userHasReviewedMovie(emailTwo, movie.getId()));
        assertTrue(movieService.userHasReviewedMovie(emailThree, movie.getId()));
        assertEquals(reviewService.getReview(reviewId).getId(), movieService.getMovieUserReview(email, movie.getId()).getId());
        assertEquals(reviewService.getReview(reviewIdTwo).getId(), movieService.getMovieUserReview(emailTwo, movie.getId()).getId());
        assertEquals(reviewService.getReview(reviewIdThree).getId(), movieService.getMovieUserReview(emailThree, movie.getId()).getId());
        assertEquals(reviewService.getReview(reviewIdFour).getId(), movieService.getMovieUserReview(emailFour, movie.getId()).getId());
    }

    @Test
    public void testGetUserMovieReviewWithErrorUser(){
        String movieTitle = "test";
        String email = "test@test.com";
        String userReview = "review";
        int star = 3;
        long id = movieService.createMovie(movieTitle, "test", 1999);
        int sizeOfReview = movieService.getMovie(id).getReviewList().size();
        assertThrows(IllegalArgumentException.class ,() -> reviewService.createReview(email, id, userReview, star));
        Movie movie = movieService.getMovie(id);

        assertEquals(movieTitle, movie.getTitle());
        assertEquals(sizeOfReview, movieService.getMovie(id).getReviewList().size());

        assertThrows(IllegalArgumentException.class ,() -> movieService.userHasReviewedMovie(email, movie.getId()));
        assertThrows(IllegalArgumentException.class ,() -> movieService.getMovieUserReview(email, movie.getId()));
    }

    @Test
    public void testGetUserMovieReviewWithErrorMovie(){
        String email = "test@test.com";
        createUser(email);

        assertThrows(IllegalArgumentException.class ,() -> movieService.userHasReviewedMovie(email, -1L));
        assertThrows(IllegalArgumentException.class ,() -> movieService.getMovieUserReview(email, -1L));
    }

    @Test
    public void testGetAllMoviesSortedByStars(){
        String movieTitle = "test";
        String email = "test@test.com";
        String emailTwo = "testTwo@test.com";
        String emailThree = "testThree@test.com";
        String userReview = "review";
        int star = 4;
        int scoreTwo = 3;
        int scoreThree = 2;

        createUser(email);
        createUser(emailTwo);
        createUser(emailThree);
        long id = movieService.createMovie(movieTitle, "test", 1799);
        long idTwo = movieService.createMovie(movieTitle, "testTwo", 1899);
        long idThree = movieService.createMovie(movieTitle, "testThree", 1999);
        int sizeOfReview = movieService.getMovie(id).getReviewList().size();
        reviewService.createReview(email, id, userReview, star);
        reviewService.createReview(emailTwo, id, userReview, star);
        reviewService.createReview(emailThree, id, userReview, star);
        reviewService.createReview(email, idTwo, userReview, scoreTwo);
        reviewService.createReview(emailTwo, idTwo, userReview, scoreTwo);
        reviewService.createReview(emailThree, idTwo, userReview, scoreTwo);
        reviewService.createReview(email, idThree, userReview, scoreThree);
        reviewService.createReview(emailTwo, idThree, userReview, scoreThree);
        reviewService.createReview(emailThree, idThree, userReview, scoreThree);
        Movie movie = movieService.getMovie(id);
        movieService.getMovie(idTwo);
        movieService.getMovie(idThree);

        List<Movie> list = movieService.getAllMoviesSortedByAverageRatings();

        for(int i = 0; i<list.size()-1; i++){
            assertNotNull(list.get(i));
            assertTrue(movieService.getMovieRatingByAverage(list.get(i).getId()) >= movieService.getMovieRatingByAverage(list.get(i + 1).getId()));
        }

        assertEquals(movieTitle, movie.getTitle());
        assertEquals(sizeOfReview+3, movieService.getMovie(id).getReviewList().size());
    }



}
