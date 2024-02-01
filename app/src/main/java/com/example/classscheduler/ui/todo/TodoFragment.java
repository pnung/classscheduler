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
    private String selectedType = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_todo, container, false);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        taskArrayListAdapter = new CustomAdapter(new ArrayList<>());
        RecyclerView todoRecyclerView = root.findViewById(R.id.todo_recycler_view);
        todoRecyclerView.setAdapter(taskArrayListAdapter);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        taskViewModel.getTasks().observe(getViewLifecycleOwner(), updatedList -> {
            taskArrayListAdapter.updateData(updatedList);
        });


        Button todoTaskAddButton = root.findViewById(R.id.todo_task_add_button);
        if (selectedType.equals("Assignment")) {
            todoTaskAddButton.setOnClickListener(new AddAssignmentOnClickListener(requireActivity(), getContext(), taskViewModel));
        } else {
            todoTaskAddButton.setOnClickListener(new AddTaskButtonOnClickListener(requireActivity(), getContext(), taskViewModel));
        }

        AppCompatSpinner sortOptionsDropdown = root.findViewById(R.id.sort_options_dropdown);
        sortOptionsDropdown.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Time", "Name", "Type", "Class"}));

        //Adding type of task (exam, assignment, task)
        AppCompatSpinner addOptionsDropdown = root.findViewById(R.id.add_options_dropdown);
        addOptionsDropdown.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[] {"Assignment", "Task", "Exam"}));


        sortOptionsDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSort = (String) sortOptionsDropdown.getSelectedItem();
                if (selectedSort.equals("Time")) {
                    taskViewModel.setSorter("Time");
                } else if (selectedSort.equals("Name")) {
                    taskViewModel.setSorter("Name");
                }
                //Collections.sort(taskArrayListAdapter.getLocalDataSet(), currentSorter);
                //taskArrayListAdapter.updateData(taskArrayListAdapter.getLocalDataSet());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        addOptionsDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = addOptionsDropdown.getSelectedItem().toString();
                todoTaskAddButton.setText("New " + selectedType);
                if (selectedType.equals("Assignment")) {
                    todoTaskAddButton.setOnClickListener(new AddAssignmentOnClickListener(requireActivity(), getContext(), taskViewModel));
                } else {
                    todoTaskAddButton.setOnClickListener(new AddTaskButtonOnClickListener(requireActivity(), getContext(), taskViewModel));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return root;
    }
    

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private ArrayList<Task> localDataSet;

        CustomAdapter(ArrayList<Task> dataSet) {
            localDataSet = dataSet;
        }

        @Override
        public int getItemViewType(int position) {
            if (localDataSet.get(position) instanceof Assignment) {
                return 1;
            } else {
                return 0;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            int layout = -1;
            if (viewType == 0) {
                layout = R.layout.todo_list_item;
            } else {
                layout = R.layout.assignment_item;
            }
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(layout, viewGroup, false);
            return new ViewHolder(view, selectedType);
        }

        //Setting the text
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            Task task = localDataSet.get(position);
            viewHolder.getTaskNameView().setText(task.getName());
            viewHolder.getTaskDescriptionView().setText(task.getDescription());
            viewHolder.getTaskDueDateView().setText(task.getCardTime());
            viewHolder.addEditOnClickListener(position);
            if (task instanceof Assignment) {
                System.out.println(position);
                Assignment assignment = (Assignment) task; 
                viewHolder.getAssignmentCourseName().setText(assignment.getCourseName());
                viewHolder.getTaskNameView().setText(assignment.getName());
            } else {
                viewHolder.getTaskNameView().setText(task.getName());
                viewHolder.getTaskDescriptionView().setText(task.getDescription());
                viewHolder.getTaskDueDateView().setText(task.getCardTime());
            }
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }

        public void updateData(ArrayList<Task> newData) {
            localDataSet = newData;
            notifyDataSetChanged();
        }

        public ArrayList<Task> getLocalDataSet() {
            return localDataSet;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView taskNameView;
            private final TextView taskDescriptionView;
            private final TextView taskDueDateView;
            private View view;
            private TextView assignmentCourseName;

            //initiating the text boxes
            ViewHolder(View view, String selectedType) {
                super(view);
                this.view = view;
                taskNameView = view.findViewById(R.id.taskName);
                taskDescriptionView = view.findViewById(R.id.taskDescription);
                taskDueDateView = view.findViewById(R.id.taskDueDate);

                if (selectedType.equals("Assignment")) {
                    assignmentCourseName = view.findViewById(R.id.courseName);
                }
            }

            void addEditOnClickListener(int position) {
                if (selectedType.equals("Assignment")) {

                } else {
                    view.setOnClickListener(new EditTaskOnClickListener(requireActivity(), getContext(), taskViewModel, position));
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

            public TextView getAssignmentCourseName() {
                return assignmentCourseName;
            }
        }
    }
}