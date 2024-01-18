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

        ListView todoListView = root.findViewById(R.id.todo_listview);
        Button todoTaskAddButton = root.findViewById(R.id.todo_task_add_button);

        ArrayAdapter<Task> taskListArrayAdapter;
        ArrayList<Task> taskList = new ArrayList<>();
        taskListArrayAdapter = new ArrayAdapter<Task>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, taskList);
        todoListView.setAdapter(taskListArrayAdapter);

        todoTaskAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hello");
                taskListArrayAdapter.add(new Task("gnfasldjnkna"));
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