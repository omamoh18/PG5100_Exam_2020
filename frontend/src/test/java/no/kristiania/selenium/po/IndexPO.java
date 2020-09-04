package no.kristiania.selenium.po;

import no.kristiania.selenium.po.admin.CreateMoviePO;
import no.kristiania.selenium.po.admin.UsersPO;
import org.h2.command.dml.Select;
import org.h2.engine.Session;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * NOTE this is modified and taken from:
 * // https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/IndexPO.java
 */


public class IndexPO extends LayoutPO {

    public IndexPO(PageObject other) {
        super(other);
    }

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public void toStartingPage(){
        getDriver().get(host + ":" + port);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Home");
    }

    public boolean isLoggedIn() {
        return getDriver().findElements(By.id("logoutId")).size() > 0 &&
                getDriver().findElements((By.id("linkToSignupId"))).isEmpty();
    }

    public UsersPO toUsers(){
        clickAndWait("createUsersId");
        UsersPO po = new UsersPO(this);
        assertTrue(po.isOnPage());
        return po;
    }

    public LogInPO toLogIn(){
        clickAndWait("linkToLoginId");
        LogInPO logInPO = new LogInPO(this);
        assertTrue(logInPO.isOnPage());
        return logInPO;
    }

    public CreateMoviePO toCreateMovie() {
        clickAndWait("createMovieId");
        CreateMoviePO po = new CreateMoviePO(this);
        assertTrue(po.isOnPage());
        return po;
    }

    public UserDetailPO toUserDetail(){
        clickAndWait("userPageId");
        UserDetailPO po = new UserDetailPO(this);
        assertTrue(po.isOnPage());
        return po;
    }


    public MovieDetailPO toMovieDetail(int row){
        clickAndWait("itemsTable:" + row + ":detailBtn");
        MovieDetailPO po = new MovieDetailPO(this);
        assertTrue(po.isOnPage());
        return po;
    }

    public List<Double> getMovieAverageRating(){
        List<Double> returnValue = new ArrayList<>();
        List<WebElement> list = driver.findElements(
                By.xpath("//table[@id='itemsTable']/tbody/tr/td[4]/label[@class='itemRating']")
        );
        int size = list.size();
        for(int i = 0; i<size; i++) {
            WebElement element = driver.findElement(By.xpath("//*[@id='itemsTable:"+i+":rating']"));
            if (!element.getText().equalsIgnoreCase("No rating")) {
                String text = element.getText();
                returnValue.add(Double.parseDouble(text.split("/")[0]));
            }
        }
        return returnValue;
    }


    public int getAmountOfDisplayedMovies(){
        List<WebElement> e = driver.findElements(
                By.xpath("//table[@id='itemsTable']/tbody/tr")
        );
        return e.size();
    }
}
