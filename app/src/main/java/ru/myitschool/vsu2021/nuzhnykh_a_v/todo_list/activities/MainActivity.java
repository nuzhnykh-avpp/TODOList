package ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.R;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.adapters.TasksListAdapter;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.database_repositories.TasksRepositoryDBImpl;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db.models.Task;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.network_repository.TasksRepositoryNetworkImpl;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.repositories.TasksRepository;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    private TasksListAdapter tasksAdapter;
    private TasksRepository tasksRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksRepository = new TasksRepositoryNetworkImpl();
        tasksAdapter = new TasksListAdapter(this, tasksRepository);
        ListView lv = (ListView)findViewById(R.id.main_activity_list);
        lv.setAdapter(tasksAdapter);

        Button addBtn = (Button)findViewById(R.id.main_activity_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setView(et)
                        .setTitle(R.string.main_activity__create_task_dialog__title_text)
                        .setMessage(R.string.main_activity__create_task_dialog__message_text)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = et.getText().toString();
                                if ("".equals(name)) {
                                    Toast.makeText(getApplicationContext(), "Нельзя пустое имя", Toast.LENGTH_LONG).show();
                                } else {
                                    Task t = new Task(name, new Date(), false);
                                    tasksRepository.insert(t);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });
        refresh();
        Button refreshBtn = (Button)findViewById(R.id.main_activity_refresh_btn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
    }

    private void refresh() {
        tasksRepository.getAll(new Consumer<List<Task>>() {
            @Override
            public void accept(List<Task> tasks) {
                tasksAdapter.setData(tasks);
            }
        });
    }
}