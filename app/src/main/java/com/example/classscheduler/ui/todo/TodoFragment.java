package com.example.classscheduler.ui.todo;

import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.classscheduler.R;
import com.example.classscheduler.data.Task;
import com.example.classscheduler.databinding.FragmentTodoBinding;

import java.util.ArrayList;

public class TodoFragment extends Fragment {

    private FragmentTodoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TodoViewModel notificationsViewModel =
                new ViewModelProvider(this).get(TodoViewModel.class);

        binding = FragmentTodoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button todoTaskAddButton = root.findViewById(R.id.todo_task_add_button);

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
                viewHolder.getTaskNameView().setText(localDataSet.get(position).toString());
                viewHolder.getTaskDueDateView().setText(localDataSet.get(position).toString());
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


        RecyclerView todoRecyclerView = root.findViewById(R.id.todo_recycler_view);
        ArrayList<Task> taskArrayList = new ArrayList<>();
        CustomAdapter taskArrayListAdapter = new CustomAdapter(taskArrayList);
        todoRecyclerView.setAdapter(taskArrayListAdapter);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        todoTaskAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hello");
                taskArrayList.add(new Task("task"));
                todoRecyclerView.setAdapter(new CustomAdapter(taskArrayList));
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