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

    public void updateCourse(Course updatedCourse) {
        ArrayList<Course> currentCourses = courses.getValue();
        if (currentCourses != null) {
            for (int i = 0; i < currentCourses.size(); i++) {
                Course course = currentCourses.get(i);
                // Assuming name and instructorName are unique identifiers for a course
                if (course.getName().equals(updatedCourse.getName()) &&
                        course.getInstructorName().equals(updatedCourse.getInstructorName())) {
                    currentCourses.set(i, updatedCourse);
                    break;
                }
            }
            courses.setValue(currentCourses);
        }
    }

    public void deleteCourse(int position) {
        ArrayList<Course> currentCourses = courses.getValue();
        if (currentCourses != null) {
            if (position >= 0 && position < currentCourses.size()) {
                currentCourses.remove(position);
                courses.setValue(currentCourses);
            }
        }
    }
}
