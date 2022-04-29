package ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ru.myitschool.vsu2021.nuzhnykh_a_v.todo_list.db.models.Task;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkJson() {
        String s = "{\"id\":0,\"name\":\"Do something\",\"deadline\":1649422859795,\"done\":false}";
        Gson gsonInstance = new Gson();
        /*//Task t = new Task(0, "Do something", new Date(System.currentTimeMillis()), false);
        Task t = gsonInstance.fromJson(s, Task.class);
        t.setDone(true);
        t.setName(t.getName() + " [OK]");
        System.out.println(gsonInstance.toJson(t));*/
        class MyLinkedList<T> {
            class Node{
                T value;
                Node next;
            }
            Node head;
            int size;
            public void add(T val) {
                Node n = new Node();
                n.value = val;
                n.next = head;
                head = n;
                size++;
            }

            @Override
            public String toString() {
                String result = "";
                Node cur = head;
                while (cur != null) {
                    result += cur.value;
                    cur = cur.next;
                }
                return result;
            }
        }

        MyLinkedList<Integer> arr = new MyLinkedList<>();
        arr.add(1);
        arr.add(2);
        arr.add(4);
        System.out.println(gsonInstance.toJson(arr));


        /*try {
            JSONObject x = new JSONObject();
            x.put("hello", "world");
            x.put("a", 1);

            JSONArray arr = new JSONArray();
            arr.put(1);
            arr.put("xxx");
            x.put("zzz", arr);

            System.out.println(x.toString(2));
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
    }
}