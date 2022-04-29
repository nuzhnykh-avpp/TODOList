package ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db.models.Task;

/**
 * Created by Nuzhnykh Alexey on 18.03.2022.
 */

public class Database {
    private static final int DATABASE_VERSION = 1;

    /****vvvvv Tasks table vvvvv*****/
    private static final String TASK_TABLE = "Tasks";
    private static final String COLUMN_TASK_ID = "id";
    private static final String COLUMN_TASK_NAME = "name";
    private static final String COLUMN_TASK_DEADLINE = "deadline";
    private static final String COLUMN_TASK_DONE = "done";
    /****^^^^^ Tasks table ^^^^^*****/

    SQLiteDatabase sqlite;

    public Database(Context ctx, String name) {
        OpenHelper openHelper = new OpenHelper(ctx, name);
        sqlite = openHelper.getWritableDatabase();
    }

    public void add(Task t) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK_NAME, t.getName());
        cv.put(COLUMN_TASK_DEADLINE, t.getDeadline().getTime());
        cv.put(COLUMN_TASK_DONE, t.isDone() ? 1 : 0);
        sqlite.insert(TASK_TABLE, null, cv);
    }
    public void update(Task t) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK_NAME, t.getName());
        cv.put(COLUMN_TASK_DEADLINE, t.getDeadline().getTime());
        cv.put(COLUMN_TASK_DONE, t.isDone() ? 1 : 0);
        sqlite.update(TASK_TABLE, cv, COLUMN_TASK_ID + " = ?",
                new String[]{""+t.getId()});
    }
    public void deleteTask(long id) {
        sqlite.delete(TASK_TABLE, COLUMN_TASK_ID + " = ?",
                new String[]{"" + id});
    }
    public void delete(Task t) {
        deleteTask(t.getId());
    }

    public Task getTask(long id) {
        Cursor c = sqlite.query(TASK_TABLE, null,
                COLUMN_TASK_ID + " = ?", new String[]{"" + id},
                null, null, null);
        c.moveToFirst();
        if (c.isAfterLast())
            return null;
        String name = c.getString(c.getColumnIndex(COLUMN_TASK_NAME));
        long deadline = c.getLong(c.getColumnIndex(COLUMN_TASK_DEADLINE));
        int done = c.getInt(c.getColumnIndex(COLUMN_TASK_DONE));
        return new Task(id, name, new Date(deadline), done != 0);
    }

    public List<Task> getAllTasks() {
        //SELECT * FROM Tasks;
        Cursor c = sqlite.query(TASK_TABLE, null,
                null, null,
                null, null, null);
        List<Task> result = new ArrayList<>();
        c.moveToFirst();
        if (c.isAfterLast())
            return result;
        do {
            long id = c.getLong(c.getColumnIndex(COLUMN_TASK_ID));
            String name = c.getString(c.getColumnIndex(COLUMN_TASK_NAME));
            long deadline = c.getLong(c.getColumnIndex(COLUMN_TASK_DEADLINE));
            int done = c.getInt(c.getColumnIndex(COLUMN_TASK_DONE));

            result.add(new Task(id, name, new Date(deadline), done != 0));
        } while (c.moveToNext());
        return result;
    }


    private class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(@Nullable Context context, String name) {
            super(context, name, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTasksTableQuery = "" +
                    "CREATE TABLE " + TASK_TABLE + " (\n" +
                    COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    COLUMN_TASK_NAME + " TEXT,\n" +
                    COLUMN_TASK_DEADLINE + " INTEGER,\n" +
                    COLUMN_TASK_DONE + " INTEGER\n" +
                    ");";
            db.execSQL(createTasksTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
            onCreate(db);
        }
    }
}
