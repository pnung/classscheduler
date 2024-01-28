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
import com.example.classscheduler.data.DateAndTime;
import com.example.classscheduler.data.Exam;
import com.example.classscheduler.data.Task;

import org.w3c.dom.Text;

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

        taskArrayListAdapter = new CustomAdapter(new ArrayList<>()/*, new ArrayList<>(), new ArrayList<>()*/);
        RecyclerView todoRecyclerView = root.findViewById(R.id.todo_recycler_view);
        todoRecyclerView.setAdapter(taskArrayListAdapter);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        taskViewModel.getTasks().observe(getViewLifecycleOwner(), updatedList -> {
            taskArrayListAdapter.updateData(updatedList);
        });

        Button todoTaskAddButton = root.findViewById(R.id.todo_task_add_button);
        todoTaskAddButton.setOnClickListener(v -> {
            TaskType taskType = getType();
            String type = taskType.getType();
            if (type.equals("Assignment")) {
                Task newAssignment = new Assignment("CALC HW", "MATH 1551", "1/27/24");
                taskViewModel.addTask(newAssignment);
            } else if (type.equals("Exam")) {
                Task newExam = new Exam("1554", "1/31/24", "6:30pm", "8:50pm");
                taskViewModel.addTask(newExam);
            } else {
                Task newTask = new Task("" + taskArrayListAdapter.getItemCount(), "Task Description", new DateAndTime(10, 30 - taskArrayListAdapter.getItemCount()));
                taskViewModel.addTask(newTask);
            }
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

        private TaskType taskType = getType();

        CustomAdapter(ArrayList<Task> dataSet) {
            localDataSet = dataSet;
        }





        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            TaskType taskType = getType();
            String type = taskType.getType();
            int layout = R.layout.todo_list_item;
            if (type.equals("Assignment")) {
                layout = R.layout.assignment_item;
            } else if (type.equals("Exam")) {
                layout= R.layout.exam_item;
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
                Assignment assignment = (Assignment) localDataSet.get(position);
                viewHolder.getAssignmentNameView().setText(assignment.getName());
                viewHolder.getAssignmentCourseNameView().setText(assignment.getCourseName());
                viewHolder.getAssignmentDueDateView().setText(assignment.getDueDate());
            } else if (type.equals("Exam")) {
                Exam exam = (Exam) localDataSet.get(position);
                viewHolder.getExamNameView().setText(exam.getName());
                viewHolder.getExamDateView().setText(exam.getExamDate());
                viewHolder.getExamStartTimeView().setText(exam.getExamStartTime());
                viewHolder.getExamEndTimeView().setText(exam.getExamEndTime());
            } else {
                Task task = localDataSet.get(position);
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
            private TaskType taskType = getType();
            private String type = taskType.getType();
            private TextView taskNameView;
            private TextView taskDescriptionView;
            private TextView taskDueDateView;
            private TextView taskTypeView;
            private TextView assignmentNameView;
            private TextView assignmentCourseNameView;
            private TextView assignmentDueDateView;
            private TextView examNameView;
            private TextView examDateView;
            private TextView examStartTimeView;
            private TextView examEndTimeView;


            //initiating the text boxes
            ViewHolder(View view) {
                super(view);
                //Task initializations

                if (type.equals("Assignment")) {
                    assignmentNameView = view.findViewById(R.id.assignmentName);
                    assignmentCourseNameView = view.findViewById(R.id.courseName);
                    assignmentDueDateView = view.findViewById(R.id.assignmentDueDate);
                } else if (type.equals("Exam")) {
                    examNameView = view.findViewById(R.id.examName);
                    examDateView = view.findViewById(R.id.examDate);
                    examStartTimeView = view.findViewById(R.id.examStartTime);
                    examEndTimeView = view.findViewById(R.id.examEndTime);

                } else {
                    taskNameView = view.findViewById(R.id.taskName);
                    taskDescriptionView = view.findViewById(R.id.taskDescription);
                    taskDueDateView = view.findViewById(R.id.taskDueDate);
                    taskTypeView = view.findViewById(R.id.taskType);
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

            public TextView getAssignmentNameView() {
                return assignmentNameView;
            }

            public TextView getAssignmentCourseNameView() {
                return assignmentCourseNameView;
            }

            public TextView getAssignmentDueDateView() {
                return assignmentDueDateView;
            }

            public TextView getExamNameView() {
                return examNameView;
            }

            public TextView getExamDateView() {
                return examDateView;
            }

            public TextView getExamStartTimeView() {
                return examStartTimeView;
            }

            public TextView getExamEndTimeView() {
                return examEndTimeView;
            }
        }
    }
}
