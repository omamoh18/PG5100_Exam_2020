package no.kristiania.selenium;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.service.MovieService;
import no.kristiania.backend.service.UserService;
import no.kristiania.selenium.po.*;
import no.kristiania.selenium.po.admin.CreateMoviePO;
import no.kristiania.selenium.po.admin.UsersPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * NOTE this file is taken from:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/SeleniumTestBase.java
 */

public abstract class SeleniumTestBase {


    @Autowired
    private MovieService moviesService;

    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();

    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId() {
        return "foo_SeleniumLocalIT_" + counter.getAndIncrement() + "@test.com";
    }

    private IndexPO home;
    private UsersPO usersPO;
    private CreateMoviePO createMovie;
    private MovieDetailPO movieDetail;
    private UserDetailPO userDetail;

    @Autowired
    private UserService userService;

    private IndexPO createNewUser(String email, String name, String surname, String password){

        home.toStartingPage();

        SignUpPO signUpPO = home.toSignUp();

        IndexPO indexPO = signUpPO.createUser(email,password,name,surname);
        assertNotNull(indexPO);

        return indexPO;
    }


    @BeforeEach
    public void initTest() {
        getDriver().manage().deleteAllCookies();
        home = new IndexPO(getDriver(), getServerHost(), getServerPort());
        home.toStartingPage();
        assertTrue(home.isOnPage(), "Failed to start from the Home Page");
    }

    @Test
    public void testLoginAndLogout(){
        String user = getUniqueId();
        String password = getUniqueId();
        String name = getUniqueId();
        assertTrue(userService.createUser(user, name,"admin",password, false));

        assertFalse(home.isLoggedIn());
        home = home.doLogin(user, password);

        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(name));
    }


  /*  @Test
    public void testDisableAndEnableUser(){
        String user = getUniqueId();
        String userPassword = getUniqueId();
        String admin = getUniqueId();
        String adminPassword = getUniqueId();
        String userName = getUniqueId();
        String adminName = getUniqueId();
        String userSurName = getUniqueId();
        String adminSurName = getUniqueId();

        assertTrue(userService.createUser(user,userName,userSurName,userPassword, false));
        assertTrue(userService.createUser(admin,adminName,adminSurName,adminPassword, true));

        assertFalse(home.isLoggedIn());
        home.doLogin(admin, adminPassword);
        assertTrue(home.isLoggedIn());
        usersPO = home.toUsers();

        assertTrue(usersPO.textExistsOnPage(user));
        assertTrue(usersPO.textExistsOnPage(userName));
        assertTrue(usersPO.textExistsOnPage(userSurName));
        assertTrue(usersPO.textExistsOnPage(admin));
        assertTrue(usersPO.textExistsOnPage(adminName));
        assertTrue(usersPO.textExistsOnPage(adminSurName));

        int userCount = usersPO.getAmountOfUsers();
        usersPO.disableUser(user);
        assertEquals(userCount, usersPO.getAmountOfUsers());
        home.doLogout();
        assertFalse(home.isLoggedIn());

        home.doLoginWithDisabledUser(user, userPassword);
        assertFalse(home.isLoggedIn());

        home.doLogin(admin, adminPassword);
        assertTrue(home.isLoggedIn());
        usersPO = home.toUsers();
        assertEquals(userCount, usersPO.getAmountOfUsers());
        usersPO.enableUser(user);
        home.doLogout();

        home.doLogin(user, userPassword);
        assertTrue(home.isLoggedIn());
    }*/

    @Test
    public void testCreateAdmin(){
        assertFalse(home.isLoggedIn());
        String email = "admin2@admin.com";
        String password = "a";

        assertTrue(userService.createUser(email,"foo","bar" , password, true));
        LogInPO logInPO = home.toLogIn();
        logInPO.isOnPage();
    }

    @Test
    public void testMovieSorting(){
        List<Double> list = home.getMovieAverageRating();
        for(int i=0; i<list.size()-1; i++){
            assertTrue(list.get(i) >= list.get(i+1));
        }
    }

  /*  @Test
    public void testMovieDetailsAndCreateAndUpdateRating(){
        assertFalse(home.isLoggedIn());
        String email = getUniqueId();
        String name = getUniqueId();
        String surName = getUniqueId();
        String password = getUniqueId();
        assertTrue(userService.createUser(email, name, surName, password, true));

        home.doLogin(email, password);
        assertTrue(home.isLoggedIn());
        int movies = home.getAmountOfDisplayedMovies();

        for(int i=0; i<movies; i++){
            movieDetail = home.toMovieDetail(i);
            assertTrue(movieDetail.isOnPage());
            home.toStartingPage();
        }

        int randomRow = new Random().nextInt(movies);
        movieDetail = home.toMovieDetail(randomRow);
        String uniqueUserReview = getUniqueId();
        String star = "2";
        String newUniqueUserReview = getUniqueId();
        String newStar = "5";
        movieDetail.createRating(uniqueUserReview, star);
        assertTrue(movieDetail.textExistsOnPage(uniqueUserReview));
        assertTrue(movieDetail.textExistsOnPage(star));

        movieDetail.updateRating(newUniqueUserReview, star);
        assertTrue(movieDetail.textExistsOnPage(newUniqueUserReview));
        assertTrue(movieDetail.textExistsOnPage(newStar));
        assertFalse(movieDetail.textExistsOnPage(uniqueUserReview));
    }*/

   /* @Test
    public void testRateMovieAndSeeUserDetails(){
        assertFalse(home.isLoggedIn());
        String email = getUniqueId();
        String name = getUniqueId();
        String surName = getUniqueId();
        String password = getUniqueId();
        assertTrue(userService.createUser(email, name, surName, password, true));

        home.doLogin(email, password);
        assertTrue(home.isLoggedIn());
        int movies = home.getAmountOfDisplayedMovies();

        int randomRow = new Random().nextInt(movies);
        movieDetail = home.toMovieDetail(randomRow);
        String uniqueUserReview = getUniqueId();
        String star = "2";
        movieDetail.createRating(uniqueUserReview, star);
        assertTrue(movieDetail.textExistsOnPage(uniqueUserReview));
        assertTrue(movieDetail.textExistsOnPage(star));

        home.toStartingPage();
        assertTrue(home.isOnPage());

        userDetail = home.toUserDetail();
        assertTrue(userDetail.isOnPage());
        assertTrue(userDetail.textExistsOnPage(email));
        assertTrue(userDetail.textExistsOnPage(name));
        assertTrue(userDetail.textExistsOnPage(surName));

        assertTrue(userDetail.textExistsOnPage(uniqueUserReview));
        assertTrue(userDetail.textExistsOnPage(star));

        assertFalse(userDetail.textExistsOnPage(password));


    }*/

    @Test
    public void testInvalidRegistration() {
        home.toStartingPage();
        SignUpPO signUpPO = home.toSignUp();

        String surName = "";

        IndexPO indexPO = signUpPO.createUser(getUniqueId(), "name", surName, "hahahhahah");
        assertNull(indexPO);
        assertTrue(signUpPO.isOnPage());
    }

  /*  @Test
    public void testCreateAndLogoutUser() {

        assertFalse(home.isLoggedIn());

        String email = getUniqueId();
        String givenName = "given";
        String familyName = "family";
        String password = "123456789";
        home = createNewUser(email, givenName, familyName, password);

        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(email));

        home.doLogout();

        assertFalse(home.isLoggedIn());
        assertFalse(home.getDriver().getPageSource().contains(email));
    }*/




}

