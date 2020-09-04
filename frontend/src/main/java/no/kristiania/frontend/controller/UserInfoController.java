package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Review;
import no.kristiania.backend.entity.User;
import no.kristiania.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

/**
 *
 * NOTE this file modified and taken from:
 * // https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/main/java/org/tsdes/intro/exercises/quizgame/frontend/controller/UserInfoController.java
 */

@Named
@RequestScoped
public class UserInfoController {

    @Autowired
    private UserService userService;

    public String getUserEmail(){
        String email = "";
        try{
            email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        } catch (ClassCastException e){
            // There is nothing to do if this happens
        }
        return email;
    }

    public String getCurrentUserFullName() {
        return userService.getUserFullName(getUserEmail());
    }

    public String getFullNameByEmail(String email) {
        return userService.getUserFullName(email);
    }

    public User getUser(){
        return userService.getUser(getUserEmail());
    }

    public List<Review> getUserReviews(){
        return userService.getUserReview(getUserEmail());
    }

}

