package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.ClassTimeListAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.OneSideItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.Day;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.DismissListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.SwipeHelperListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CourseDay;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by assaftayouri on 12/03/2018.
 */

public class AddClassSecondFragment extends Fragment implements SwipeHelperListener
{
    private DismissListener dialogDismissCallback;
    private String courseName;
    private Float coursePoints;
    private Long startDate;
    private Long endDate;
    private int color;
    private boolean isExists;
    private int index;
    private TextView startTimeText;
    private TextView endTimeText;
    private AppCompatButton startTimeBtn;
    private AppCompatButton endTimeBtn;
    private AppCompatButton addTimeBtn;
    private AppCompatButton doneBtn;
    private RecyclerView timeList;
    private SwitchCompat sundaySW;
    private SwitchCompat mondaySW;
    private SwitchCompat tuesdaySW;
    private SwitchCompat wednesdaySW;
    private SwitchCompat thursdaySW;
    private SwitchCompat fridaySW;
    private SwitchCompat saturdaySW;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private ClassTimeListAdapter classTimeListAdapter;
    private ArrayList<CourseDay> courseDays;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        courseName = bundle.getString("courseName");
        coursePoints = bundle.getFloat("coursePoints");
        startDate = bundle.getLong("startDate");
        endDate = bundle.getLong("endDate");
        color = bundle.getInt("color");
        isExists = bundle.getBoolean("isExists");
        index = bundle.getInt("index");

        courseDays = new ArrayList<CourseDay>();

        if (isExists)
        {
            courseDays.addAll(User.Student.GetCourseAtIndex(index).getCourseDays());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.second_add_class, container, false);
        startTimeText = view.findViewById(R.id.startTimeText);
        endTimeText = view.findViewById(R.id.endTimeText);
        startTimeBtn = view.findViewById(R.id.startTimeButton);
        endTimeBtn = view.findViewById(R.id.endTimeButton);
        addTimeBtn = view.findViewById(R.id.addTimeButton);
        doneBtn = view.findViewById(R.id.doneButton);
        timeList = view.findViewById(R.id.timeRecyclerView);
        sundaySW = (SwitchCompat) view.findViewById(R.id.sundaySW);
        mondaySW = view.findViewById(R.id.mondaySW);
        tuesdaySW = view.findViewById(R.id.tuesdaySW);
        wednesdaySW = view.findViewById(R.id.wednesdaySW);
        thursdaySW = view.findViewById(R.id.thursdaySW);
        fridaySW = view.findViewById(R.id.fridaySW);
        saturdaySW = view.findViewById(R.id.saturdaySW);

        doneBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (courseDays.size() == 0)
                {
                    Toast toast = Toast.makeText(getContext(), "No Times", Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    Calendar calendarstart = Calendar.getInstance();
                    calendarstart.setTimeInMillis(startDate);
                    Calendar calendarend = Calendar.getInstance();
                    calendarend.setTimeInMillis(endDate);

                    if (isExists)
                    {
                        Course course = User.Student.GetCourseAtIndex(index);
                        course.setCourseName(courseName);
                        course.setCourseColor(color);

                        course.SetStartDateAsCalendar(calendarstart);

                        course.SetEndDateAsCalendar(calendarend);

                        course.setPoints(coursePoints);
                        course.setCourseDays(courseDays);
                        User.Student.UpdateCourse(index, getContext());
                    }
                    else
                    {
                        User.Student.AddClass(new Course(courseName, coursePoints, calendarstart, calendarend, color, courseDays), getContext());
                    }
                    dialogDismissCallback.Dismiss();
                }
            }
        });

        addTimeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AddClassTime();
            }
        });

        startTimeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1)
                    {
                        startHour = i;
                        startMinute = i1;
                        SetTimeTextView(startTimeText, startHour, startMinute);

                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        endTimeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1)
                    {
                        endHour = i;
                        endMinute = i1;
                        SetTimeTextView(endTimeText, endHour, endMinute);


                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });


        classTimeListAdapter = new ClassTimeListAdapter(getContext(), courseDays);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        timeList.setLayoutManager(mLayoutManager);
        timeList.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new OneSideItemHelper<ClassTimeListAdapter.ClassTimeViewHolder>(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(timeList);
        timeList.setAdapter(classTimeListAdapter);
        classTimeListAdapter.notifyDataSetChanged();

        return view;
    }

    private void SetTimeTextView(TextView startTimeText, int hour, int minute)
    {
        String ampm = "PM";
        String minutestr = String.valueOf(minute);
        if (minute < 10)
        {
            minutestr = "0" + minutestr;
        }
        if (hour < 12)
        {
            ampm = "AM";
        }

        startTimeText.setText(hour + ":" + minutestr + " " + ampm);
    }

    private void AddClassTime()
    {
        if (CheckValidation())
        {
            if (sundaySW.isChecked())
            {
                CourseDay courseDay = new CourseDay(Day.SUNDAY, startHour, startMinute, endHour, endMinute);
                this.classTimeListAdapter.addItem(courseDay);
            }

            if (mondaySW.isChecked())
            {
                CourseDay courseDay = new CourseDay(Day.MONDAY, startHour, startMinute, endHour, endMinute);
                this.classTimeListAdapter.addItem(courseDay);
            }

            if (tuesdaySW.isChecked())
            {
                CourseDay courseDay = new CourseDay(Day.TUESDAY, startHour, startMinute, endHour, endMinute);
                this.classTimeListAdapter.addItem(courseDay);
            }

            if (wednesdaySW.isChecked())
            {
                CourseDay courseDay = new CourseDay(Day.WEDNESDAY, startHour, startMinute, endHour, endMinute);
                this.classTimeListAdapter.addItem(courseDay);
            }

            if (thursdaySW.isChecked())
            {
                CourseDay courseDay = new CourseDay(Day.THURSDAY, startHour, startMinute, endHour, endMinute);
                this.classTimeListAdapter.addItem(courseDay);
            }

            if (fridaySW.isChecked())
            {
                CourseDay courseDay = new CourseDay(Day.FRIDAY, startHour, startMinute, endHour, endMinute);
                this.classTimeListAdapter.addItem(courseDay);
            }

            if (saturdaySW.isChecked())
            {
                CourseDay courseDay = new CourseDay(Day.SATURDAY, startHour, startMinute, endHour, endMinute);
                this.classTimeListAdapter.addItem(courseDay);
            }
        }

    }

    private boolean CheckValidation()
    {
        if (startTimeText.getText().toString().equals("") || endTimeText.getText().toString().equals(""))
        {
            return false;
        }
        if (endHour < startHour)
        {
            return false;
        }
        if (endHour == startHour && startMinute > endMinute)
        {
            return false;
        }
        return true;
    }


    public void setDialogDismissCallback(DismissListener dialogDismissCallback)
    {
        this.dialogDismissCallback = dialogDismissCallback;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        this.classTimeListAdapter.removeItem(position);
    }
}
