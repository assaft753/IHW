package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;

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
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by assaftayouri on 10/03/2018.
 */

public class CalendarManager
{
    private final String PATTERN = "dd'/'MM'/'yyyy";
    private HashMap<String, List<CalendarHelper>> calendarDictionary;

    public CalendarManager()
    {
        this.calendarDictionary = new HashMap<String, List<CalendarHelper>>();
    }

    public List<CalendarHelper> getCalendarDictionary()
    {
        List<CalendarHelper> calendarHelpers = new ArrayList<>();
        for (Map.Entry<String, List<CalendarHelper>> pair : this.calendarDictionary.entrySet())
        {
            calendarHelpers.addAll(pair.getValue());
        }
        return calendarHelpers;
    }

    public void setCalendarDictionary(List<CalendarHelper> calendarDic)
    {
        for (CalendarHelper calendarHelper : calendarDic)
        {
            if (this.calendarDictionary.containsKey(MakeDateString(calendarHelper)))
            {
                this.calendarDictionary.get(MakeDateString(calendarHelper)).add(calendarHelper);
            }
            else
            {
                ArrayList<CalendarHelper> list = new ArrayList<>();
                list.add(calendarHelper);
                this.calendarDictionary.put(MakeDateString(calendarHelper), list);
            }
        }
    }

    private String MakeDateString(CalendarHelper calendarHelper)
    {
        return calendarHelper.getDay() + "/" + calendarHelper.getMonth() + "/" + calendarHelper.getYear();
    }

