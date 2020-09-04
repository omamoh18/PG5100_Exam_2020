package no.kristiania.selenium.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UserDetailPO extends LayoutPO {

    public UserDetailPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public UserDetailPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("User Details");
    }

    public boolean textExistsOnPage(String text){
        // Source https://stackoverflow.com/questions/11454798/how-can-i-check-if-some-text-exist-or-not-in-the-page-using-selenium
        List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
        return list.size() > 0;
    }
}
