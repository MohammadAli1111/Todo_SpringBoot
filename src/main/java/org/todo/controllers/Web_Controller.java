package org.todo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.todo.models.User;
import org.todo.services.UserService;

@Controller
public class Web_Controller {
    @Autowired
    UserService userService;


    @GetMapping(value = {"/","/index"})
    public ModelAndView index(){

        return new ModelAndView("index");
    }

    @GetMapping(value = "/login")
    public ModelAndView get_Login(){
        return new ModelAndView("login");
    }

    @GetMapping(value = "/signup")
    public ModelAndView get_signUp(){
        return new ModelAndView("signUp","user",new User());
    }
    @PostMapping(value = "signup")
    public  String post_signup(@Validated User user){

        if(userService.findByEmail(user.getEmail())==null){
            userService.saveUser(user);
            return "redirect:/login";
        }else {
            return "redirect:/signup";
        }

    }

    @GetMapping("/home")
    public String home(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userAuth = userService.findByEmail(auth.getName());
        if (userService.isAdmin(userAuth)==1) {
            return "redirect:/Admin/home";
        }else {
            return "redirect:/User/home";
        }
    }


}
