package org.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todo.models.Todo;
import org.todo.repositories.TodoRepository;

import java.util.List;

@SuppressWarnings("ALL")
@Service
@Transactional
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public void save(Todo todo){
        todoRepository.save(todo);
    }
    public void delete(int id){
        todoRepository.deleteById(id);
    }
    public List<Todo> findAll(){
        return todoRepository.findAll();
    }

    public Todo findById(int id) {
       return todoRepository.getById(id);
    }
}
