package com.example.classscheduler.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classscheduler.R;
import com.example.classscheduler.databinding.FragmentHomeBinding;
import com.example.classscheduler.data.Task;
import com.example.classscheduler.databinding.FragmentTodoBinding;
import com.example.classscheduler.ui.todo.TodoViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TodoViewModel notificationsViewModel =
                new ViewModelProvider(this).get(TodoViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button homeAddCourseButton = root.findViewById(R.id.home_add_course_button);

        // recycler view stuff

        class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

            private ArrayList<Task> localDataSet;

            /**
             * Provide a reference to the type of views that you are using
             * (custom ViewHolder)
             */
            class ViewHolder extends RecyclerView.ViewHolder {
                private final TextView taskName;
                private final TextView taskDueDate;

                public ViewHolder(View view) {
                    super(view);
                    // Define click listener for the ViewHolder's View

                    taskName = (TextView) view.findViewById(R.id.taskName);
                    taskDueDate = (TextView) view.findViewById(R.id.taskDueDate);
                }

                public TextView getTaskNameView() {
                    return taskName;
                }

                public TextView getTaskDueDateView() {
                    return taskDueDate;
                }
            }

            /**
             * Initialize the dataset of the Adapter
             *
             * @param dataSet String[] containing the data to populate views to be used
             * by RecyclerView
             */
            public CustomAdapter(ArrayList<Task> dataSet) {
                localDataSet = dataSet;
            }

            // Create new views (invoked by the layout manager)
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                // Create a new view, which defines the UI of the list item
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.text_row_item, viewGroup, false);

                return new ViewHolder(view);
            }

            // Replace the contents of a view (invoked by the layout manager)
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, final int position) {

                // Get element from your dataset at this position and replace the
                // contents of the view with that element
                viewHolder.getTaskNameView().setText(localDataSet.get(position).getName());
                viewHolder.getTaskDueDateView().setText(localDataSet.get(position).getDescription());
            }

            // Return the size of your dataset (invoked by the layout manager)
            @Override
            public int getItemCount() {
                return localDataSet.size();
            }

            public void add(Task task) {
                localDataSet.add(task);
            }
        }


        RecyclerView homeRecyclerView = root.findViewById(R.id.home_recycler_view);
        CustomAdapter taskArrayListAdapter = new CustomAdapter(Task.getTaskList());
        homeRecyclerView.setAdapter(taskArrayListAdapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        homeAddCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hello");
                Task T = new Task("taskName", "description");
                homeRecyclerView.setAdapter(new CustomAdapter(Task.getTaskList()));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}