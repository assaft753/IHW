package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by assaftayouri on 09/03/2018.
 */

public class CourseDay
{
    private int dayInWeek;
    private int hour;
    private int minute;

    public CourseDay(int day, int hour, int minute)
    {
        this.dayInWeek = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int getDayInWeek()
    {
        return dayInWeek;
    }

    public int getHour()
    {
        return hour;
    }

    public int getMinute()
    {
        return minute;
    }
}
