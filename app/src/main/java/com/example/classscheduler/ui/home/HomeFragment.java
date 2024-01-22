package com.example.classscheduler.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.classscheduler.R;
import com.example.classscheduler.data.Course;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private CourseViewModel courseViewModel;
    private CustomAdapter courseArrayListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        courseArrayListAdapter = new CustomAdapter(new ArrayList<>());
        RecyclerView homeRecyclerView = root.findViewById(R.id.home_recycler_view);
        homeRecyclerView.setAdapter(courseArrayListAdapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        courseViewModel.getCourses().observe(getViewLifecycleOwner(), updatedList -> {
            courseArrayListAdapter.updateData(updatedList);
        });

        Button homeAddCourseButton = root.findViewById(R.id.home_add_course_button);
        homeAddCourseButton.setOnClickListener(v -> {
            LayoutInflater inflater1 = requireActivity().getLayoutInflater();
            View dialogView = inflater1.inflate(R.layout.dialog_add_course, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialogView)
                    .setTitle("Add New Course")
                    .setPositiveButton("Add", (dialog, id) -> {
                        EditText editCourseName = dialogView.findViewById(R.id.edit_course_name);
                        EditText editCourseDays = dialogView.findViewById(R.id.edit_course_days);
                        EditText editInstructorName = dialogView.findViewById(R.id.edit_instructor_name);
                        EditText editStartTime = dialogView.findViewById(R.id.edit_start_time);
                        EditText editEndTime = dialogView.findViewById(R.id.edit_end_time);

                        Course newCourse = new Course(
                                editCourseName.getText().toString(), editInstructorName.getText().toString(),
                                editCourseDays.getText().toString() + " " + editStartTime.getText().toString() + " - " + editEndTime.getText().toString());
                        courseViewModel.addCourse(newCourse);
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> {
                        // User cancelled the dialog
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return root;
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private ArrayList<Course> localDataSet;

        CustomAdapter(ArrayList<Course> dataSet) {
            localDataSet = dataSet;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.course_list_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            Course course = localDataSet.get(position);
            viewHolder.getCourseNameView().setText(course.getName());
            viewHolder.getCourseTimeView().setText(course.getCourseTime());
            viewHolder.getInstructorNameView().setText(course.getInstructorName());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }

        public void updateData(ArrayList<Course> newData) {
            localDataSet = newData;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView courseNameView;
            private final TextView courseTimeView;
            private final TextView instructorNameView;

            ViewHolder(View view) {
                super(view);
                courseNameView = view.findViewById(R.id.courseName);
                courseTimeView = view.findViewById(R.id.courseTime);
                instructorNameView = view.findViewById(R.id.instructorName);
            }

            TextView getCourseNameView() {
                return courseNameView;
            }

            TextView getCourseTimeView() {
                return courseTimeView;
            }

            TextView getInstructorNameView() {
                return instructorNameView;
            }
        }
    }
}
