package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core;

import android.content.Context;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.TaskType;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CalendarHelper;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CalendarManager;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.NotificationManager;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by assaftayouri on 09/03/2018.
 */

public class Student
{

    private int MAXTOALERT = 7;


    private NotificationManager notificationManager;
    private CalendarManager calendarManager;
    private String email;
    private String userID;
    private int pushId;
    private String password;
    private String userName;
    private List<Course> courses;
    private ArrayList<HomeWork> completedHW;
    private ArrayList<HomeWork> uncompletedHW;
    private ArrayList<Exam> gradedExams;
    private ArrayList<Exam> ungradedExams;

    public Student(int pushId, String email, String password, String userID, String userName)
    {
        this.pushId = pushId;
        this.notificationManager = new NotificationManager(pushId);
        this.calendarManager = new CalendarManager();
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.userID = userID;
        this.courses = new ArrayList<Course>();
        this.completedHW = new ArrayList<HomeWork>();
        this.uncompletedHW = new ArrayList<HomeWork>();
        this.gradedExams = new ArrayList<Exam>();
        this.ungradedExams = new ArrayList<Exam>();
    }


    public Student()
    {
        this.courses = new ArrayList<Course>();
        this.completedHW = new ArrayList<HomeWork>();
        this.uncompletedHW = new ArrayList<HomeWork>();
        this.gradedExams = new ArrayList<Exam>();
        this.ungradedExams = new ArrayList<Exam>();
        this.calendarManager = new CalendarManager();
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public List<CalendarHelper> getCalendarManager()
    {
        return calendarManager.getCalendarDictionary();
    }

    public void setCalendarManager(List<CalendarHelper> calendarManager)
    {
        this.calendarManager.setCalendarDictionary(calendarManager);
    }

    @Exclude
    public ArrayList<HomeWork> getCompletedHW()
    {
        return completedHW;
    }

    @Exclude
    public ArrayList<HomeWork> getUncompletedHW()
    {
        return uncompletedHW;
    }

    @Exclude
    public ArrayList<Exam> getGradedExams()
    {
        return gradedExams;
    }

    @Exclude
    public ArrayList<Exam> getUngradedExams()
    {
        return ungradedExams;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setCourses(List<Course> courses)
    {
        this.courses = courses;
    }

    public Course FindCourseByName(String courseName)
    {
        for (Course course : courses)
        {
            if (course.getCourseName().equals(courseName))
            {
                return course;
            }
        }
        return null;
    }

    public int getPushId()
    {
        return notificationManager.GeneratePushId();
    }

    public void setPushId(int pushId)
    {
        if (notificationManager == null)
        {
            notificationManager = new NotificationManager(pushId);
        }
        else
        {
            notificationManager.setPushId(pushId);
        }
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

    public List<Course> getCourses()
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


    public HomeWork GetUncompletedHWByIndex(int hwIndex)
    {
        return this.uncompletedHW.get(hwIndex);
    }

    public HomeWork GetCompletedHWByIndex(int hwIndex)
    {
        return this.completedHW.get(hwIndex);
    }

    public void AddCompletedHW(int toBeCompleted, Context context)
    {
        HomeWork homeWork = this.uncompletedHW.remove(toBeCompleted);
        homeWork.setFinished(true);
        this.completedHW.add(homeWork);
        Collections.sort(this.completedHW);
        this.RemoveNotification(homeWork.getPushId(), context);
    }

    public void FromCompletedToUncompleted(int completedHWIndex, Context context)
    {
        HomeWork homeWork = this.completedHW.remove(completedHWIndex);
        homeWork.setFinished(false);
        this.uncompletedHW.add(homeWork);
        Collections.sort(this.uncompletedHW);
        homeWork.setPushId(this.AddNotification(homeWork.getTaskName(), context.getResources().getString(R.string.homeWork), context, homeWork.GetToDateAsObject(), homeWork.getNotify()));
    }

    public void AddUncompletedHW(HomeWork homeWork, Context context)
    {
        this.uncompletedHW.add(homeWork);
        homeWork.getCourse().AddToHomeWork(homeWork);
        Collections.sort(this.uncompletedHW);
        CalendarHelper calendarHelper = new CalendarHelper(homeWork.GetToDateAsObject().get(Calendar.HOUR_OF_DAY), homeWork.GetToDateAsObject().get(Calendar.MINUTE), homeWork.GetToDateAsObject().get(Calendar.DAY_OF_MONTH), homeWork.GetToDateAsObject().get(Calendar.MONTH), homeWork.GetToDateAsObject().get(Calendar.YEAR), homeWork.getTaskName(), homeWork.getCourse(), TaskType.HomeWork);
        this.calendarManager.AddToCalendar(calendarHelper, context);
        homeWork.setPushId(this.AddNotification(homeWork.getTaskName(), context.getResources().getString(R.string.homeWork), context, homeWork.GetToDateAsObject(), homeWork.getNotify()));
    }

    public void UpdatedUncompletedHW(int indexUncompleted, Context context, HomeWork oldHomeWork)
    {
        HomeWork homeWork = this.uncompletedHW.get(indexUncompleted);
        Collections.sort(this.uncompletedHW);

        CalendarHelper calendarHelper = new CalendarHelper(oldHomeWork.GetToDateAsObject().get(Calendar.HOUR_OF_DAY), oldHomeWork.GetToDateAsObject().get(Calendar.MINUTE), oldHomeWork.GetToDateAsObject().get(Calendar.DAY_OF_MONTH), oldHomeWork.GetToDateAsObject().get(Calendar.MONTH), oldHomeWork.GetToDateAsObject().get(Calendar.YEAR), oldHomeWork.getTaskName(), oldHomeWork.getCourse(), TaskType.HomeWork);
        this.calendarManager.RemoveFromCalendar(calendarHelper, context);

        calendarHelper = new CalendarHelper(homeWork.GetToDateAsObject().get(Calendar.HOUR_OF_DAY), homeWork.GetToDateAsObject().get(Calendar.MINUTE), homeWork.GetToDateAsObject().get(Calendar.DAY_OF_MONTH), homeWork.GetToDateAsObject().get(Calendar.MONTH), homeWork.GetToDateAsObject().get(Calendar.YEAR), homeWork.getTaskName(), homeWork.getCourse(), TaskType.HomeWork);
        this.calendarManager.AddToCalendar(calendarHelper, context);

        homeWork.setPushId(this.UpdateNotification(homeWork.getPushId(), homeWork.getTaskName(), context.getResources().getString(R.string.homeWork), homeWork.GetToDateAsObject(), context, homeWork.getNotify()));
    }

    public void UpdatedCompletedHW(int indexCompleted, Context context, HomeWork oldHomeWork)
    {
        HomeWork homeWork = this.completedHW.get(indexCompleted);
        Collections.sort(this.completedHW);

        CalendarHelper calendarHelper = new CalendarHelper(oldHomeWork.GetToDateAsObject().get(Calendar.HOUR_OF_DAY), oldHomeWork.GetToDateAsObject().get(Calendar.MINUTE), oldHomeWork.GetToDateAsObject().get(Calendar.DAY_OF_MONTH), oldHomeWork.GetToDateAsObject().get(Calendar.MONTH), oldHomeWork.GetToDateAsObject().get(Calendar.YEAR), oldHomeWork.getTaskName(), oldHomeWork.getCourse(), TaskType.HomeWork);
        this.calendarManager.RemoveFromCalendar(calendarHelper, context);

        calendarHelper = new CalendarHelper(homeWork.GetToDateAsObject().get(Calendar.HOUR_OF_DAY), homeWork.GetToDateAsObject().get(Calendar.MINUTE), homeWork.GetToDateAsObject().get(Calendar.DAY_OF_MONTH), homeWork.GetToDateAsObject().get(Calendar.MONTH), homeWork.GetToDateAsObject().get(Calendar.YEAR), homeWork.getTaskName(), homeWork.getCourse(), TaskType.HomeWork);
        this.calendarManager.AddToCalendar(calendarHelper, context);
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
        homeWork.getCourse().RemoveFromHomeWork(homeWork);
        if (homeWork.getPushId() != - 1)
        {
            RemoveNotification(homeWork.getPushId(), context);
        }
        CalendarHelper calendarHelper = new CalendarHelper(homeWork.GetToDateAsObject().get(Calendar.HOUR_OF_DAY), homeWork.GetToDateAsObject().get(Calendar.MINUTE), homeWork.GetToDateAsObject().get(Calendar.DAY_OF_MONTH), homeWork.GetToDateAsObject().get(Calendar.MONTH), homeWork.GetToDateAsObject().get(Calendar.YEAR), homeWork.getTaskName(), homeWork.getCourse(), TaskType.HomeWork);
        this.calendarManager.RemoveFromCalendar(calendarHelper, context);
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
        exam.setPushId(this.AddNotification(exam.getCourse().getCourseName() + " " + context.getResources().getString(R.string.term) + " " + exam.getTerm().toString(), context.getResources().getString(R.string.exam), context, exam.GetExamDateAsCalendar(), exam.getNotify()));
    }

    public void AddUngradedExam(Exam exam, Context context)
    {
        this.ungradedExams.add(exam);
        exam.getCourse().GetExamsAsArray()[exam.getTerm().ordinal()] = exam;
        Collections.sort(this.ungradedExams);
        CalendarHelper calendarHelper = new CalendarHelper(exam.GetExamDateAsCalendar().get(Calendar.HOUR_OF_DAY), exam.GetExamDateAsCalendar().get(Calendar.MINUTE), exam.GetExamDateAsCalendar().get(Calendar.DAY_OF_MONTH), exam.GetExamDateAsCalendar().get(Calendar.MONTH), exam.GetExamDateAsCalendar().get(Calendar.YEAR), context.getResources().getString(R.string.term) + " " + exam.getTerm().toString(), exam.getCourse(), TaskType.Exam);
        this.calendarManager.AddToCalendar(calendarHelper, context);
        exam.setPushId(this.AddNotification(exam.getCourse().getCourseName() + " " + context.getResources().getString(R.string.term) + " " + exam.getTerm().toString(), context.getResources().getString(R.string.exam), context, exam.GetExamDateAsCalendar(), exam.getNotify()));
    }

    public void UpdatedUngradedExam(int indexUngraded, Context context, Exam oldExam)
    {
        Exam exam = this.ungradedExams.get(indexUngraded);
        Collections.sort(this.ungradedExams);

        exam.getCourse().GetExamsAsArray()[oldExam.getTerm().ordinal()] = null;
        exam.getCourse().GetExamsAsArray()[exam.getTerm().ordinal()] = exam;

        CalendarHelper calendarHelper = new CalendarHelper(oldExam.GetExamDateAsCalendar().get(Calendar.HOUR_OF_DAY), oldExam.GetExamDateAsCalendar().get(Calendar.MINUTE), oldExam.GetExamDateAsCalendar().get(Calendar.DAY_OF_MONTH), oldExam.GetExamDateAsCalendar().get(Calendar.MONTH), oldExam.GetExamDateAsCalendar().get(Calendar.YEAR), context.getResources().getString(R.string.term) + " " + oldExam.getTerm().toString(), oldExam.getCourse(), TaskType.Exam);
        this.calendarManager.RemoveFromCalendar(calendarHelper, context);

        calendarHelper = new CalendarHelper(exam.GetExamDateAsCalendar().get(Calendar.HOUR_OF_DAY), exam.GetExamDateAsCalendar().get(Calendar.MINUTE), exam.GetExamDateAsCalendar().get(Calendar.DAY_OF_MONTH), exam.GetExamDateAsCalendar().get(Calendar.MONTH), exam.GetExamDateAsCalendar().get(Calendar.YEAR), context.getResources().getString(R.string.term) + " " + exam.getTerm().toString(), exam.getCourse(), TaskType.Exam);
        this.calendarManager.AddToCalendar(calendarHelper, context);

        exam.setPushId(this.UpdateNotification(exam.getPushId(), exam.getCourse().getCourseName() + " " + context.getResources().getString(R.string.term) + " " + exam.getTerm().toString(), context.getResources().getString(R.string.exam), exam.GetExamDateAsCalendar(), context, exam.getNotify()));
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
        exam.getCourse().GetExamsAsArray()[exam.getTerm().ordinal()] = null;
        if (exam.getPushId() != - 1)
        {
            RemoveNotification(exam.getPushId(), context);
        }
        CalendarHelper calendarHelper = new CalendarHelper(exam.GetExamDateAsCalendar().get(Calendar.HOUR_OF_DAY), exam.GetExamDateAsCalendar().get(Calendar.MINUTE), exam.GetExamDateAsCalendar().get(Calendar.DAY_OF_MONTH), exam.GetExamDateAsCalendar().get(Calendar.MONTH), exam.GetExamDateAsCalendar().get(Calendar.YEAR), context.getResources().getString(R.string.term) + " " + exam.getTerm().toString(), exam.getCourse(), TaskType.Exam);
        this.calendarManager.RemoveFromCalendar(calendarHelper, context);
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

    public void AddClass(Course course, Context context)
    {
        this.courses.add(course);
        this.calendarManager.AddClass(course, context);
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
        calendarManager.RemoveClass(course, context);
    }

    public void UpdateCourse(int courseIndex, Context context)
    {
        Course course = this.courses.get(courseIndex);
        this.calendarManager.RemoveOnlyClass(course, context);
        this.calendarManager.AddClass(course, context);
    }

    private int AddNotification(String title, String content, Context context, Calendar calendar, int notify)
    {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(calendar.getTimeInMillis());
        calendar1.set(Calendar.DAY_OF_MONTH, calendar1.get(Calendar.DAY_OF_MONTH) - notify);
        if (calendar1.after(Calendar.getInstance()))
        {
            int pushId = notificationManager.GeneratePushId();
            notificationManager.AddNotification(pushId, title, content, context, calendar, notify);
            return pushId;
        }
        else
        {
            return - 1;
        }
    }

    private void RemoveNotification(int pushId, Context context)
    {
        if (pushId != - 1)
        {
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
            return notificationManager.UpdateNotification(pushId, title, content, calendar, context, notify);
        }
        return - 1;
    }

    public ArrayList<Event> GetEvents()
    {
        return calendarManager.GetEvent();
    }

    public List<CalendarHelper> GetListOFTasksInDate(Date date)
    {
        return calendarManager.GetListOFTasksInDate(date);
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

    public void SaveChanges(DatabaseReference myRef)
    {
        myRef.child(User.Student.getUserID()).setValue(User.Student);
    }


}
