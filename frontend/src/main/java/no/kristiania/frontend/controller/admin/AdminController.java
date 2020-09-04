package no.kristiania.frontend.controller.admin;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class AdminController {

    @Autowired
    private MovieService movieService;


    private String title, director;
    private int yearOfRelease;


    private boolean movieExists(String title){
        boolean exist = false;
        for(Movie i : movieService.getAllMovies()){
            if(i.getTitle().equalsIgnoreCase(title)) exist = true;
        }
        return exist;
    }


    public String createMovie(){
        if(title.isEmpty()|| director.isEmpty()|| yearOfRelease == 0 || movieExists(title)){
            return "/admin/create-item.jsf?faces-redirect=true&error=true";
        }

        movieService.createMovie(title, director, yearOfRelease);

        return "/admin/create-movie.jsf?faces-redirect=true&success=true";
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }
}
