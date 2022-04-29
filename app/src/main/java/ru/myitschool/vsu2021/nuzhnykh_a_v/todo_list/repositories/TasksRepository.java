package ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.repositories;

import java.util.List;
import java.util.function.Consumer;

import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db.models.Task;

/**
 * Created by Nuzhnykh Alexey on 18.03.2022.
 */

public interface TasksRepository {
    void insert(Task t);
    void remove(Task t);
    void update(Task t);
    void getAll(Consumer<List<Task>> callback);
}
