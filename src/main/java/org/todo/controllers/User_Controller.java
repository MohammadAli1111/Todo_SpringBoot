package org.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.todo.models.Todo;
import org.todo.models.User;
import org.todo.services.TodoService;
import org.todo.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/User")
public class User_Controller {

    @Autowired
    TodoService todoService;
    @Autowired
    UserService userService;

    User user_Auth=null;

    @GetMapping("/home")
    public ModelAndView get_Home(@Param("Message") String Message){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user_Auth = userService.findByEmail(auth.getName());
        ModelAndView modelAndView=new ModelAndView("home_user","todo",new Todo());
        modelAndView.addObject("todos",user_Auth.getTodos());

        if(Message!=null){
            if(Message.contains("Done")){
                modelAndView.addObject("Message_Done",Message);
            }else {
                modelAndView.addObject("Message_Error",Message);
            }

        }
        return modelAndView;
    }

    @PostMapping ("/save")
    public void  save(@Validated Todo todo, HttpServletResponse response) throws IOException {
       try {
           todo.setUser(user_Auth);
           todoService.save(todo);
           response.sendRedirect("/User/home?Message=Done Save");
       }catch (Exception e){
           response.sendRedirect("/User/home?Message=Error Save");
       }

    }
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable("id") int id, HttpServletResponse response) throws IOException {
        try {
            todoService.delete(id);
            response.sendRedirect("/User/home?Message=Done Delete");
        }catch (Exception e){
            response.sendRedirect("/User/home?Message=Error Delete");
        }
    }
    @GetMapping ("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") int id){

        return new ModelAndView("edit_Todo","todo",todoService.findById(id));
    }
    @PostMapping ("/update")
    public void  update(@Validated Todo todo, HttpServletResponse response) throws IOException {
        try {
            todo.setUser(user_Auth);
            todoService.save(todo);
            response.sendRedirect("/User/home?Message=Done update");
        }catch (Exception e){
            response.sendRedirect("/User/home?Message=Error update");
        }

    }


}
