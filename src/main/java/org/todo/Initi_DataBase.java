package org.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.todo.models.Role;
import org.todo.models.User;
import org.todo.services.RoleService;
import org.todo.services.UserService;

@Component
public class Initi_DataBase implements CommandLineRunner {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    // Save Role&Users when runtime
    @Override
    public void run(String... args) throws Exception {

        if(roleService.findAll().isEmpty()) {
            Role role_Admin = new Role();
            role_Admin.setRole("ADMIN");
            roleService.save(role_Admin);


            Role role_User = new Role();
            role_User.setRole("User");
            roleService.save(role_User);


            //____________Save Admin _____________

            User mohammad=new User("Admin Admin","Admin@gmail.com",
                    "12345678");



            userService.saveAdmin(mohammad);


        }


    }
}
