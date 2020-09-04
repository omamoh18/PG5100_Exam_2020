package no.kristiania.selenium.po.admin;

import no.kristiania.selenium.po.LayoutPO;
import no.kristiania.selenium.po.PageObject;

public class CreateMoviePO extends LayoutPO {

    public CreateMoviePO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Create Movie");
    }

    public void createMovie(String title, String director, int yearOfRelease){

        setText("title", title);
        setText("director",director);
        setText("yearOfRelease", Integer.toString(yearOfRelease));
        clickAndWait("createMovieID");
    }

}
