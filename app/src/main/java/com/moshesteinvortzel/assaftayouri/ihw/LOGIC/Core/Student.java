package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core;

import android.content.Context;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.TaskType;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CalendarHelper;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CalendarManager;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.NotificationManager;

import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.FormatFlagsConversionMismatchException;

/**
 * Created by assaftayouri on 09/03/2018.
 */

public class Student
{

    private int MAXTOALERT = 7;


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
        this.calendarManager = new CalendarManager();
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.courses = new ArrayList<Course>();
        this.completedHW = new ArrayList<HomeWork>();
        this.uncompletedHW = new ArrayList<HomeWork>();
        this.gradedExams = new ArrayList<Exam>();
        this.ungradedExams = new ArrayList<Exam>();
    }

    public String[] GetCourseNames()
    {
        String[] strings = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++)
        {
            strings[i] = courses.get(i).getCourseName();
        }
        return strings;
    }

    public ArrayList<Exam> getGradedExams()
    {
        return gradedExams;
    }

    public ArrayList<Exam> getUngradedExams()
    {
        return ungradedExams;
    }

    public ArrayList<Course> getCourses()
    {
        return courses;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public String getUserName()
    {
        return userName;
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

    public int UpdatedUncompletedHW(int indexUncompleted, Context context, HomeWork oldHomeWork)
    {
        HomeWork homeWork = this.uncompletedHW.get(indexUncompleted);
        Collections.sort(this.uncompletedHW);

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

    public void CanceledGradedExam(Exam exam, int position)
    {
        this.ungradedExams.add(position, exam);
    }

    public Exam AddGradedExamPartly(int toBeGraded)
    {
        return this.ungradedExams.remove(toBeGraded);
    }

    public void AddGradedExamFinish(Exam exam, int grade, Context context)
    {
        exam.setGrade(grade);
        exam.setGraded(true);
        this.gradedExams.add(exam);
        Collections.sort(this.gradedExams);
        this.RemoveNotification(exam.getPushId(), context);
    }

    public void FromGradedToUngraded(int gradedExamIndex, Context context)
    {
        Exam exam = this.gradedExams.remove(gradedExamIndex);
        exam.setGraded(false);
        this.ungradedExams.add(exam);
        Collections.sort(this.ungradedExams);
        exam.setPushId(this.AddNotification(exam.getCourse().getCourseName() + " Term " + exam.getTerm().toString(), "Exam", context, exam.getExamDate(), exam.getNotify()));
    }

    public void AddUngradedExam(Exam exam, Context context)
    {
        this.ungradedExams.add(exam);
        exam.getCourse().getExams()[exam.getTerm().ordinal()] = exam;
        Collections.sort(this.ungradedExams);
        CalendarHelper calendarHelper = new CalendarHelper(exam.getExamDate().get(Calendar.HOUR_OF_DAY), exam.getExamDate().get(Calendar.MINUTE), exam.getCourse().getCourseName(), exam.getCourse(), TaskType.Exam);
        this.calendarManager.AddToCalendar(calendarHelper, exam.getExamDate());
        exam.setPushId(this.AddNotification(exam.getCourse().getCourseName() + " Term " + exam.getTerm().toString(), "Exam", context, exam.getExamDate(), exam.getNotify()));
    }

    public void UpdatedUngradedExam(int indexUngraded, Context context, Exam oldExam)
    {
        Exam exam = this.ungradedExams.get(indexUngraded);
        Collections.sort(this.ungradedExams);

        exam.getCourse().getExams()[oldExam.getTerm().ordinal()] = null;
        exam.getCourse().getExams()[exam.getTerm().ordinal()] = exam;

        CalendarHelper calendarHelper = new CalendarHelper(oldExam.getExamDate().get(Calendar.HOUR_OF_DAY), oldExam.getExamDate().get(Calendar.MINUTE), oldExam.getCourse().getCourseName(), oldExam.getCourse(), TaskType.Exam);
        this.calendarManager.RemoveFromCalendar(calendarHelper, oldExam.getExamDate());

        calendarHelper = new CalendarHelper(exam.getExamDate().get(Calendar.HOUR_OF_DAY), exam.getExamDate().get(Calendar.MINUTE), exam.getCourse().getCourseName(), exam.getCourse(), TaskType.Exam);
        this.calendarManager.AddToCalendar(calendarHelper, exam.getExamDate());

        exam.setPushId(this.UpdateNotification(exam.getPushId(), exam.getCourse().getCourseName() + " Term " + exam.getTerm().toString(), "Exam", exam.getExamDate(), context, exam.getNotify()));
    }

    public Exam GetUngradedExam(int index)
    {
        return ungradedExams.get(index);
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
        exam.getCourse().getExams()[exam.getTerm().ordinal()] = null;
        if (exam.getPushId() != - 1)
        {
            System.out.println("enter delete push");
            RemoveNotification(exam.getPushId(), context);
        }
        CalendarHelper calendarHelper = new CalendarHelper(exam.getExamDate().get(Calendar.HOUR_OF_DAY), exam.getExamDate().get(Calendar.MINUTE), exam.getCourse().getCourseName(), exam.getCourse(), TaskType.Exam);
        this.calendarManager.RemoveFromCalendar(calendarHelper, exam.getExamDate());
    }

    public Course GetCourseAtIndex(int index)
    {
        return this.courses.get(index);
    }

    public boolean CheckValidationOfClass(String CourseName)
    {
        for (int i = 0; i < this.courses.size(); i++)
        {
            if (this.courses.get(i).getCourseName().equals(CourseName))
            {
                return false;
            }
        }
        return true;
    }

    public boolean CheckValidationOfClassWithIndex(String CourseName, int index)
    {
        for (int i = 0; i < this.courses.size(); i++)
        {
            if (this.courses.get(i).getCourseName().equals(CourseName) && i != index)
            {
                return false;
            }
        }
        return true;
    }

    public void AddClass(Course course)
    {
        this.courses.add(course);
        this.calendarManager.AddClass(course);
    }

    public void RemoveClassElements(int courseIndex, Context context)
    {
        Course course = this.courses.remove(courseIndex);

        for (int i = 0; i < this.uncompletedHW.size(); )
        {
            if (this.uncompletedHW.get(i).getCourse().equals(course))
            {
                this.RemoveHW(i, false, context);
            }
            else
            {
                i++;
            }
        }

        for (int i = 0; i < this.completedHW.size(); )
        {
            if (this.completedHW.get(i).getCourse().equals(course))
            {
                this.RemoveHW(i, true, context);
            }
            else
            {
                i++;
            }
        }

        for (int i = 0; i < this.gradedExams.size(); )
        {
            if (this.gradedExams.get(i).getCourse().equals(course))
            {
                this.RemoveExam(i, true, context);
            }
            else
            {
                i++;
            }
        }

        for (int i = 0; i < this.ungradedExams.size(); )
        {
            if (this.ungradedExams.get(i).getCourse().equals(course))
            {
                this.RemoveExam(i, false, context);
            }
            else
            {
                i++;
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
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(calendar.getTimeInMillis());
        calendar1.set(Calendar.DAY_OF_MONTH, calendar1.get(Calendar.DAY_OF_MONTH) - notify);
        if (calendar1.after(Calendar.getInstance()))
        {
            System.out.println("Notification Added");
            int pushId = notificationManager.getPushId();
            notificationManager.AddNotification(pushId, title, content, context, calendar, notify);
            return pushId;
        }
        else
        {
            System.out.println("Notification Not Added");
            return - 1;
        }
    }

    private void RemoveNotification(int pushId, Context context)
    {
        if (pushId != - 1)
        {
            System.out.println("Notification Removed");
            notificationManager.CancelNotification(pushId, context);
        }
    }

    private int UpdateNotification(int pushId, String title, String content, Calendar calendar, Context context, int notify)
    {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(calendar.getTimeInMillis());
        calendar1.set(Calendar.DAY_OF_MONTH, calendar1.get(Calendar.DAY_OF_MONTH) - notify);
        if (calendar1.after(Calendar.getInstance()))
        {
            System.out.println("Notification Updated");
            return notificationManager.UpdateNotification(pushId, title, content, calendar, context, notify);
        }
        System.out.println("Notification Not Updated");
        return - 1;
    }

    public float[] CalculateExamsAvg()
    {
        float exampoints = 0;
        float sumgrade = 0;
        Exam exam;
        for (Course course : courses)
        {
            exam = course.GetRelevantExam();
            if (exam != null)
            {
                exampoints += course.getPoints();
                sumgrade += course.getPoints() * exam.getGrade();
            }
        }
        float cal;
        if (exampoints == 0 || sumgrade == 0)
        {
            cal = 0;
        }
        else
        {
            cal = sumgrade / exampoints;
        }
        float[] result = new float[]{
                cal, exampoints
        };
        return result;
    }

    public String[] GetNotifyOpt()
    {
        String[] strs = new String[MAXTOALERT];
        for (int i = 0; i < MAXTOALERT; i++)
        {
            if (i == 0)
            {
                strs[i] = (i + 1) + " Day";
            }
            else
            {
                strs[i] = (i + 1) + " Days";
            }
        }
        return strs;
    }


    public String[] GetTermOpt()
    {
        return new String[]{"A", "B", "C"};
    }


}
