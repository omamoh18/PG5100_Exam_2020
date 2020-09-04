package no.kristiania.frontend.controller.admin;

import no.kristiania.backend.entity.User;
import no.kristiania.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UserController {

    @Autowired
    private UserService userService;

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public void changeEnabled(String email) {
        userService.changeEnabled(email);
    }

}
