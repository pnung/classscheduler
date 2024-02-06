package com.example.classscheduler.ui.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.example.classscheduler.R;
import com.example.classscheduler.data.Assignment;
import com.example.classscheduler.data.DateAndTime;

public class EditAssignmentOnClickListener implements View.OnClickListener {

    private FragmentActivity requiredActivity;
    private Context context;
    private TaskViewModel taskViewModel;
    private int position;

    Integer[] baseDays = new Integer[31];
    Integer[] days = {0};

    ArrayAdapter<Integer> dayArrayAdapter;



    public EditAssignmentOnClickListener(FragmentActivity requiredActivity, Context context, TaskViewModel taskViewModel, int position) {
        this.requiredActivity = requiredActivity;
        this.context = context;
        this.taskViewModel = taskViewModel;
        this.position = position;
    }


    @Override
    public void onClick(View v) {

        for (int i = 0; i < 31; i++) {
            baseDays[i] = i+1;
        }

        LayoutInflater inflater1 = requiredActivity.getLayoutInflater();
        View dialogView = inflater1.inflate(R.layout.dialog_edit_assignment, null);

        Assignment currentAssignment = (Assignment) taskViewModel.getTasks().getValue().get(position);

        Spinner selectMonthSpinner = dialogView.findViewById(R.id.select_month_spinner);
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        ArrayAdapter<String> monthArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, months);
        selectMonthSpinner.setAdapter(monthArrayAdapter);

        Spinner selectDaySpinner = dialogView.findViewById(R.id.select_day_spinner);
        dayArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, days);
        selectDaySpinner.setAdapter(dayArrayAdapter);

        Spinner selectYearSpinner = dialogView.findViewById(R.id.select_year_spinner);
        Integer[] years = {2024, 2025, 2026, 2027, 2028, 2029};
        ArrayAdapter<Integer> yearArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, years);
        selectYearSpinner.setAdapter(yearArrayAdapter);

        Spinner selectHourSpinner = dialogView.findViewById(R.id.select_hour_spinner);
        Integer[] hours = {12,1,2,3,4,5,6,7,8,9,10,11};
        ArrayAdapter<Integer> hourArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, hours);
        selectHourSpinner.setAdapter(hourArrayAdapter);

        Spinner selectMinuteSpinner = dialogView.findViewById(R.id.select_minute_spinner);
        String[] minutes = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
        ArrayAdapter<String> minuteArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, minutes);
        selectMinuteSpinner.setAdapter(minuteArrayAdapter);

        Spinner selectAmOrPmSpinner = dialogView.findViewById(R.id.select_am_pm);
        String[] amOrPm = {"AM", "PM"};
        ArrayAdapter<String> amOrPmAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, amOrPm);
        selectAmOrPmSpinner.setAdapter(amOrPmAdapter);

        EditText setTaskName = dialogView.findViewById(R.id.set_task_name);
        setTaskName.setText(currentAssignment.getName());

        EditText setTaskDescription = dialogView.findViewById(R.id.set_task_description);
        setTaskDescription.setText(currentAssignment.getDescription());

        EditText setAssignmentCourse = dialogView.findViewById(R.id.set_exam_course);
        setAssignmentCourse.setText(currentAssignment.getCourse());

        days = new Integer[DateAndTime.getNumberOfDays(DateAndTime.getMonthStringFromNumber(currentAssignment.getPrimaryDateAndTime().getMonth()))];
        for (int i = 0; i < days.length; i++) days[i] = i+1;
        dayArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, days);
        selectDaySpinner.setAdapter(dayArrayAdapter);

        selectMonthSpinner.setSelection(currentAssignment.getPrimaryDateAndTime().getMonth() - 1);
        selectDaySpinner.setSelection(currentAssignment.getPrimaryDateAndTime().getDay() - 1);
        selectYearSpinner.setSelection(currentAssignment.getPrimaryDateAndTime().getYear() - 2024);
        selectHourSpinner.setSelection(currentAssignment.getPrimaryDateAndTime().getHour() % 12);
        selectMinuteSpinner.setSelection(currentAssignment.getPrimaryDateAndTime().getMinute() / 5);
        selectAmOrPmSpinner.setSelection(currentAssignment.getPrimaryDateAndTime().isPM() ? 1 : 0);


        selectMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                days = new Integer[DateAndTime.getNumberOfDays(selectMonthSpinner.getSelectedItem().toString())];
                for (int i = 0; i < days.length; i++) days[i] = i+1;
                dayArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, days);
                selectDaySpinner.setAdapter(dayArrayAdapter);
                if (currentAssignment.getPrimaryDateAndTime().getDay() >= days.length) {
                    currentAssignment.getPrimaryDateAndTime().setDay(days.length);
                }
                selectDaySpinner.setSelection(currentAssignment.getPrimaryDateAndTime().getDay() - 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setTitle("Edit Assignment")
                .setPositiveButton("Add", (dialog, id) -> {

                    Assignment newAssignment = new Assignment(
                            setTaskName.getText().toString(),
                            setTaskDescription.getText().toString(),
                            new DateAndTime(
                                    (int) selectYearSpinner.getSelectedItem(),
                                    DateAndTime.getMonthNumberFromString(selectMonthSpinner.getSelectedItem().toString()),
                                    (int) selectDaySpinner.getSelectedItem(),
                                    selectAmOrPmSpinner.getSelectedItem().toString().equals("PM"),
                                    (int) selectHourSpinner.getSelectedItem(),
                                    Integer.parseInt(selectMinuteSpinner.getSelectedItem().toString())
                            ),
                            setAssignmentCourse.getText().toString()
                    );
                    taskViewModel.set(position, newAssignment);

                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        Button deleteTaskButton = dialogView.findViewById(R.id.delete_task);
        deleteTaskButton.setOnClickListener((v1) -> {
            taskViewModel.remove(position);
            dialog.dismiss();
        });
    }
}