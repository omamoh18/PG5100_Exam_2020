package no.kristiania.frontend.controller;


import no.kristiania.backend.SortingOptions;
import no.kristiania.backend.entity.Review;
import no.kristiania.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class FilterController implements Serializable {

    @Autowired
    ReviewService reviewService;
    private int selectedRating;


    private List<Review> reviews;

    @PostConstruct
    private void init() {

        reviews = reviewService.getAllReviewsSortedByRatings();
    }

    public String sortByRating() {

        if (selectedRating == 0) {
            reviews = reviewService.getAllReviewsSortedByRatings();
        } else {
            reviews = reviewService.getAllReviewsSortedByRatings();
        }

        return "/search.jsf?faces-redirect=true";
    }

    private String dropDownSelect = "SORT";

    public String getDropDownSelect() {
        return dropDownSelect;
    }

    public void setDropDownSelect(String dropDownSelect) {
        this.dropDownSelect = dropDownSelect;
    }


  public List<Review> getAllReviewsSortedByRating(){
        List<Review> reviewList = reviewService.getAllReviewsSortedByRatings();
        return reviewList;
    }
}
