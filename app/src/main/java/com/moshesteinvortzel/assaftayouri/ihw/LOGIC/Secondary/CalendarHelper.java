package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.TaskType;

import java.util.Calendar;

/**
 * Created by assaftayouri on 10/03/2018.
 */

public class CalendarHelper implements Comparable<CalendarHelper>
{
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;
    private int endHour;
    private int endMinute;
    private String TaskName;
    private Course course;
    private TaskType taskType;
    private long eventId;

    public CalendarHelper(int hour, int minute, int day, int month, int year, String taskName, Course course, TaskType taskType)
    {
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        TaskName = taskName;
        this.course = course;
        this.taskType = taskType;
        this.eventId = 0;
        this.endMinute = - 1;
        this.endHour = - 1;
    }

    public CalendarHelper(int hour, int minute, int day, int month, int year, int endHour, int endMinute, String taskName, Course course, TaskType taskType)
    {
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.endHour = endHour;
        this.endMinute = endMinute;
        TaskName = taskName;
        this.course = course;
        this.taskType = taskType;
        this.eventId = 0;
    }

    public int getEndHour()
    {
        return endHour;
    }

    public int getEndMinute()
    {
        return endMinute;
    }

    public int getDay()
    {
        return day;
    }

    public int getMonth()
    {
        return month;
    }

    public int getYear()
    {
        return year;
    }

    public long getEventId()
    {
        return eventId;
    }

    public void setEventId(long eventId)
    {
        this.eventId = eventId;
    }

    public String GenerateTimeStr()
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

        return hour + ":" + minutestr + " " + ampm;
    }

    public TaskType getTaskType()
    {
        return taskType;
    }

    public int getHour()
    {
        return hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public String getTaskName()
    {
        return TaskName;
    }

    @Override
    public int compareTo(@NonNull CalendarHelper calendarHelper)
    {
        int result = this.getHour() - calendarHelper.getHour();
        if (result != 0)
        {
            return result;
        }
        return this.getMinute() - calendarHelper.getMinute();
    }

    public Course getCourse()
    {
        return course;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj != this)
        {
            CalendarHelper tempCalendarHelper = (CalendarHelper) obj;
            if (this.TaskName.equals(tempCalendarHelper.TaskName) && this.course.getCourseName().equals(tempCalendarHelper.course.getCourseName()) && this.taskType == tempCalendarHelper.taskType)
            {
                return true;
            }
            return false;
        }
        else
        {
            return true;
        }
    }
}
