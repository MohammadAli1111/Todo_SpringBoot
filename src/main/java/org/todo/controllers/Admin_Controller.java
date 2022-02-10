package org.todo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/Admin")
public class Admin_Controller {

    @GetMapping("/home")
    public ModelAndView get_Home(){
        return new ModelAndView("home_admin");
    }
}
