package org.todo.models;

import javax.persistence.*;

@SuppressWarnings("ALL")
@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    User user;


    public Todo() {
    }

    public Todo(String title) {
        this.title = title;
    }

    public Todo(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
