package com.example.classscheduler.ui.todo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.classscheduler.R;
import com.example.classscheduler.data.Assignment;
import com.example.classscheduler.data.DateAndTime;
import com.example.classscheduler.data.Exam;
import com.example.classscheduler.data.Task;
import com.example.classscheduler.ui.todo.TaskViewModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TodoFragment extends Fragment {

    private TaskViewModel taskViewModel;
    private CustomAdapter taskArrayListAdapter;
    private TaskType type = new TaskType("");

    Comparator<Task> chronologicalSorter = new Task.ChronologicalSort();
    Comparator<Task> alphabeticalSorter = new Task.AlphabeticalSort();
    Comparator<Task> currentSorter = chronologicalSorter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_todo, container, false);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        taskArrayListAdapter = new CustomAdapter(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        RecyclerView todoRecyclerView = root.findViewById(R.id.todo_recycler_view);
        todoRecyclerView.setAdapter(taskArrayListAdapter);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        taskViewModel.getTasks().observe(getViewLifecycleOwner(), updatedList -> {
            taskArrayListAdapter.updateData(updatedList, updatedList, updatedList);
        });

        Button todoTaskAddButton = root.findViewById(R.id.todo_task_add_button);
        todoTaskAddButton.setOnClickListener(v -> {
            System.out.println("a");
            Task newTask = new Task("" + taskArrayListAdapter.getItemCount(), "Task Description", new DateAndTime(10, 30 - taskArrayListAdapter.getItemCount()));
            System.out.println("b");
            // Add task to ViewModel
            taskViewModel.addTask(newTask);
            Collections.sort(taskArrayListAdapter.getLocalDataSet(), currentSorter);
        });


        AppCompatSpinner sortOptionsDropdown = root.findViewById(R.id.sort_options_dropdown);
        sortOptionsDropdown.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Time", "Name", "Type", "Class"}));

        sortOptionsDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSort = (String) sortOptionsDropdown.getSelectedItem();
                if (selectedSort.equals("Time")) {
                    currentSorter = chronologicalSorter;
                } else if (selectedSort.equals("Name")) {
                    currentSorter = alphabeticalSorter;
                }
                Collections.sort(taskArrayListAdapter.getLocalDataSet(), currentSorter);
                taskArrayListAdapter.updateData(taskArrayListAdapter.getLocalDataSet());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Adding type of task (exam, assignment, task)
        AppCompatSpinner addOptionsDropdown = root.findViewById(R.id.add_options_dropdown);
        addOptionsDropdown.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[] {"Assignment", "Task", "Exam"}));

        addOptionsDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type.setType((String) addOptionsDropdown.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return root;
    }

    public TaskType getType() {
        return type;
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private ArrayList<Task> localDataSet;

        private ArrayList<Assignment> localDataSet_Assignments;

        private ArrayList<Exam> localDataSet_Exams;

        private TaskType taskType = getType();

        CustomAdapter(ArrayList<Task> dataSet, ArrayList<Assignment> dataSet2, ArrayList<Exam> dataSet3) {
            localDataSet = dataSet;
            localDataSet_Assignments = dataSet2;
            localDataSet_Exams = dataSet3;
        }





        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            TaskType taskType = getType();
            String type = taskType.getType();
            int layout = R.layout.todo_list_item;
            if (type.equals("Assignment")) {
                layout = R.layout.assignment_item;
            }
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(layout, viewGroup, false);
            return new ViewHolder(view);
        }

        //Setting the text
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            TaskType taskType = getType();
            String type = taskType.getType();
            if (type.equals("Assignment")) {
                Assignment assignment = localDataSet_Assignments.get(position);
                Log.d("myTag", "Assignment has been text-set in onBind");
                viewHolder.getAssignmentCourseName().setText(assignment.getCourseName());
                viewHolder.getTaskNameView().setText(assignment.getName());
                //Log.d("myTag", "Assignment has been text-set in onBind");
            } else {
                Task task = localDataSet.get(position);
                viewHolder.getTaskNameView().setText(task.getName());
                viewHolder.getTaskDescriptionView().setText(task.getDescription());
                viewHolder.getTaskDueDateView().setText(task.getCardTime());
                Log.d("myTag", "Task has been text-set in onBind");
            }
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }

        public void updateData(ArrayList<Task> newData, ArrayList<Assignment> newData2, ArrayList<Exam> newData3) {
            localDataSet = newData;
            localDataSet_Assignments = newData2;
            localDataSet_Exams = newData3;

            notifyDataSetChanged();
        }

        public ArrayList<Task> getLocalDataSet() {
            return localDataSet;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TaskType taskType = getType();
            private String type = taskType.getType();
            private final TextView taskNameView;
            private final TextView taskDescriptionView;
            private final TextView taskDueDateView;
            private final TextView taskTypeView;
            private TextView assignmentCourseName;

            //initiating the text boxes
            ViewHolder(View view) {
                super(view);
                //Task initializations
                taskNameView = view.findViewById(R.id.taskName);
                taskDescriptionView = view.findViewById(R.id.taskDescription);
                taskDueDateView = view.findViewById(R.id.taskDueDate);
                taskTypeView = view.findViewById(R.id.taskType);

                if (type.equals("Assignment")) {
                    Log.d("myTag", "Assignment has been initialized");
                    assignmentCourseName = view.findViewById(R.id.courseName);
                }
            }

            TextView getTaskNameView() {
                return taskNameView;
            }

            TextView getTaskDescriptionView() {
                return taskDescriptionView;
            }

            public TextView getTaskDueDateView() {
                return taskDueDateView;
            }

            public TextView getTaskTypeView() {
                return taskTypeView;
            }

            public TextView getAssignmentCourseName() {
                return assignmentCourseName;
            }
        }
    }
}
