package com.example.classscheduler.ui.todo;

import android.os.Bundle;
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
import com.example.classscheduler.data.DateAndTime;
import com.example.classscheduler.data.Task;
import com.example.classscheduler.ui.todo.TaskViewModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TodoFragment extends Fragment {

    private TaskViewModel taskViewModel;
    private CustomAdapter taskArrayListAdapter;


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
        todoTaskAddButton.setOnClickListener(new AddTaskButtonOnClickListener(requireActivity(), getContext(), taskViewModel));


        AppCompatSpinner sortOptionsDropdown = root.findViewById(R.id.sort_options_dropdown);
        sortOptionsDropdown.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Time", "Name", "Type", "Class"}));

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


        return root;
    }


    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private ArrayList<Task> localDataSet;

        public CustomAdapter(ArrayList<Task> dataSet) {
            localDataSet = dataSet;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.todo_list_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            Task task = localDataSet.get(position);
            viewHolder.getTaskNameView().setText(task.getName());
            viewHolder.getTaskDescriptionView().setText(task.getDescription());
            viewHolder.getTaskDueDateView().setText(task.getCardTime());
            viewHolder.getTaskTypeView().setText(task.getType());
            viewHolder.addEditOnClickListener(position);
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
            private final TextView taskTypeView;
            private View view;

            ViewHolder(View view) {
                super(view);
                this.view = view;
                taskNameView = view.findViewById(R.id.taskName);
                taskDescriptionView = view.findViewById(R.id.taskDescription);
                taskDueDateView = view.findViewById(R.id.taskDueDate);
                taskTypeView = view.findViewById(R.id.taskType);
            }

            void addEditOnClickListener(int position) {
                view.setOnClickListener(new EditTaskOnClickListener(requireActivity(), getContext(), taskViewModel, position));
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
        }
    }
}