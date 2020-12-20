package ru.mirea.pr3;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ResultThread extends Thread {

    private ConcurrentHashMap<String, Task> tasks = null;

    public ResultThread(ConcurrentHashMap<String, Task> tasks) {
        this.tasks = tasks;
    }

    public void run() {
        while (true){
            if (tasks.size() != 0){
                Iterator<ConcurrentHashMap.Entry<String, Task>> itr = tasks.entrySet().iterator();
                while (itr.hasNext()) {
                    ConcurrentHashMap.Entry<String, Task> entry = itr.next();
                    String key = entry.getKey();
                    Task t = entry.getValue();
                    if(t.flag != false){
                        t.out.println(t.result);
                        tasks.remove(key);
                    }
                }
            }
        }
    }
}
