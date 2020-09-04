package no.kristiania.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class DetailController {

    @Autowired
    private MainController mainController;

}