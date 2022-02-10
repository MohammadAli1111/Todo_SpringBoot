package org.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todo.models.Role;
import org.todo.models.User;
import org.todo.repositories.RoleRepository;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("ALL")
@Service("RoleService")
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void save(Role role){
        roleRepository.save(role);
    }
  public Role findByRole(String role){
      return   roleRepository.findByRole(role);
    }
    public  Role findByUsers(User user){
      return roleRepository.findByUsers(user);
    }

    public List<Role> findAll() {
      return roleRepository.findAll();
    }
}
