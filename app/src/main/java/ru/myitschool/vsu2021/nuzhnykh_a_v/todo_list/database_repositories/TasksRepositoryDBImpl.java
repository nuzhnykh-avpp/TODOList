package ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.database_repositories;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.function.Consumer;

import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db.Database;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db.models.Task;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.repositories.TasksRepository;

/**
 * Created by Nuzhnykh Alexey on 18.03.2022.
 */

public class TasksRepositoryDBImpl implements TasksRepository {
    private Database database;

    public TasksRepositoryDBImpl(Context ctx, String name) {
        database = new Database(ctx, name);
    }

    private static class InsertTask extends AsyncTask<Void, Void, Void> {
        private Database db;
        private Task t;

        public InsertTask(Database db, Task t) {
            this.db = db;
            this.t = t;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.add(t);
            return null;
        }
    }

    @Override
    public void insert(Task t) {
        new InsertTask(database, t).execute();
    }

    private static class RemoveTask extends AsyncTask<Void, Void, Void> {
        private Database db;
        private Task t;

        public RemoveTask(Database db, Task t) {
            this.db = db;
            this.t = t;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.delete(t);
            return null;
        }
    }

    @Override
    public void remove(Task t) {
        new RemoveTask(database, t).execute();
    }

    private static class UpdateTask extends AsyncTask<Void, Void, Void> {
        private Database db;
        private Task t;

        public UpdateTask(Database db, Task t) {
            this.db = db;
            this.t = t;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.update(t);
            return null;
        }
    }
    @Override
    public void update(Task t) {
        new UpdateTask(database, t).execute();
    }

    private static class GetAllTask extends AsyncTask<Void, Void, List<Task>> {
        private Database db;
        private Consumer<List<Task>> consumer;

        public GetAllTask(Database db, Consumer<List<Task>> consumer) {
            this.db = db;
            this.consumer = consumer;
        }


        @Override
        protected List<Task> doInBackground(Void... voids) {
            return db.getAllTasks();
        }

        @Override
        protected void onPostExecute(List<Task> tasks) {
            consumer.accept(tasks);
        }
    }
    @Override
    public void getAll(Consumer<List<Task>> callback) {
        new GetAllTask(database, callback).execute();
    }
}
