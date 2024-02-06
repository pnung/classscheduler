package com.example.classscheduler.ui.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.classscheduler.R;
import com.example.classscheduler.data.Assignment;
import com.example.classscheduler.data.Exam;
import com.example.classscheduler.data.Task;

import java.util.ArrayList;

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
        } else if (selectedType.equals("Exam")) {
            todoTaskAddButton.setOnClickListener(new AddExamOnClickListener(requireActivity(), getContext(), taskViewModel));
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
                } else if (selectedType.equals("Exam")) {
                    todoTaskAddButton.setOnClickListener(new AddExamOnClickListener(requireActivity(), getContext(), taskViewModel));
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
            } else if (localDataSet.get(position) instanceof Exam) {
                return 2;
            } else {
                return 0;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            final int layout;
            if (viewType == 0) {
                layout = R.layout.todo_list_item;
            } else if (viewType == 1) {
                layout = R.layout.assignment_item;
            } else if (viewType == 2) {
                layout = R.layout.exam_item;
            } else {
                layout = -1;
            }

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(layout, viewGroup, false);
            return new ViewHolder(view, viewType);
        }

        //Setting the text
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            //viewHolder.setIsRecyclable(false);
            Task task = localDataSet.get(position);
            viewHolder.getTaskNameView().setText(task.getName());
            viewHolder.getTaskDueDateView().setText(task.getCardTime());
            viewHolder.addEditOnClickListener(position);
            if (task instanceof Assignment) {
                Assignment assignment = (Assignment) task;
                viewHolder.getTaskDescriptionView().setText(assignment.getDescription());
                viewHolder.getCourseName().setText(assignment.getCourseName());
                viewHolder.getTaskNameView().setText(assignment.getName());
            } else if (task instanceof Exam) {
                Exam exam = (Exam) task;
                viewHolder.getExamDate().setText(exam.getPrimaryDateAndTime().getDateString());
                viewHolder.getExamEndTime().setText(exam.getExamEndTime().getTimeString());
                viewHolder.getTaskDueDateView().setText(exam.getPrimaryDateAndTime().getTimeString());
            } else {
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
            private TextView taskNameView;
            private TextView taskDescriptionView;
            private TextView taskDueDateView;
            private View view;
            private TextView courseName;
            private TextView examEndTime;
            private TextView examDate;

            //initiating the text boxes
            ViewHolder(View view, int viewType) {
                super(view);
                this.view = view;
                taskNameView = view.findViewById(R.id.taskName);
                taskDueDateView = view.findViewById(R.id.taskDueDate);

                if (viewType == 1) {
                    courseName = view.findViewById(R.id.courseName);
                    taskDescriptionView = view.findViewById(R.id.taskDescription);
                }

                if (viewType == 2) {
                    courseName = view.findViewById(R.id.courseName);
                    examEndTime = view.findViewById(R.id.examEndTime);
                    examDate =view.findViewById(R.id.examDate);
                }

                if (viewType == 0) {
                    taskDescriptionView = view.findViewById(R.id.taskDescription);
                }
            }

            void addEditOnClickListener(int position) {
                if (localDataSet.get(position) instanceof Assignment) {
                    view.setOnClickListener(new EditAssignmentOnClickListener(requireActivity(), getContext(), taskViewModel, position));
                } else if (localDataSet.get(position) instanceof Exam) {
                    view.setOnClickListener(new EditExamOnClickListener(requireActivity(), getContext(), taskViewModel, position));
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

            public TextView getCourseName() {
                return courseName;
            }

            public TextView getExamEndTime() {
                return examEndTime;
            }

            public TextView getExamDate() {
                return examDate;
            }
        }
    }
}