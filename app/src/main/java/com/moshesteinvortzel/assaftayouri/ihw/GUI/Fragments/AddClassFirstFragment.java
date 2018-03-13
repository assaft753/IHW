package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.FirstAddClassListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by assaftayouri on 12/03/2018.
 */

public class AddClassFirstFragment extends android.support.v4.app.Fragment
{
    private final String ERROR = "Invalid Details";
    private String opt;
    private EditText courseName;
    private EditText coursePoints;
    private TextView startDate;
    private TextView endDate;
    private Button startDateBtn;
    private Button endDateBtn;
    private AppCompatButton chooseColor;
    private AppCompatButton nextBtn;
    private Calendar startDateCalendar;
    private Calendar endDateCalendar;
    private DatePickerDialog.OnDateSetListener startDateCallback;
    private DatePickerDialog.OnDateSetListener endDateCallback;
    private int color;
    private Course course;
    private int indexOf;
    private FirstAddClassListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("create");
        this.startDateCalendar = Calendar.getInstance();
        this.endDateCalendar = Calendar.getInstance();
        this.opt = getArguments().getString("opt");
        if (! this.opt.equals("new"))
        {
            indexOf = Integer.valueOf(this.opt);
            course=User.Student.GetCourseAtIndex(indexOf);
        }
        else
        {
            course = null;
        }
        /*Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, 18);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3);*/

        this.startDateCallback = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
            {
                startDateCalendar.set(Calendar.YEAR, i);
                startDateCalendar.set(Calendar.MONTH, i1);
                startDateCalendar.set(Calendar.DAY_OF_MONTH, i2);
                AddClassFirstFragment.this.SetDateText(AddClassFirstFragment.this.startDate, startDateCalendar);
            }
        };

        this.endDateCallback = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
            {
                endDateCalendar.set(Calendar.YEAR, i);
                endDateCalendar.set(Calendar.MONTH, i1);
                endDateCalendar.set(Calendar.DAY_OF_MONTH, i2);
                AddClassFirstFragment.this.SetDateText(AddClassFirstFragment.this.endDate, endDateCalendar);
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.first_add_class, container, false);
        courseName = view.findViewById(R.id.className);
        coursePoints = view.findViewById(R.id.classPoints);
        startDate = view.findViewById(R.id.startDateText);
        endDate = view.findViewById(R.id.endDateText);
        startDateBtn = view.findViewById(R.id.startDateButton);
        endDateBtn = view.findViewById(R.id.endDateButton);
        chooseColor = view.findViewById(R.id.colorChooseButton);
        nextBtn = view.findViewById(R.id.NextButton);
        startDateBtn.setBackgroundResource(R.drawable.calendar);
        endDateBtn.setBackgroundResource(R.drawable.calendar);

        this.startDateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(getContext(), R.style.MyDialogTheme, AddClassFirstFragment.this.startDateCallback, AddClassFirstFragment.this.startDateCalendar.get(Calendar.YEAR), AddClassFirstFragment.this.startDateCalendar.get(Calendar.MONTH), AddClassFirstFragment.this.startDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        this.endDateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(getContext(), R.style.MyDialogTheme, AddClassFirstFragment.this.endDateCallback, AddClassFirstFragment.this.endDateCalendar.get(Calendar.YEAR), AddClassFirstFragment.this.endDateCalendar.get(Calendar.MONTH), AddClassFirstFragment.this.endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        this.chooseColor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new SpectrumDialog.Builder(getContext())
                        .setColors(R.array.demo_colors)
                        .setSelectedColorRes(R.color.md_blue_500)
                        .setDismissOnColorSelected(false)
                        .setOutlineWidth(3)
                        .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener()
                        {
                            @Override
                            public void onColorSelected(boolean positiveResult, @ColorInt int color)
                            {
                                if (positiveResult)
                                {
                                    SetColorOfPickColor(color);
                                }
                            }
                        }).build().show(getFragmentManager(), "Color Picker");
            }
        });

        this.nextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (! CheckValidation())
                {
                    Toast toast = Toast.makeText(getContext(), ERROR, Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    boolean isExists = false;
                    int index = - 1;
                    if (course != null)
                    {
                        isExists = true;
                        index = indexOf;
                    }
                    AddClassFirstFragment.this.listener.MoveToSecondAddClass(courseName.getText().toString().trim(),
                            Float.valueOf(coursePoints.getText().toString()), startDateCalendar.getTimeInMillis(),
                            endDateCalendar.getTimeInMillis(), color, isExists, index);
                }
            }
        });

        if (this.course != null)
        {
            PutData(this.course);
        }
        return view;
    }

    public void setListener(FirstAddClassListener listener)
    {
        this.listener = listener;
    }

    private boolean CheckValidation()
    {
        if (this.color == 0 || this.coursePoints.getText().toString().trim().length() == 0 ||
                courseName.getText().toString().trim().length() == 0 ||
                startDate.getText().toString().length() == 0 ||
                endDate.getText().toString().length() == 0)
        {
            return false;
        }

        long diff = endDateCalendar.getTime().getTime() - startDateCalendar.getTime().getTime();
        long diffInDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if (this.endDateCalendar.before(this.startDateCalendar) || diffInDays < 7)
        {
            return false;
        }

        if (this.course != null)
        {
            return User.Student.CheckValidationOfClassWithIndex(courseName.getText().toString().trim(), indexOf);
        }
        else
        {
            return User.Student.CheckValidationOfClass(courseName.getText().toString().trim());
        }


    }

    private void PutData(Course course)
    {
        this.courseName.setText(course.getCourseName());

        this.coursePoints.setText(String.valueOf(course.getPoints()));

        this.startDateCalendar.setTimeInMillis(course.getStartDate().getTimeInMillis());
        AddClassFirstFragment.this.SetDateText(AddClassFirstFragment.this.startDate, startDateCalendar);

        this.endDateCalendar.setTimeInMillis(course.getEndDate().getTimeInMillis());
        AddClassFirstFragment.this.SetDateText(AddClassFirstFragment.this.endDate, endDateCalendar);

        SetColorOfPickColor(course.getCourseColor());

    }

    private void SetDateText(TextView textView, Calendar calendar)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        textView.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void SetColorOfPickColor(int color)
    {
        AddClassFirstFragment.this.color = color;
        Drawable gradientDrawable = AddClassFirstFragment.this.chooseColor.getBackground();
        gradientDrawable.setColorFilter(AddClassFirstFragment.this.color, PorterDuff.Mode.SRC_ATOP);
    }
}
