package org.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.todo.models.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer> {

}
