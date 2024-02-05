package com.example.classscheduler.ui.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.example.classscheduler.R;
import com.example.classscheduler.data.Assignment;
import com.example.classscheduler.data.DateAndTime;
import com.example.classscheduler.data.Exam;
import com.example.classscheduler.data.Task;

public class AddExamOnClickListener implements View.OnClickListener {

    private FragmentActivity requiredActivity;
    private Context context;
    private TaskViewModel taskViewModel;

    Integer[] baseDays = new Integer[31];
    Integer[] days = {0};

    ArrayAdapter<Integer> dayArrayAdapter;

    public AddExamOnClickListener(FragmentActivity requiredActivity, Context context, TaskViewModel taskViewModel) {
        System.out.println("ADD EXAM ON CLICK LISTENER");
        this.requiredActivity = requiredActivity;
        this.context = context;
        this.taskViewModel = taskViewModel;
    }


    @Override
    public void onClick(View v) {

        for (int i = 0; i < 31; i++) {
            baseDays[i] = i+1;
        }

        LayoutInflater inflater1 = requiredActivity.getLayoutInflater();
        View dialogView = inflater1.inflate(R.layout.dialog_add_exam, null);

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

        Spinner selectHourDurationSpinner = dialogView.findViewById(R.id.select_hour_duration);
        Integer[] durationHours = {0,1,2,3,4,5};
        ArrayAdapter<Integer> hourDurationArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, durationHours);
        selectHourDurationSpinner.setAdapter(hourDurationArrayAdapter);

        Spinner selectMinuteDurationSpinner = dialogView.findViewById(R.id.select_minute_duration);
        String[] durationMinutes = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
        ArrayAdapter<String> minuteDurationArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, durationMinutes);
        selectMinuteDurationSpinner.setAdapter(minuteDurationArrayAdapter);


        selectMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                days = new Integer[DateAndTime.getNumberOfDays(selectMonthSpinner.getSelectedItem().toString())];
                for (int i = 0; i < days.length; i++) days[i] = i+1;
                dayArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, days);
                selectDaySpinner.setAdapter(dayArrayAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setTitle("Add New Exam")
                .setPositiveButton("Add", (dialog, id) -> {
                    boolean abort = false;
                    EditText setTaskName = dialogView.findViewById(R.id.set_task_name);
                    EditText setAssignmentCourse = dialogView.findViewById(R.id.set_exam_course);

                    int endMinute = Integer.parseInt(selectMinuteSpinner.getSelectedItem().toString()) +
                            Integer.parseInt(selectMinuteDurationSpinner.getSelectedItem().toString());
                    int endHour = (int) selectHourSpinner.getSelectedItem() + (int) selectHourDurationSpinner.getSelectedItem();
                    boolean endAmOrPm = selectAmOrPmSpinner.getSelectedItem().toString().equals("PM");
                    System.out.println(endAmOrPm);


                    if (endMinute >= 60) {
                        endMinute %= 60;
                        endHour += 1;
                    }
                    if (endHour >= 12) {
                        System.out.println("endHour overflow");
                        System.out.println(endAmOrPm);
                        endHour %= 12;
                        if (endHour == 0) {
                            endHour = 12;
                        }
                        if (!endAmOrPm) {
                            endAmOrPm = true;
                        } else {
                            System.out.println("ERROR");
                            Toast.makeText(context, "Exam's can't span multiple days!", Toast.LENGTH_LONG).show();
                            abort = true;
                        }
                    }

                    if (!abort) {
                    Task newTask = new Exam(
                            setTaskName.getText().toString(),
                            new DateAndTime(
                                    (int) selectYearSpinner.getSelectedItem(),
                                    DateAndTime.getMonthNumberFromString(selectMonthSpinner.getSelectedItem().toString()),
                                    (int) selectDaySpinner.getSelectedItem(),
                                    selectAmOrPmSpinner.getSelectedItem().toString().equals("PM"),
                                    (int) selectHourSpinner.getSelectedItem(),
                                    Integer.parseInt(selectMinuteSpinner.getSelectedItem().toString())
                            ),
                            new DateAndTime(
                                    (int) selectYearSpinner.getSelectedItem(),
                                    DateAndTime.getMonthNumberFromString(selectMonthSpinner.getSelectedItem().toString()),
                                    (int) selectDaySpinner.getSelectedItem(),
                                    endAmOrPm,
                                    endHour,
                                    endMinute
                            ),
                            setAssignmentCourse.getText().toString()
                    );
                    taskViewModel.addTask(newTask);
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
