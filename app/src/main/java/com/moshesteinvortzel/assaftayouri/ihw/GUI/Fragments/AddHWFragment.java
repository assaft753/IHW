package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs.ItemDialog;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Exam;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.HomeWork;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.Priority;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.Term;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.AddExamDialogListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.DismissListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by assaftayouri on 08/03/2018.
 */

public class AddHWFragment extends android.support.v4.app.Fragment
{
    private final String PATTERN = "dd/MM/yy";
    private TextView toolbarTitleText;
    private TextView pickClassText;
    private TextView classText;
    private TextView pickPriorityText;
    private TextView priorityText;
    private TextView toDateText;
    private TextView toTimeText;
    private TextView reminderText;
    private EditText hwName;
    private Button toDateBtn;
    private Button toTimeBtn;
    private Button reminderBtn;
    private AppCompatButton doneBtn;

    private Calendar toDateCalendar;
    private String[] NotifyStrings;
    private String opt;
    private Priority priorityHW;
    private int notify;
    private HomeWork currentHW;
    private int hwIndex;
    private Course currentCourse;
    private HomeWork oldHW;
    private DatePickerDialog.OnDateSetListener toDateCallback;
    private DismissListener listener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        toDateCalendar = Calendar.getInstance();
        toDateCalendar.set(Calendar.SECOND, 0);
        toDateCalendar.set(Calendar.MILLISECOND, 0);
        NotifyStrings = User.Student.GetNotifyOpt();
        opt = getArguments().getString("opt");
        if (opt.equals("new"))
        {
            currentHW = null;
            hwIndex = 0;
        }
        else
        {
            hwIndex = Integer.parseInt(opt);
            System.out.println(hwIndex);
            if (hwIndex > 0)
            {
                currentHW = User.Student.GetUncompletedHWByIndex(hwIndex - 1);
            }
            else
            {
                currentHW = User.Student.GetCompletedHWByIndex((hwIndex * - 1) - 1);
            }
            oldHW = new HomeWork(currentHW);
        }
        /*Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12);
        currentExam = new Exam(new Course("Assaf", (float) 2.4, Calendar.getInstance(), Calendar.getInstance(), 90, null), Term.B, calendar, 7);*/


