package ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.network_repository;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db.Database;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db.models.Task;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.for_retrofit.TasksService;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.repositories.TasksRepository;

/**
 * Created by Nuzhnykh Alexey on 08.04.2022.
 */

public class TasksRepositoryNetworkImpl implements TasksRepository {
    @Override
    public void insert(Task t) {

    }

    @Override
    public void remove(Task t) {

    }

    @Override
    public void update(Task t) {

    }

    public static class TasksList {
        private Task[] data = new Task[0];

        public TasksList() {
        }
        public TasksList(Task[] data) {
            this.data = data;
        }

        public List<Task> getData() {
            return Arrays.asList(data);
        }
    }
    private static class GetAllTask extends AsyncTask<Void, Void, TasksList> {
        private Consumer<List<Task>> consumer;

        public GetAllTask(Consumer<List<Task>> consumer) {
            this.consumer = consumer;
        }


        @Override
        protected TasksList doInBackground(Void... voids) {
            /*String url = "https://raw.githubusercontent.com/nuzhnykh-avpp/todo-content/main/tasks.json";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String result = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(result, TasksList.class);
            } catch (Exception ex) {
                ex.printStackTrace();
                return new TasksList();
            }*/

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://raw.githubusercontent.com/nuzhnykh-avpp/todo-content/main")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TasksService service = retrofit.create(TasksService.class);

            Call<List<Task>> call = service.getAll();

            try {
                Response<List<Task>> response = call.execute();
                List<Task> result = response.body();
                return new TasksList(result.toArray(new Task[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new TasksList();
        }

        @Override
        protected void onPostExecute(TasksList tasks) {
            consumer.accept(tasks.getData());
        }
    }

    @Override
    public void getAll(Consumer<List<Task>> callback) {
        new GetAllTask(callback).execute();
    }
}
