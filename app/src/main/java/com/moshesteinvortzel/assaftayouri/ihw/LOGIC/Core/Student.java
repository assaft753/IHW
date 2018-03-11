package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core;

import android.content.Context;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.TaskType;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CalendarHelper;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CalendarManager;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.NotificationManager;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by assaftayouri on 09/03/2018.
 */

public class Student
{
    private NotificationManager notificationManager;
    private CalendarManager calendarManager;
    private String email;
    private String password;
    private String userName;
    private ArrayList<Course> courses;
    private ArrayList<HomeWork> completedHW;
    private ArrayList<HomeWork> uncompletedHW;
    private ArrayList<Exam> gradedExams;
    private ArrayList<Exam> ungradedExams;

    public Student(int pushId, String email, String password, String userName)
    {
        this.notificationManager = new NotificationManager(pushId);
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.courses = new ArrayList<Course>();
        this.completedHW = new ArrayList<HomeWork>();
        this.uncompletedHW = new ArrayList<HomeWork>();
        this.gradedExams = new ArrayList<Exam>();
        this.ungradedExams = new ArrayList<Exam>();
    }

    public void AddCompletedHW(int toBeCompleted, Context context)
    {
        HomeWork homeWork = this.uncompletedHW.remove(toBeCompleted);

        this.completedHW.add(homeWork);
        Collections.sort(this.completedHW);
        this.RemoveNotification(homeWork.getPushId(), context);
    }

    public int FromCompletedToUncompleted(int completedHWIndex, Context context)
    {
        HomeWork homeWork = this.completedHW.remove(completedHWIndex);
        this.uncompletedHW.add(homeWork);
        Collections.sort(this.uncompletedHW);
        if (! homeWork.getToDate().before(Calendar.getInstance()))
        {
            return this.AddNotification("HomeWork", homeWork.getTaskName(), context, homeWork.getToDate(), homeWork.getNotify());
        }
        return homeWork.getPushId();
    }

    public int AddUncompletedHW(HomeWork homeWork, Context context)
    {
        this.uncompletedHW.add(homeWork);
        Collections.sort(this.uncompletedHW);
        CalendarHelper calendarHelper = new CalendarHelper(homeWork.getToDate().get(Calendar.HOUR_OF_DAY), homeWork.getToDate().get(Calendar.MINUTE), homeWork.getTaskName(), homeWork.getCourse(), TaskType.HomeWork);
        this.calendarManager.AddToCalendar(calendarHelper, homeWork.getToDate());
        return this.AddNotification("HomeWork", homeWork.getTaskName(), context, homeWork.getToDate(), homeWork.getNotify());
    }

    public int UpdatedUncompletedHW(int indexUncompleted, Context context, boolean toSort, HomeWork oldHomeWork)
    {
        HomeWork homeWork = this.uncompletedHW.get(indexUncompleted);
        if (toSort)
        {
            Collections.sort(this.uncompletedHW);
        }

        CalendarHelper calendarHelper = new CalendarHelper(oldHomeWork.getToDate().get(Calendar.HOUR_OF_DAY), oldHomeWork.getToDate().get(Calendar.MINUTE), oldHomeWork.getTaskName(), oldHomeWork.getCourse(), TaskType.HomeWork);
        this.calendarManager.RemoveFromCalendar(calendarHelper, oldHomeWork.getToDate());

        calendarHelper = new CalendarHelper(homeWork.getToDate().get(Calendar.HOUR_OF_DAY), homeWork.getToDate().get(Calendar.MINUTE), homeWork.getTaskName(), homeWork.getCourse(), TaskType.HomeWork);
        this.calendarManager.AddToCalendar(calendarHelper, homeWork.getToDate());

        return this.UpdateNotification(homeWork.getPushId(), "HomeWork", homeWork.getTaskName(), homeWork.getToDate(), context, homeWork.getNotify());
    }

    public HomeWork RemoveHW(int indexHW, boolean completed, Context context)
    {
        HomeWork homeWork;
        if (completed)
        {
            homeWork = this.completedHW.remove(indexHW);
        }
        else
        {
            homeWork = this.uncompletedHW.remove(indexHW);
        }
        this.RemoveHW(homeWork, context);
        return homeWork;

    }

    private void RemoveHW(HomeWork homeWork, Context context)
    {
        RemoveNotification(homeWork.getPushId(), context);
        CalendarHelper calendarHelper = new CalendarHelper(homeWork.getToDate().get(Calendar.HOUR_OF_DAY), homeWork.getToDate().get(Calendar.MINUTE), homeWork.getTaskName(), homeWork.getCourse(), TaskType.HomeWork);
        this.calendarManager.RemoveFromCalendar(calendarHelper, homeWork.getToDate());
    }


    public void AddGradedExam(int toBeGraded, Context context)
    {
        Exam exam = this.ungradedExams.remove(toBeGraded);
        this.gradedExams.add(exam);
        Collections.sort(this.gradedExams);
        this.RemoveNotification(exam.getPushId(), context);
    }

