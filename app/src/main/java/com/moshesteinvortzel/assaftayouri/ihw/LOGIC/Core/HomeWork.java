package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core;

import android.support.annotation.NonNull;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.Priority;

import java.util.Calendar;

/**
 * Created by assaftayouri on 06/03/2018.
 */

public class HomeWork implements Comparable<HomeWork>
{
    private Course course;
    private String TaskName;
    private Priority priority;
    private Calendar toDate;
    //private boolean finished;
    private int notify;
    private int pushId;

    public HomeWork(Course course, String taskName, Priority priority, Calendar toDate, int notify)
    {
        this.course = course;
        TaskName = taskName;
        this.priority = priority;
        this.toDate = toDate;
        this.notify = notify;
        //this.finished = false;
    }

    public HomeWork(String taskName, Calendar toDate)
    {
        TaskName = taskName;
        this.toDate = toDate;
    }


    public Course getCourse()
    {
        return course;
    }

    public int getNotify()
    {
        return notify;
    }

    public void setNotify(int notify)
    {
        this.notify = notify;
    }

    public Calendar getToDate()
    {
        return toDate;
    }

    public String getTaskName()
    {
        return TaskName;
    }

    public void setTaskName(String taskName)
    {
        TaskName = taskName;
    }

    public int getPushId()
    {
        return pushId;
    }

    public void setPushId(int pushId)
    {
        this.pushId = pushId;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj != this)
        {
            HomeWork com = (HomeWork) obj;
            if (this.TaskName.equals(com.TaskName))
            {
                if (this.toDate.equals(com.toDate))
                {
                    return true;
                }
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public int compareTo(@NonNull HomeWork homeWork)
    {
        return this.toDate.compareTo(homeWork.toDate);
    }
}