package com.example.classscheduler.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.classscheduler.data.Course;
import java.util.ArrayList;

public class CourseViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Course>> courses;

    public CourseViewModel() {
        courses = new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<ArrayList<Course>> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        ArrayList<Course> currentCourses = courses.getValue();
        if (currentCourses != null) {
            currentCourses.add(course);
            courses.setValue(currentCourses);
        }
    }
}
