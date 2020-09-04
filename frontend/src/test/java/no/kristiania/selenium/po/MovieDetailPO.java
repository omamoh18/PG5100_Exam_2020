package no.kristiania.selenium.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class MovieDetailPO extends LayoutPO{

    public MovieDetailPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public MovieDetailPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Details");
    }

    public void createRating(String userReview, String star){
        setText("addUserReview", userReview);
        setText("addStar", star);
        clickAndWait("createBtnId");
    }

    public void updateRating(String userReview, String star){
        setText("addUserReview", userReview);
        setText("addStar", star);
        clickAndWait("updateBtnId");
    }


    public boolean textExistsOnPage(String text){
        // Source https://stackoverflow.com/questions/11454798/how-can-i-check-if-some-text-exist-or-not-in-the-page-using-selenium
        List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
        return list.size() > 0;
    }
}