    private String MakeDateString(Calendar date)
    {
        return date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.MONTH) + "/" + date.get(Calendar.YEAR);
    }

    public void AddToCalendar(CalendarHelper calendarHelper, Context context)
    {
        List<CalendarHelper> arrayList;
        String date = MakeDateString(calendarHelper);
        if (this.calendarDictionary.containsKey(date))
        {
            arrayList = this.calendarDictionary.get(date);
            arrayList.add(calendarHelper);
            AddToNativeCalendar(calendarHelper, context);
            Collections.sort(arrayList);
        }
        else
        {
            arrayList = new ArrayList<CalendarHelper>();
            arrayList.add(calendarHelper);
            AddToNativeCalendar(calendarHelper, context);
            this.calendarDictionary.put(date, arrayList);
        }
    }

    public void RemoveFromCalendar(CalendarHelper calendarHelper, Context context)
    {
        String date = MakeDateString(calendarHelper);
        boolean b;
        if (this.calendarDictionary.containsKey(date))
        {
            for (int i = 0; i < calendarDictionary.get(date).size(); i++)
            {
                if (calendarDictionary.get(date).get(i).equals(calendarHelper))
                {
                    RemoveFromNativeCalendar(this.calendarDictionary.get(date).get(i), context);
                    this.calendarDictionary.get(date).remove(i);
                }
            }

        }
    }

    public ArrayList<Event> GetEvent()
    {
        ArrayList<Event> events = new ArrayList<Event>();
        Calendar cal = Calendar.getInstance();
        for (Map.Entry<String, List<CalendarHelper>> pair : this.calendarDictionary.entrySet())
        {
            for (CalendarHelper calendarHelper : pair.getValue())
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, calendarHelper.getDay());
                calendar.set(Calendar.MONTH, calendarHelper.getMonth());
                calendar.set(Calendar.YEAR, calendarHelper.getYear());
                System.out.println(calendar.getTime());
                Event ev = new Event(calendarHelper.getCourse().getCourseColor(), calendar.getTimeInMillis());
                events.add(ev);
            }
        }
        return events;
    }

    public void RemoveClass(Course course, Context context)
    {
        for (Map.Entry<String, List<CalendarHelper>> pair : this.calendarDictionary.entrySet())
        {
            List<CalendarHelper> arrayList = (pair.getValue());
            for (int i = 0; i < arrayList.size(); i++)
            {
                if (arrayList.get(i).getCourse().getCourseName().equals(course.getCourseName()))
                {
                    RemoveFromNativeCalendar(arrayList.get(i), context);
                    arrayList.remove(i);
                }
            }
        }
    }

    public void RemoveOnlyClass(Course course, Context context)
    {
        for (Map.Entry<String, List<CalendarHelper>> pair : this.calendarDictionary.entrySet())
        {
            List<CalendarHelper> arrayList = (pair.getValue());
            for (int i = 0; i < arrayList.size(); i++)
            {
                if (arrayList.get(i).getCourse().getCourseName().equals(course.getCourseName()) && arrayList.get(i).getTaskType() == TaskType.Class)
                {
                    RemoveFromNativeCalendar(arrayList.get(i), context);
                    arrayList.remove(i);
                }
            }
        }
    }

    public void AddClass(Course course, Context context)
    {
        Calendar baseCalendar = Calendar.getInstance();
        for (CourseDay day : course.getCourseDays())
        {
            baseCalendar.setTimeInMillis(course.GetStartDateAsCalendar().getTimeInMillis());
            baseCalendar.set(Calendar.DAY_OF_WEEK, day.getDayInWeek().GetDayNumber());
            if (baseCalendar.compareTo(course.GetStartDateAsCalendar()) < 0)
            {
                baseCalendar.set(Calendar.DAY_OF_MONTH, baseCalendar.get(Calendar.DAY_OF_MONTH) + 7);
            }
            while (baseCalendar.compareTo(course.GetEndDateAsCalendar()) <= 0 && baseCalendar.compareTo(course.GetStartDateAsCalendar()) >= 0)
            {
                CalendarHelper calendarHelper = new CalendarHelper(day.getStartHour(), day.getStartMinute(), baseCalendar.get(Calendar.DAY_OF_MONTH), baseCalendar.get(Calendar.MONTH), baseCalendar.get(Calendar.YEAR), day.getEndHour(), day.getEndMinute(), course.getCourseName(), course, TaskType.Class);
                String string = MakeDateString(calendarHelper);
                if (this.calendarDictionary.containsKey(string))
                {
                    AddToNativeCalendar(calendarHelper, context);
                    this.calendarDictionary.get(string).add(calendarHelper);
                }
                else
                {
                    ArrayList<CalendarHelper> arrayList = new ArrayList<CalendarHelper>();
                    arrayList.add(calendarHelper);
                    AddToNativeCalendar(calendarHelper, context);
                    this.calendarDictionary.put(string, arrayList);
                }
                baseCalendar.set(Calendar.DAY_OF_MONTH, baseCalendar.get(Calendar.DAY_OF_MONTH) + 7);
            }
        }
        for (Map.Entry<String, List<CalendarHelper>> pair : this.calendarDictionary.entrySet())
        {
            Collections.sort(pair.getValue());
        }

    }

    public List<CalendarHelper> GetListOFTasksInDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String datestr = MakeDateString(calendar);
        List<CalendarHelper> calendarHelpers = calendarDictionary.get(datestr);
        if (calendarHelpers == null)
        {
            calendarHelpers = new ArrayList<>();
        }
        return calendarHelpers;
    }

    private void AddToNativeCalendar(CalendarHelper calendarHelper, Context context)
    {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED)
        {
            long calID = 1;
            long startMillis = 0;
            long endMillis = 0;
            Calendar beginTime = Calendar.getInstance();
            beginTime.set(calendarHelper.getYear(), calendarHelper.getMonth(), calendarHelper.getDay(), calendarHelper.getHour(), calendarHelper.getMinute());
            startMillis = beginTime.getTimeInMillis();

            ContentResolver cr = context.getContentResolver();
            ContentValues values = new ContentValues();
            String title;
            String description;
            if (calendarHelper.getTaskType() == TaskType.Class)
            {
                title = calendarHelper.getCourse().getCourseName();
                description = "";
                Calendar endTime = Calendar.getInstance();
                endTime.set(calendarHelper.getYear(), calendarHelper.getMonth(), calendarHelper.getDay(), calendarHelper.getEndHour(), calendarHelper.getEndMinute());
                endMillis = endTime.getTimeInMillis();
            }
            else
            {
                title = calendarHelper.getTaskName();
                description = calendarHelper.getCourse().getCourseName();
                //Calendar endTime = Calendar.getInstance();
                //endTime.set(calendarHelper.getYear(), calendarHelper.getMonth(), calendarHelper.getDay(), calendarHelper.getEndHour(), calendarHelper.getEndMinute());
                endMillis = beginTime.getTimeInMillis();
            }
            values.put(CalendarContract.Events.DTSTART, startMillis);
            values.put(CalendarContract.Events.DTEND, endMillis);
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
            values.put(CalendarContract.Events.CALENDAR_ID, calID);
            //values.put(CalendarContract.Events.EVENT_COLOR, calendarHelper.getCourse().getCourseColor());
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            long eventID = Long.parseLong(uri.getLastPathSegment());
            calendarHelper.setEventId(eventID);
        }

    }

    private void RemoveFromNativeCalendar(CalendarHelper calendarHelper, Context context)
    {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED)
        {
            ContentResolver cr = context.getContentResolver();
            ContentValues values = new ContentValues();
            Uri deleteUri = null;
            deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, calendarHelper.getEventId());
            context.getContentResolver().delete(deleteUri, null, null);
        }
    }
}

