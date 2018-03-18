package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core;

import android.graphics.Color;
import android.provider.CalendarContract;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.Term;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CourseDay;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private List<CourseDay> courseDays;
    private List<HomeWork> homeWorks;
    private Exam[] exams;

    public Course(String courseName, float points, Calendar startDate, Calendar endDate, int courseColor, List<CourseDay> courseDays)
    {
        this.courseName = courseName;
        this.points = points;
        this.startDate = Calendar.getInstance();
        this.endDate = Calendar.getInstance();
        setStartDate(startDate.getTimeInMillis());
        setEndDate(endDate.getTimeInMillis());
        this.courseColor = courseColor;
        this.courseDays = courseDays;
        this.homeWorks = new ArrayList<HomeWork>();
        this.exams = new Exam[3];
    }


    public Course()
    {
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        homeWorks = new ArrayList<>();
        this.exams = new Exam[3];
        courseDays = new ArrayList<>();
    }

    public void setExams(List<Exam> exams)
    {
        for (int i = 0; i < exams.size(); i++)
        {
            this.exams[i] = exams.get(i);
        }
    }

    public void setHomeWorks(List<HomeWork> homeWorks)
    {
        this.homeWorks = homeWorks;
    }

    public List<HomeWork> getHomeWorks()
    {
        return homeWorks;
    }

    public List<Exam> getExams()
    {
        return Arrays.asList(exams);
    }

    public Exam[] GetExamsAsArray()
    {
        return this.exams;
    }

    public boolean CheckExamValidate(Term term)
    {
        if (exams[term.ordinal()] != null)
        {
            return false;
        }
        return true;

    }

    public Exam GetRelevantExam()
    {
        for (int i = exams.length - 1; i >= 0; i--)
        {
            if (exams[i] != null && exams[i].getGraded())
            {
                return exams[i];
            }
        }
        return null;
    }

    public boolean CheckExamDate(Calendar calendar, int skipIndex)
    {

        for (int i = 0; i < exams.length; i++)
        {
            if (exams[i] != null && i != skipIndex)
            {
                if (calendar.get(Calendar.YEAR) == exams[i].GetExamDateAsCalendar().get(Calendar.YEAR)
                        && calendar.get(Calendar.DAY_OF_MONTH) == exams[i].GetExamDateAsCalendar().get(Calendar.DAY_OF_MONTH)
                        && calendar.get(Calendar.MONTH) == exams[i].GetExamDateAsCalendar().get(Calendar.MONTH))
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

    public Calendar GetStartDateAsCalendar()
    {
        return startDate;
    }

    public Calendar GetEndDateAsCalendar()
    {
        return endDate;
    }

    public float getPoints()
    {
        return points;
    }

    public long getStartDate()
    {
        return startDate.getTimeInMillis();
    }

    public long getEndDate()
    {
        return endDate.getTimeInMillis();
    }

    public int getCourseColor()
    {
        return courseColor;
    }

    public List<CourseDay> getCourseDays()
    {
        return courseDays;
    }

    public boolean CheckNewHomeWork(String homeWorkStr)
    {
        for (HomeWork homeWork : homeWorks)
        {
            if (homeWork.getTaskName().equals(homeWorkStr))
            {
                return false;
            }
        }
        return true;
    }

    public boolean CheckExistsHomeWork(String homeWorkStr)
    {
        int count = 0;
        for (HomeWork homeWork : homeWorks)
        {
            if (homeWork.getTaskName().equals(homeWorkStr))
            {
                count++;
                if (count > 1)
                {
                    return false;
                }
            }
        }
        return true;
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

    public void setStartDate(long startDate)
    {
        this.startDate.setTimeInMillis(startDate);
        this.startDate.set(Calendar.MINUTE, 0);
        this.startDate.set(Calendar.HOUR, 0);
        this.startDate.set(Calendar.SECOND, 0);

    }

    public void setEndDate(long endDate)
    {
        this.endDate.setTimeInMillis(endDate);
        this.startDate.set(Calendar.MINUTE, 0);
        this.startDate.set(Calendar.HOUR, 0);
        this.startDate.set(Calendar.SECOND, 0);
    }

    public void SetStartDateAsCalendar(Calendar startDate)
    {
        this.startDate = startDate;
    }

    public void SetEndDateAsCalendar(Calendar endDate)
    {
        this.endDate = endDate;
    }

    public void setCourseColor(int courseColor)
    {
        this.courseColor = courseColor;
    }

    public void setCourseDays(List<CourseDay> courseDays)
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
