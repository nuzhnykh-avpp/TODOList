package ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.for_retrofit;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db.models.Task;

/**
 * Created by Nuzhnykh Alexey on 15.04.2022.
 */

public interface TasksService {
    /*void insert(Task t);
    void remove(Task t);
    void update(Task t);*/

    @GET("/tasks.json")
    Call<List<Task>> getAll();

    @GET("/all_tasks/{task_name}.json")
    Call<Task> get(@Path("task_name") String task_name);

    @POST("/tasks")
    Call<Void> insert(@Body Task t);
}
