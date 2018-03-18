package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs.AddDialog;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs.ItemDialog;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Exam;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.Day;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.Term;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.AddExamDialogListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.DismissListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by assaftayouri on 08/03/2018.
 */

public class
AddExamFragment extends android.support.v4.app.Fragment
{
    private final String PATTERN = "dd/MM/yy";
    private String opt;
    private Exam exam;
    private TextView toolbarTitleText;
    private TextView pickClassText;
    private TextView classText;
    private TextView pickTermText;
    private TextView termText;
    private TextView toDateText;
    private TextView toTimeText;
    private TextView reminderText;
    private Button toDateBtn;
    private Button toTimeBtn;
    private Button reminderBtn;
    private AppCompatButton doneBtn;
    private DatePickerDialog.OnDateSetListener toDateCallback;
    private Calendar toDateCalendar;
    private String[] NotifyStrings;
    private int notify;
    private int classIndex;
    private Course currentCourse;
    private Term termExam;
    private Exam currentExam;
    private Exam oldExam;
    private int examIndex;
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
            currentExam = null;
        }
        else
        {
            examIndex = Integer.parseInt(opt);
            System.out.println(examIndex);
            currentExam = User.Student.GetUngradedExam(examIndex);
            oldExam = new Exam(currentExam);
        }

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
        View view = inflater.inflate(R.layout.add_exam, container, false);
        toolbarTitleText = view.findViewById(R.id.toolbarTitleText);
        pickClassText = view.findViewById(R.id.pickClass);
        pickTermText = view.findViewById(R.id.pickTerm);
        toDateBtn = view.findViewById(R.id.toDateButton);
        toTimeBtn = view.findViewById(R.id.toTimeButton);
        reminderBtn = view.findViewById(R.id.reminderButton);
        doneBtn = view.findViewById(R.id.doneButton);
        toDateText = view.findViewById(R.id.toDateText);
        toTimeText = view.findViewById(R.id.toTimeText);
        reminderText = view.findViewById(R.id.reminderText);
        classText = view.findViewById(R.id.classText);
        termText = view.findViewById(R.id.termText);

        doneBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (ValidateInput())
                {
                    if (currentExam != null)
                    {
                        currentExam.setExamDate(toDateCalendar.getTimeInMillis());
                        currentExam.setNotify(notify);
                        currentExam.setTerm(termExam);
                        User.Student.UpdatedUngradedExam(examIndex, getContext(), oldExam);
                    }
                    else
                    {
                        Exam exam = new Exam(currentCourse, termExam, toDateCalendar, notify);
                        User.Student.AddUngradedExam(exam, getContext());
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

        pickTermText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String[] strings = Term.GetOpt();
                AddExamDialogListener OKbtn = new AddExamDialogListener()
                {
                    @Override
                    public void OkValue(int value)
                    {
                        termExam = Term.values()[value];
                        termText.setText(termExam.toString());
                    }
                };

                ItemDialog itemDialog = new ItemDialog();
                itemDialog.setStrs(strings, OKbtn);
                itemDialog.show(getFragmentManager(), "Picker");
            }
        });

        if (currentExam == null)
        {
            toolbarTitleText.setText(getResources().getText(R.string.addExam));
            pickClassText.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String[] strings = User.Student.GetCourseNames();
                    if (strings.length == 0)
                    {
                        Toast.makeText(getContext(), getResources().getText(R.string.noclassesfound), Toast.LENGTH_SHORT).show();
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
            toolbarTitleText.setText(getResources().getText(R.string.editExam));
            PutData();
        }
        pickTermText.setPaintFlags(pickTermText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        pickClassText.setPaintFlags(pickClassText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        return view;
    }

    private boolean ValidateInput()
    {
        try
        {
            if (classText.getText().equals("") || termText.getText().equals("")
                    || toDateText.getText().equals("") || toTimeText.getText().equals("") || reminderText.getText().equals(""))
            {
                throw new Exception(getResources().getString(R.string.missingInfo));
            }

            if ((oldExam != null && oldExam.getTerm() != termExam) || (oldExam == null))
            {
                if (! currentCourse.CheckExamValidate(termExam))
                {
                    throw new Exception(getResources().getString(R.string.termtaken));
                }
            }

            int index = - 1;
            if (currentExam != null)
            {
                index = currentExam.getTerm().ordinal();

            }

            if (! currentCourse.CheckExamDate(toDateCalendar, index))
            {
                throw new Exception(getResources().getString(R.string.exaontime));
            }
            return true;
        } catch (Exception ex)
        {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void PutData()
    {
        currentCourse = currentExam.getCourse();

        termText.setText(currentExam.getTerm().toString());
        termExam = currentExam.getTerm();

        classText.setText(currentExam.getCourse().getCourseName());

        toDateCalendar.setTimeInMillis(currentExam.GetExamDateAsCalendar().getTimeInMillis());
        SetTimeTextView(toTimeText, toDateCalendar.get(Calendar.HOUR_OF_DAY), toDateCalendar.get(Calendar.MINUTE));
        SetDateText(toDateText, toDateCalendar);

        notify = currentExam.getNotify();
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
