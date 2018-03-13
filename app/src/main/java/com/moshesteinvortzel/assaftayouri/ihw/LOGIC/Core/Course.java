package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core;

import android.graphics.Color;
import android.provider.CalendarContract;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.Term;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CourseDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by assaftayouri on 09/03/2018.
 */

public class Course
{
    private String courseName;
    private float points;
    private Calendar startDate;
    private Calendar endDate;
    private int courseColor;
    private ArrayList<CourseDay> courseDays;
    private ArrayList<HomeWork> homeWorks;
    private Exam[] exams;

    public Course(String courseName, float points, Calendar startDate, Calendar endDate, int courseColor, ArrayList<CourseDay> courseDays)
    {
        this.courseName = courseName;
        this.points = points;
        setStartDate(startDate);
        setEndDate(endDate);
        this.courseColor = courseColor;
        this.courseDays = courseDays;
        this.homeWorks = new ArrayList<HomeWork>();
        this.exams = new Exam[3];
    }

    public boolean CheckExamValidate(Term term)
    {
        if (exams[term.ordinal()] != null)
        {
            return false;
        }
        return true;

    }

    public boolean CheckExamDate(Calendar calendar)
    {
        for (int i = 0; i < exams.length; i++)
        {
            if (exams[i] != null)
            {
                if (exams[i].getExamDate().compareTo(calendar) == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }


    public String getCourseName()
    {
        return courseName;
    }

    public float getPoints()
    {
        return points;
    }

    public Calendar getStartDate()
    {
        return startDate;
    }

    public Calendar getEndDate()
    {
        return endDate;
    }

    public int getCourseColor()
    {
        return courseColor;
    }

    public ArrayList<CourseDay> getCourseDays()
    {
        return courseDays;
    }

    public boolean IsHomeWorkExists(String taskName, Calendar toDate)
    {
        return this.homeWorks.contains(new HomeWork(taskName, toDate));

    }

    public void AddToHomeWork(HomeWork homeWork)
    {
        this.homeWorks.add(homeWork);
    }

    public void RemoveFromHomeWork(HomeWork homeWork)
    {
        this.homeWorks.remove(homeWork);
    }

    public Boolean AddToExam(Exam exam, int term)
    {
        if (exams[term] == null)
        {
            exams[term] = exam;
            return true;
        }
        return false;
    }

    public void RemoveFromExam(int term)
    {
        exams[term] = null;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public void setPoints(float points)
    {
        this.points = points;
    }

    public void setStartDate(Calendar startDate)
    {
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.HOUR, 0);
        startDate.set(Calendar.SECOND, 0);
        this.startDate = startDate;
    }

    public void setEndDate(Calendar endDate)
    {
        endDate.set(Calendar.MINUTE, 0);
        endDate.set(Calendar.HOUR, 0);
        endDate.set(Calendar.SECOND, 0);
        this.endDate = endDate;
    }

    public void setCourseColor(int courseColor)
    {
        this.courseColor = courseColor;
    }

    public void setCourseDays(ArrayList<CourseDay> courseDays)
    {
        this.courseDays = courseDays;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        Course course = (Course) obj;
        return this.courseName.equals(course.getCourseName());
    }
}