    public int FromGradedToUngraded(int gradedExamIndex, Context context)
    {
        Exam exam = this.gradedExams.remove(gradedExamIndex);
        this.ungradedExams.add(exam);
        Collections.sort(this.ungradedExams);
        if (! exam.getExamDate().before(Calendar.getInstance()))
        {
            return this.AddNotification("Exam", exam.getCourse().getCourseName(), context, exam.getExamDate(), exam.getNotify());
        }
        return exam.getPushId();
    }

    public int AddUngradedExam(Exam exam, Context context)
    {
        this.ungradedExams.add(exam);
        Collections.sort(this.ungradedExams);
        CalendarHelper calendarHelper = new CalendarHelper(exam.getExamDate().get(Calendar.HOUR_OF_DAY), exam.getExamDate().get(Calendar.MINUTE), exam.getCourse().getCourseName(), exam.getCourse(), TaskType.Exam);
        this.calendarManager.AddToCalendar(calendarHelper, exam.getExamDate());
        return this.AddNotification("Exam", exam.getCourse().getCourseName(), context, exam.getExamDate(), exam.getNotify());
    }

    public int UpdatedUngradedExam(int indexUngraded, Context context, boolean toSort, Exam oldExam)
    {
        Exam exam = this.ungradedExams.get(indexUngraded);
        if (toSort)
        {
            Collections.sort(this.ungradedExams);
        }

        CalendarHelper calendarHelper = new CalendarHelper(oldExam.getExamDate().get(Calendar.HOUR_OF_DAY), oldExam.getExamDate().get(Calendar.MINUTE), oldExam.getCourse().getCourseName(), oldExam.getCourse(), TaskType.Exam);
        this.calendarManager.RemoveFromCalendar(calendarHelper, oldExam.getExamDate());

        calendarHelper = new CalendarHelper(exam.getExamDate().get(Calendar.HOUR_OF_DAY), exam.getExamDate().get(Calendar.MINUTE), exam.getCourse().getCourseName(), exam.getCourse(), TaskType.Exam);
        this.calendarManager.AddToCalendar(calendarHelper, exam.getExamDate());

        return this.UpdateNotification(exam.getPushId(), "Exam", exam.getCourse().getCourseName(), exam.getExamDate(), context, exam.getNotify());
    }

    public Exam RemoveExam(int indexExam, boolean graded, Context context)
    {
        Exam exam;
        if (graded)
        {
            exam = this.gradedExams.remove(indexExam);
        }
        else
        {
            exam = this.ungradedExams.remove(indexExam);
        }
        this.RemoveExam(exam, context);
        return exam;
    }

    private void RemoveExam(Exam exam, Context context)
    {
        RemoveNotification(exam.getPushId(), context);
        CalendarHelper calendarHelper = new CalendarHelper(exam.getExamDate().get(Calendar.HOUR_OF_DAY), exam.getExamDate().get(Calendar.MINUTE), exam.getCourse().getCourseName(), exam.getCourse(), TaskType.Exam);
        this.calendarManager.RemoveFromCalendar(calendarHelper, exam.getExamDate());
    }

    public void AddClass(Course course)
    {
        this.courses.add(course);
        this.calendarManager.AddClass(course);
    }

    public void RemoveClass(int courseIndex, Context context)
    {
        Course course = this.courses.remove(courseIndex);
        for (int i = 0; i < this.uncompletedHW.size(); i++)
        {
            if (this.uncompletedHW.get(i).getCourse().equals(course))
            {
                this.RemoveHW(i, false, context);
            }
        }

        for (int i = 0; i < this.completedHW.size(); i++)
        {
            if (this.completedHW.get(i).getCourse().equals(course))
            {
                this.RemoveHW(i, true, context);
            }
        }
        for (int i = 0; i < this.gradedExams.size(); i++)
        {
            if (this.gradedExams.get(i).getCourse().equals(course))
            {
                this.RemoveExam(i, true, context);
            }
        }
        for (int i = 0; i < this.ungradedExams.size(); i++)
        {
            if (this.ungradedExams.get(i).getCourse().equals(course))
            {
                this.RemoveExam(i, false, context);
            }
        }
    }

    public void UpdateCourse(int courseIndex)
    {
        Course course = this.courses.get(courseIndex);
        this.calendarManager.RemoveOnlyClass(course);
        this.calendarManager.AddClass(course);
    }

    private int AddNotification(String title, String content, Context context, Calendar calendar, int notify)
    {
        int pushId = notificationManager.getPushId();
        notificationManager.AddNotification(pushId, title, content, context, calendar, notify);
        return pushId;
    }

    private void RemoveNotification(int pushId, Context context)
    {
        notificationManager.CancelNotification(pushId, context);
    }

    private int UpdateNotification(int pushId, String title, String content, Calendar calendar, Context context, int notify)
    {
        return notificationManager.UpdateNotification(pushId, title, content, calendar, context, notify);
    }
}
