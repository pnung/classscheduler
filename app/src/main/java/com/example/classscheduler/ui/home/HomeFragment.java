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
import androidx.recyclerview.widget.ListAdapter;

import com.example.classscheduler.R;
import com.example.classscheduler.databinding.FragmentHomeBinding;
import com.example.classscheduler.data.Task;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        ListView homeText = root.findViewById(R.id.home_text);
        Button homeTestButton = root.findViewById(R.id.home_test_button);

        ArrayAdapter<Task> myArrayAdapter;
        ArrayList<Task> taskList = new ArrayList<>();
        myArrayAdapter = new ArrayAdapter<Task>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, taskList);
        homeText.setAdapter(myArrayAdapter);

        homeTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hello");
                myArrayAdapter.add(new Task("gnfasldjnkna"));
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