package ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.R;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db.models.Task;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.repositories.TasksRepository;

/**
 * Created by Nuzhnykh Alexey on 18.03.2022.
 */

public class TasksListAdapter extends BaseAdapter {

    private List<Task> data = new ArrayList<>();
    private LayoutInflater inflater;
    private TasksRepository repository;

    public TasksListAdapter(Context ctx, TasksRepository repository) {
        inflater = LayoutInflater.from(ctx);
        this.repository = repository;
    }

    public void setData(List<Task> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = inflater.inflate(R.layout.task_item, null);
        }
        final Task t = data.get(position);
        TextView nameTV = (TextView) v.findViewById(R.id.task_item_task_name_tv);
        if (t != null) {
            nameTV.setText(t.getName());
            ImageButton doneBtn = (ImageButton) v.findViewById(R.id.task_item_done_btn);
            doneBtn.setImageResource(t.isDone() ?
                    R.drawable.ic_baseline_check_24 :
                    R.drawable.ic_baseline_outlined_flag_24);
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    t.setDone(!t.isDone());
                    repository.update(t);
                }
            });
            ImageButton removeBtn = (ImageButton) v.findViewById(R.id.task_item_remove_btn);
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repository.remove(t);
                }
            });
        }
        return v;
    }
}
