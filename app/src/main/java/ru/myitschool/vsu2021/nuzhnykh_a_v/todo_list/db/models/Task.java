package ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db.models;

import java.util.Date;

/**
 * Created by Nuzhnykh Alexey on 18.03.2022.
 */

public class Task {
    private Long id = null;
    private String name;
    private long deadline;
    private boolean done;

    public Task(long id, String name, long deadline, boolean done) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.done = done;
    }
    public Task(long id, String name, Date deadline, boolean done) {
        this(id, name, deadline.getTime(), done);
    }


    public Task(String name, long deadline, boolean done) {
        this.name = name;
        this.deadline = deadline;
        this.done = done;
    }
    public Task(String name, Date deadline, boolean done) {
        this(name, deadline.getTime(), done);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDeadline() {
        return new Date(deadline);
    }

    public boolean isDone() {
        return done;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline.getTime();
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
