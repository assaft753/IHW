package com.moshesteinvortzel.assaftayouri.ihw;

import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.thebluealliance.spectrum.SpectrumDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewCourseActivity extends AppCompatActivity
{
private Button pickColor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        final Calendar myCalendar = Calendar.getInstance();
        final TextView date1 = findViewById(R.id.startDateText);
        final TextView date2 = findViewById(R.id.endDateText);
        final Button startDate = findViewById(R.id.startDateButton);
        final Button endDate = findViewById(R.id.endDateButton);
        pickColor=findViewById(R.id.colorChooseButton);
        pickColor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new SpectrumDialog.Builder(getApplicationContext())
                        .setColors(R.array.demo_colors)
                        .setSelectedColorRes(R.color.md_blue_500)
                        .setDismissOnColorSelected(false)
                        .setOutlineWidth(3)
                        .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                            @Override public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                                if (positiveResult)
                                {
                                    Toast.makeText(getApplicationContext(), "Color selected: #" + Integer.toHexString(color).toUpperCase(), Toast.LENGTH_SHORT).show();
                                    Drawable gradientDrawable=pickColor.getBackground();
                                    gradientDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Dialog cancelled", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).build().show(getSupportFragmentManager(),"blabla");


            }
        });
        date1.setPaintFlags(date1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        date2.setPaintFlags(date2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        startDate.setBackgroundResource(R.drawable.calendar);
        endDate.setBackgroundResource(R.drawable.calendar);

        final DatePickerDialog.OnDateSetListener dateStart = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
            {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.GERMANY);
                date1.setText(simpleDateFormat.format(myCalendar.getTime()));
            }
        };

        startDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(AddNewCourseActivity.this,  R.style.MyDialogTheme, dateStart, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener dateEnd= new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
            {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.GERMANY);
                date2.setText(simpleDateFormat.format(myCalendar.getTime()));
            }
        };

        endDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(AddNewCourseActivity.this,  R.style.MyDialogTheme, dateEnd, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void AddClass(View view)
    {
    }
}
