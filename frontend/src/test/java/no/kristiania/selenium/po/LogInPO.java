package no.kristiania.selenium.po;

public class LogInPO extends PageObject{
    public LogInPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Login");
    }

    public IndexPO createUser(String name, String surName, String email, String password){
        setText("email", email);
        setText("name", name);
        setText("surname", surName);
        setText("password", password);
        clickAndWait("submit");

        IndexPO po = new IndexPO(this);

        if (po.isOnPage()) {
            return po;
        }
        return null;
    }
}
