package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary;

import android.graphics.Color;
import android.provider.CalendarContract;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.TaskType;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CourseDay;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.TaskType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by assaftayouri on 10/03/2018.
 */

public class CalendarManager
{
    private final String PATTERN="dd'/'MM'/'yyyy";
    private HashMap<String, ArrayList<CalendarHelper>> calendarDictionary;

    public CalendarManager()
    {
        this.calendarDictionary = new HashMap<String, ArrayList<CalendarHelper>>();
    }

    private String MakeDateString(Calendar calendar)
    {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
    }

    public void AddToCalendar(CalendarHelper calendarHelper, Calendar calendar)
    {
        ArrayList<CalendarHelper> arrayList;
        String date = MakeDateString(calendar);
        if (this.calendarDictionary.containsKey(date))
        {
            arrayList = this.calendarDictionary.get(date);
            arrayList.add(calendarHelper);
            Collections.sort(arrayList);
        }
        else
        {
            arrayList = new ArrayList<>();
            arrayList.add(calendarHelper);
            this.calendarDictionary.put(date, arrayList);
        }
    }

    public void RemoveFromCalendar(CalendarHelper calendarHelper, Calendar calendar)
    {
        String date = MakeDateString(calendar);
        boolean b;
        if (this.calendarDictionary.containsKey(date))
        {
           System.out.println(this.calendarDictionary.get(date).remove(calendarHelper));
        }
    }

    public ArrayList<Event> GetEvent()
    {
        ArrayList<Event> events=new ArrayList<Event>();
        Calendar cal = Calendar.getInstance();
        for (Map.Entry<String, ArrayList<CalendarHelper>> pair : this.calendarDictionary.entrySet())
        {
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN, Locale.ENGLISH);
            try
            {
                cal.setTime(sdf.parse(pair.getKey()));
                for(CalendarHelper calendarHelper:pair.getValue())
                {
                    Calendar calendar=Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH));
                    calendar.set(Calendar.YEAR,cal.get(Calendar.YEAR));
                    Event ev = new Event(calendarHelper.getCourse().getCourseColor(),calendar.getTimeInMillis());
                    events.add(ev);
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return  events;
    }

    public void RemoveClass(Course course)
    {
        for (Map.Entry<String, ArrayList<CalendarHelper>> pair : this.calendarDictionary.entrySet())
        {
            ArrayList<CalendarHelper> arrayList = (pair.getValue());
            for (int i = 0; i < arrayList.size(); i++)
            {
                if (arrayList.get(i).getCourse().getCourseName().equals(course.getCourseName()))
                {
                    arrayList.remove(i);
                }
            }
        }
    }

    public void RemoveOnlyClass(Course course)
    {
        for (Map.Entry<String, ArrayList<CalendarHelper>> pair : this.calendarDictionary.entrySet())
        {
            ArrayList<CalendarHelper> arrayList = (pair.getValue());
            for (int i = 0; i < arrayList.size(); i++)
            {
                if (arrayList.get(i).getCourse().getCourseName().equals(course.getCourseName()) && arrayList.get(i).getTaskType() == TaskType.Class)
                {
                    arrayList.remove(i);
                }
            }
        }
    }

    public void AddClass(Course course)
    {
        Calendar baseCalendar = Calendar.getInstance();
        //baseCalendar.setTimeInMillis(course.getStartDate().getTimeInMillis());
        //baseCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //while (baseCalendar.compareTo(course.getEndDate()) <= 0)
        // {
        for (CourseDay day : course.getCourseDays())
        {
            baseCalendar.setTimeInMillis(course.getStartDate().getTimeInMillis());
            //baseCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            baseCalendar.set(Calendar.DAY_OF_WEEK, day.getDayInWeek().GetDayNumber());
            if (baseCalendar.compareTo(course.getStartDate()) < 0)
            {
                baseCalendar.set(Calendar.DAY_OF_MONTH, baseCalendar.get(Calendar.DAY_OF_MONTH) + 7);
            }
            while (baseCalendar.compareTo(course.getEndDate()) <= 0 && baseCalendar.compareTo(course.getStartDate()) >= 0)
            {
                CalendarHelper calendarHelper = new CalendarHelper(day.getStartHour(), day.getStartMinute(), course.getCourseName(), course, TaskType.Class);
                String string = MakeDateString(baseCalendar);
                if (this.calendarDictionary.containsKey(string))
                {
                    this.calendarDictionary.get(string).add(calendarHelper);
                }
                else
                {
                    ArrayList<CalendarHelper> arrayList = new ArrayList<CalendarHelper>();
                    arrayList.add(calendarHelper);
                    this.calendarDictionary.put(string, arrayList);
                }
                baseCalendar.set(Calendar.DAY_OF_MONTH, baseCalendar.get(Calendar.DAY_OF_MONTH) + 7);
            }
            //baseCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        // }
        for (Map.Entry<String, ArrayList<CalendarHelper>> pair : this.calendarDictionary.entrySet())
        {
            Collections.sort(pair.getValue());
        }

    }

    public ArrayList<CalendarHelper> GetListOFTasksInDate(Date date)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        String datestr=MakeDateString(calendar);
        ArrayList<CalendarHelper> calendarHelpers=calendarDictionary.get(datestr);
        if(calendarHelpers==null)
        {
            calendarHelpers=new ArrayList<>();
        }
        return calendarHelpers;
    }
}

