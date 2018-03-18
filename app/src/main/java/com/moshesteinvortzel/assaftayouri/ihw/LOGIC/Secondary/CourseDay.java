package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.Day;

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
    private Day dayInWeek;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    public CourseDay(Day dayInWeek, int startHour, int startMinute, int endHour, int endMinute)
    {
        this.dayInWeek = dayInWeek;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public CourseDay()
    {
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        CourseDay courseDay = (CourseDay) obj;
        if (courseDay.dayInWeek == this.dayInWeek
                && courseDay.startHour == this.startHour
                && courseDay.endHour == this.endHour)
        {
            return true;
        }
        return false;
    }

    public Day getDayInWeek()
    {
        return dayInWeek;
    }

    public void setDayInWeek(Day dayInWeek)
    {
        this.dayInWeek = dayInWeek;
    }

    public int getStartHour()
    {
        return startHour;
    }

    public void setStartHour(int startHour)
    {
        this.startHour = startHour;
    }

    public int getStartMinute()
    {
        return startMinute;
    }

    public void setStartMinute(int startMinute)
    {
        this.startMinute = startMinute;
    }

    public int getEndHour()
    {
        return endHour;
    }

    public void setEndHour(int endHour)
    {
        this.endHour = endHour;
    }

    public int getEndMinute()
    {
        return endMinute;
    }

    public void setEndMinute(int endMinute)
    {
        this.endMinute = endMinute;
    }
}
