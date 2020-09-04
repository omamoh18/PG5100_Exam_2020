package no.kristiania.backend.service;

import no.kristiania.backend.StubApplication;
import no.kristiania.backend.entity.Review;
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
public class UserServiceTest extends ServiceTestBase {

    @Autowired
    UserService userService;

    @Autowired
    MovieService movieService;

    @Autowired
    ReviewService reviewService;

    public Long createMovie(String title){
        return movieService.createMovie(title, "test", 2016);
    }

    public boolean createUser(String email){
        return userService.createUser(email, "foo", "bar", "1234", false);
    }

    public boolean createAdmin(String email){
        return userService.createUser(email, "foo", "bar", "1234", true);
    }


    @Test
    public void testCreateUser(){
        int sizeOfUsers = userService.getAllUsers().size();
        String email = "foo@bar.com";
        boolean created = createUser(email);

        assertTrue(created);
        assertEquals(email, userService.getUser(email).getEmail());
        assertEquals(sizeOfUsers+1, userService.getAllUsers().size());
    }

    @Test
    public void testCreateAdmin(){
        int sizeOfUsers = userService.getAllUsers().size();
        String email = "foo@bar.com";
        boolean created = createAdmin(email);

        assertTrue(created);
        assertEquals(email, userService.getUser(email).getEmail());
        assertEquals(sizeOfUsers+1, userService.getAllUsers().size());
    }

    @Test
    public void testChangeEnabled(){
        String email = "foo@bar.com";
        boolean created = createUser(email);

        assertTrue(created);
        boolean enabled = userService.getUser(email).getEnabled();
        userService.changeEnabled(email);
        assertEquals(enabled, !userService.getUser(email).getEnabled());
    }

    @Test
    public void testGetUserFullName(){
        String name = "foo";
        String surname = "bar";
        String email = "foo@bar.com";
        boolean created = userService.createUser(email, name, surname, "1234", false);

        assertTrue(created);

        String receivedFullName = userService.getUserFullName(email);

        assertTrue(receivedFullName.contains(name));
        assertTrue(receivedFullName.contains(surname));
    }

    @Test
    public void testCreateTwoUsersWithSameEmail(){
        String email = "foo@bar.com";
        boolean created = createUser(email);

        assertTrue(created);

        boolean createdWithSameEmail = createUser(email);

        assertFalse(createdWithSameEmail);
    }

    @Test
    public void testCreateTwoAdminWithSameEmail(){
        String email = "foo@bar.com";
        boolean created = createAdmin(email);

        assertTrue(created);

        boolean createdWithSameEmail = createAdmin(email);

        assertFalse(createdWithSameEmail);
    }

    @Test
    public void testGetUserReviews(){
        String email = "foo@bar.com";
        String movieTitle = "test";
        String movieTitleTwo = "testTwo";
        String movieTitleThree = "testThree";
        String userReview = "review";
        String userReviewTwo = "reviewTwo";
        String userReviewThree = "reviewThree";
        int star = 2;

        boolean created = createUser(email);
        assertTrue(created);

        List<Review> userReviewBefore = userService.getUserReview(email);

        long movieId = createMovie(movieTitle);
        long movieIdTwo = createMovie(movieTitleTwo);
        long movieIdThree = createMovie(movieTitleThree);
        long reviewId = reviewService.createReview(email, movieId, userReview, star);
        long reviewIdTwo = reviewService.createReview(email, movieIdTwo, userReviewTwo, star);
        long reviewIdThree = reviewService.createReview(email, movieIdThree, userReviewThree, star);

        List<Review> userReviewAfter = userService.getUserReview(email);

        assertTrue(userReviewAfter.size() > userReviewBefore.size());
        assertEquals(userReviewBefore.size() + 3, userReviewAfter.size());

    }
}
