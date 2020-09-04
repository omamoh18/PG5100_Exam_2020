package no.kristiania.selenium.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * NOTE this is modified and taken from:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/LayoutPO.java
 */

public abstract class LayoutPO extends PageObject {

    public LayoutPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public LayoutPO(PageObject other) {
        super(other);
    }

    public SignUpPO toSignUp(){

        clickAndWait("linkToSignupId");

        SignUpPO signUpPO = new SignUpPO(this);
        assertTrue(signUpPO.isOnPage());

        return signUpPO;
    }

    public IndexPO doLogin(String email, String password) {
        clickAndWait("linkToLoginId");
        IndexPO indexPO = new IndexPO(this);

        setText("username", email);
        setText("password", password);
        clickAndWait("submit");

        assertTrue(indexPO.isOnPage());

        return indexPO;

    }

    public IndexPO doLoginWithDisabledUser(String email, String password) {
        clickAndWait("linkToLoginId");
        IndexPO po = new IndexPO(this);

        setText("username", email);
        setText("password", password);
        clickAndWait("submit");

        return po;
    }



    public IndexPO doLogout(){
        clickAndWait("logoutId");

        IndexPO indexPO = new IndexPO(this);
        assertTrue(indexPO.isOnPage());

        return indexPO;
    }

    public boolean isLoggedIn(){
        return getDriver().findElements(By.id("logoutId")).size() > 0 &&
                getDriver().findElements((By.id("linkToSignupId"))).isEmpty();
    }
}

