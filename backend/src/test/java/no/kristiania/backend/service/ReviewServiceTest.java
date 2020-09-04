package no.kristiania.backend.service;

import no.kristiania.backend.StubApplication;
import no.kristiania.backend.entity.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ReviewServiceTest extends ServiceTestBase {

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    MovieService movieService;

    public boolean createUser(String email){
        return userService.createUser(email, "foo", "bar", "1234", false);
    }

    public Long createMovie(String title){
        return movieService.createMovie(title, "test", 1999);
    }

    @Test
    public void testCreateReview(){
        String userEmail = "test@test.com";
        String movieTitle = "test";
        String testReview = "review";
        int star = 3;
        boolean createdUser = createUser(userEmail);
        long createdMovie = createMovie(movieTitle);
        int sizeOfReviews = reviewService.getAllReviews().size();
        long id = reviewService.createReview(userEmail, createdMovie, testReview, star);

        assertTrue(createdUser);
        assertNotNull(movieService.getMovie(createdMovie));
        assertNotNull(reviewService.getReview(id));
        assertEquals(sizeOfReviews + 1, reviewService.getAllReviews().size());

    }

    @Test
    public void testGetAllReviews(){
        String userEmail = "test@test.com";
        String movieTitle = "test";
        String userSecondEmail = "barSecond@foo.no";
        String testReview = "review";
        int star = 3;
        int secondStar = 1;
        boolean createdUser = createUser(userEmail);
        boolean createdSecondUser = createUser(userSecondEmail);
        long createdMovie = createMovie(movieTitle);
        int sizeOfReviews = reviewService.getAllReviews().size();
        long id = reviewService.createReview(userEmail, createdMovie, testReview, star);
        long secondId = reviewService.createReview(userSecondEmail, createdMovie, testReview, secondStar);

        assertTrue(createdUser);
        assertTrue(createdSecondUser);
        assertNotNull(movieService.getMovie(createdMovie));
        assertNotNull(reviewService.getReview(id));
        assertNotNull(reviewService.getReview(secondId));
        assertEquals(sizeOfReviews + 2, reviewService.getAllReviews().size());
    }

    @Test
    public void testGetReview(){
        String userEmail = "test@test.no";
        String movieTitle = "test";
        String testReview = "review";
        int star = 4;
        boolean createdUser = createUser(userEmail);
        long createdMovie = createMovie(movieTitle);
        long id = reviewService.createReview(userEmail, createdMovie, testReview, star);

        assertTrue(createdUser);
        assertNotNull(movieService.getMovie(createdMovie));
        assertNotNull(reviewService.getReview(id));
    }

    @Test
    public void testUpdateReview(){
        String userEmail = "test@yahoo.no";
        String movieTitle = "test";
        String testReview = "review";
        int star = 1;
        int newStar = 3;
        boolean createdUser = createUser(userEmail);
        long createdMovie = createMovie(movieTitle);
        long id = reviewService.createReview(userEmail, createdMovie, testReview, star);

        assertTrue(createdUser);
        assertNotNull(movieService.getMovie(createdMovie));
        assertEquals(star, reviewService.getReview(id).getStar());

        Review review = reviewService.getReview(id);
        review.setStar(newStar);
        reviewService.updateReview(review);
        assertNotEquals(star, reviewService.getReview(id).getStar());
        assertEquals(newStar, reviewService.getReview(id).getStar());
    }

    @Test
    public void testCreateReviewWithWrongUser(){
        String errorEmail = "test@error.no";
        String movieTitle = "test";
        String testReview = "review";
        int star = 4;
        long createdMovie = createMovie(movieTitle);

        assertNotNull(movieService.getMovie(createdMovie));
        assertThrows(IllegalArgumentException.class ,() -> reviewService.createReview(errorEmail, createdMovie, testReview, star));
    }

    @Test
    public void testCreateReviewWithWrongMovie(){
        String email = "test@test.com";
        String movieTitle = "test";
        String testReview = "review";
        int score = 4;
        Long errorMovieId = - 1L;
        boolean createdUser = createUser(email);

        assertTrue(createdUser);
        assertThrows(IllegalArgumentException.class ,() -> reviewService.createReview(email, errorMovieId, testReview, score));
    }

    @Test
    public void testCreateReviewWithErrorStar(){
        String email = "test@test.com";
        String movieTitle = "test";
        String testReview = "review";
        int star = - 1;
        boolean createdUser = createUser(email);
        long createdMovie = createMovie(movieTitle);

        assertTrue(createdUser);
        assertNotNull(movieService.getMovie(createdMovie));
        assertThrows(IllegalArgumentException.class ,() -> reviewService.createReview(email, createdMovie, testReview, star));
    }


}
