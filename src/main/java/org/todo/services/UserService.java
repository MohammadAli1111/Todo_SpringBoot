package org.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todo.models.Role;
import org.todo.models.User;
import org.todo.repositories.RoleRepository;
import org.todo.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void saveAdmin(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(Arrays.asList(userRole));
        user.setEnabled(true);
        userRepository.save(user);
    }
    public void saveUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("User");
        user.setRoles(Arrays.asList(userRole));
        user.setEnabled(true);
        userRepository.save(user);
    }
    public void delete(int id){
        userRepository.deleteById(id);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public int isAdmin(User user) {
        Role role = user.getRoles().get(0);
        if (role.getRole().equals("ADMIN")) return 1;
        else return 2;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        user.setRoles(Arrays.asList(roleService.findByUsers(user)));
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


}
