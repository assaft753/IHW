package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core;

import android.graphics.Color;
import android.provider.CalendarContract;

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
    private Color courseColor;
    private ArrayList<CourseDay> courseDays;
    private ArrayList<HomeWork> homeWorks;
    private ArrayList<Exam> exams;

    public Course(String courseName, float points, Calendar startDate, Calendar endDate, Color courseColor)
    {
        this.courseName = courseName;
        this.points = points;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseColor = courseColor;
        this.courseDays = new ArrayList<CourseDay>();
        this.homeWorks = new ArrayList<HomeWork>();
        this.exams = new ArrayList<Exam>();
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

    public Color getCourseColor()
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

    public void AddToExam(Exam exam)
    {
        this.exams.add(exam);
    }

    public void RemoveFromExam(Exam exam)
    {
        this.exams.remove(exam);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        Course course = (Course) obj;
        return this.courseName.equals(course.getCourseName());
    }
}
