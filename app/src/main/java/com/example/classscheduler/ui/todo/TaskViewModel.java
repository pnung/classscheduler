package com.example.classscheduler.ui.todo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.classscheduler.data.Task;
import java.util.ArrayList;

public class TaskViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Task>> tasks;

    public TaskViewModel() {
        tasks = new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<ArrayList<Task>> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        ArrayList<Task> currentTasks = tasks.getValue();
        if (currentTasks != null) {
            currentTasks.add(task);
            tasks.setValue(currentTasks);
        }
    }
}