        this.toDateCallback = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
            {
                toDateCalendar.set(Calendar.YEAR, i);
                toDateCalendar.set(Calendar.MONTH, i1);
                toDateCalendar.set(Calendar.DAY_OF_MONTH, i2);
                SetDateText(toDateText, toDateCalendar);
            }
        };

    }

    private void SetDateText(TextView toDateText, Calendar toDateCalendar)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN, Locale.getDefault());
        toDateText.setText(simpleDateFormat.format(toDateCalendar.getTime()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.add_hw, container, false);
        toolbarTitleText = view.findViewById(R.id.toolbarTitleText);
        pickClassText = view.findViewById(R.id.pickClass);
        pickPriorityText = view.findViewById(R.id.pickPriority);
        toDateBtn = view.findViewById(R.id.toDateButton);
        toTimeBtn = view.findViewById(R.id.toTimeButton);
        reminderBtn = view.findViewById(R.id.reminderButton);
        doneBtn = view.findViewById(R.id.doneButton);
        toDateText = view.findViewById(R.id.toDateText);
        toTimeText = view.findViewById(R.id.toTimeText);
        reminderText = view.findViewById(R.id.reminderText);
        classText = view.findViewById(R.id.classText);
        priorityText = view.findViewById(R.id.priorityText);
        hwName = view.findViewById(R.id.hwName);


        doneBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (ValidateInput())
                {
                    if (currentHW != null)
                    {
                        currentHW.setToDate(toDateCalendar);
                        currentHW.setNotify(notify);
                        currentHW.setPriority(priorityHW);
                        if (hwIndex < 0)
                        {
                            User.Student.UpdatedCompletedHW((hwIndex * - 1) - 1, getContext(), oldHW);
                        }
                        else
                        {
                            User.Student.UpdatedUncompletedHW(hwIndex - 1, getContext(), oldHW);
                        }

                    }
                    else
                    {
                        HomeWork homeWork = new HomeWork(currentCourse, hwName.getText().toString().trim(), priorityHW, toDateCalendar, notify);
                        User.Student.AddUncompletedHW(homeWork, getContext());
                    }
                    listener.Dismiss();
                }
            }
        });

        toDateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(getContext(), R.style.MyDialogTheme, toDateCallback, toDateCalendar.get(Calendar.YEAR), toDateCalendar.get(Calendar.MONTH), toDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        toTimeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1)
                    {
                        SetTimeTextView(toTimeText, i, i1);
                        toDateCalendar.set(Calendar.HOUR_OF_DAY, i);
                        toDateCalendar.set(Calendar.MINUTE, i1);
                    }
                }, toDateCalendar.get(Calendar.HOUR_OF_DAY), toDateCalendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        reminderBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                AddExamDialogListener OKbtn = new AddExamDialogListener()
                {
                    @Override
                    public void OkValue(int value)
                    {
                        notify = value + 1;
                        reminderText.setText(NotifyStrings[notify - 1]);
                    }
                };

                ItemDialog itemDialog = new ItemDialog();
                itemDialog.setStrs(NotifyStrings, OKbtn);
                itemDialog.show(getFragmentManager(), "Picker");
            }
        });

        pickPriorityText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String[] strings = Priority.GetOpt();
                AddExamDialogListener OKbtn = new AddExamDialogListener()
                {
                    @Override
                    public void OkValue(int value)
                    {
                        priorityHW = Priority.values()[value];
                        priorityText.setText(priorityHW.toString());
                    }
                };

                ItemDialog itemDialog = new ItemDialog();
                itemDialog.setStrs(strings, OKbtn);
                itemDialog.show(getFragmentManager(), "Picker");
            }
        });

        if (currentHW == null)
        {
            toolbarTitleText.setText("Add HomeWork");
            pickClassText.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String[] strings = User.Student.GetCourseNames();
                    if (strings.length == 0)
                    {
                        Toast.makeText(getContext(), "No Classes Found", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        AddExamDialogListener OKbtn = new AddExamDialogListener()
                        {
                            @Override
                            public void OkValue(int value)
                            {
                                currentCourse = User.Student.GetCourseAtIndex(value);
                                classText.setText(currentCourse.getCourseName());
                            }
                        };

                        ItemDialog itemDialog = new ItemDialog();
                        itemDialog.setStrs(strings, OKbtn);
                        itemDialog.show(getFragmentManager(), "Picker");
                    }
                }
            });
        }

        else
        {
            toolbarTitleText.setText("Edit HomeWork");
            PutData();
        }
        pickPriorityText.setPaintFlags(pickPriorityText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        pickClassText.setPaintFlags(pickClassText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        return view;
    }


    private boolean ValidateInput()
    {
        try
        {
            if (classText.getText().equals("") || priorityText.getText().equals("")
                    || toDateText.getText().equals("") || toTimeText.getText().equals("") || reminderText.getText().equals("") || hwName.getText().toString().trim().equals(""))
            {
                throw new Exception("Missing Info");
            }
            if (currentHW != null)
            {
                if (! currentCourse.CheckExistsHomeWork(hwName.getText().toString().trim()))
                {
                    throw new Exception("Duplicate Task Name");
                }
            }
            else
            {
                if (! currentCourse.CheckNewHomeWork(hwName.getText().toString().trim()))
                {
                    throw new Exception("Duplicate Task Name");
                }
            }
            System.out.println("true");
            return true;
        } catch (Exception ex)
        {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("false");
            return false;
        }
    }

    private void PutData()
    {
        currentCourse = currentHW.getCourse();

        priorityText.setText(currentHW.getPriority().toString());
        priorityHW = currentHW.getPriority();

        classText.setText(currentHW.getCourse().getCourseName());

        toDateCalendar.setTimeInMillis(currentHW.getToDate().getTimeInMillis());
        SetTimeTextView(toTimeText, toDateCalendar.get(Calendar.HOUR_OF_DAY), toDateCalendar.get(Calendar.MINUTE));
        SetDateText(toDateText, toDateCalendar);

        notify = currentHW.getNotify();
        reminderText.setText(NotifyStrings[notify - 1]);
    }

    private void SetTimeTextView(TextView toTimeText, int hour, int minute)
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

        toTimeText.setText(hour + ":" + minutestr + " " + ampm);
    }

    public void setDialogDismissCallback(DismissListener listener)
    {
        this.listener = listener;
    }

}
