package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Review;
import no.kristiania.backend.service.MovieService;
import no.kristiania.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.time.LocalDate;

@Named
@RequestScoped
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    MovieService movieService;

    @Autowired
    UserInfoController userInfoController;

    @Autowired
    MainController mainController;

    private String userReview = "";
    private int star = 1;

    public String createReview(){
        if(userReview.isEmpty() || star <= 0 || star > 5){
            return mainController.toMovieDetailPageError();
        }
        long id = reviewService.createReview(userInfoController.getUserEmail(), mainController.getMovie().getId(), getUserReview(), getStar(), getLocalDate());
        reviewService.getReview(id).getMovie().getReviewList().add(reviewService.getReview(id));
        return mainController.toMovieDetailPageSuccess(reviewService.getReview(id).getMovie());
    }

    public String updateReview(){
        if((!userReview.isEmpty() || mainController.userHasRatedMovie()) && (star <= 0 || star > 5)){
            return mainController.toMovieDetailPageError();
        }
        Review review = mainController.getUserReview();
        review.setUserReview(userReview);
        review.setStar(star);
        review.setLocalDate(LocalDate.now());
        reviewService.updateReview(review);
        return mainController.toMovieDetailPageSuccess(reviewService.getReview(review.getId()).getMovie());
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public LocalDate getLocalDate(){
        return LocalDate.now();
    }

}
