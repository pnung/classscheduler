package com.example.classscheduler.ui.todo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classscheduler.data.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Task>> tasks;

    private Comparator<Task> chronologicalSorter = new Task.ChronologicalSort();
    private Comparator<Task> alphabeticalSorter = new Task.AlphabeticalSort();
    private Comparator<Task> currentSorter = chronologicalSorter;

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
        sort();
    }

    public void set(int index, Task task) {
        ArrayList<Task> currentTasks = tasks.getValue();
        if (currentTasks != null) {
            currentTasks.set(index, task);
            tasks.setValue(currentTasks);
        }
        sort();
    }

    public void remove(int index) {
        ArrayList<Task> currentTasks = tasks.getValue();
        if (currentTasks != null) {
            currentTasks.remove(index);
            tasks.setValue(currentTasks);
        }
    }

    public void setSorter(String sorter) {
        if (sorter.equals("Time")) {
            currentSorter = chronologicalSorter;
        } else if (sorter.equals("Name")) {
            currentSorter = alphabeticalSorter;
        }
        sort();
    }

    public void sort() {
        ArrayList<Task> currentTasks = tasks.getValue();
        Collections.sort(currentTasks, currentSorter);
        tasks.setValue(currentTasks);
        if (currentSorter == alphabeticalSorter) {
            System.out.println("sorting alphabetically");
        }
    }
}
