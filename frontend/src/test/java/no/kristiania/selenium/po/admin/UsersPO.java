package no.kristiania.selenium.po.admin;

import no.kristiania.selenium.po.LayoutPO;
import no.kristiania.selenium.po.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UsersPO extends LayoutPO {

    public UsersPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Users");
    }


    public int getAmountOfUsers() {
        List<WebElement> elements = driver.findElements(
                By.xpath("//table[@id='userTable']/tbody/tr"));
        return elements.size();
    }

    private int getRowOfUser(String email) {
        List<WebElement> elements = driver.findElements(
                By.xpath("//table[@id='userTable']/tbody/tr"));
        for(int i = 0; i < elements.size(); i++) {
            if(elements.get(i).getText().contains(email)){
                return i;
            }
        }
        return -1;
    }

    public boolean textExistsOnPage(String text){
        // Source https://stackoverflow.com/questions/11454798/how-can-i-check-if-some-text-exist-or-not-in-the-page-using-selenium
        List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
        return list.size() > 0;
    }

    public void disableUser(String email) {
        int row = getRowOfUser(email);
        WebElement element = driver.findElement(By.id("userTable:"+row+":disableBtn"));
        element.click();
        getDriver().switchTo().alert().accept();
        waitForPageToLoad();
    }

    public void enableUser(String email) {
        int row = getRowOfUser(email);
        WebElement element = driver.findElement(By.id("userTable:"+row+":enableBtn"));
        element.click();
        getDriver().switchTo().alert().accept();
        waitForPageToLoad();
    }
}

