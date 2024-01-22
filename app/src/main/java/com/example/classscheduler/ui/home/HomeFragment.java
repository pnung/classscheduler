package com.example.classscheduler.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.classscheduler.R;
import com.example.classscheduler.ui.home.CourseViewModel;
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
            // Create a new course
            Course newCourse = new Course("Course Name", "Course Time", "Instructor Name");
            // Add course to ViewModel
            courseViewModel.addCourse(newCourse);
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
