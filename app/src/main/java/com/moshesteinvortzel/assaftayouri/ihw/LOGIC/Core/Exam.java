package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.Term;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by assaftayouri on 09/03/2018.
 */

public class Exam implements Comparable<Exam>
{
    private Course course;
    private int grade;
    private Term term;
    private Calendar examDate;
    private int notify;
    private int pushId;
    private boolean graded;

    public Exam(Course course, Term term, Calendar examDate, int notify)
    {
        this.course = course;
        this.term = term;
        this.examDate = examDate;
        this.notify = notify;
        this.graded = false;
    }

    public Exam()
    {
        examDate = Calendar.getInstance();
        graded = false;
    }

    public Exam(Exam currentExam)
    {
        this.course = currentExam.getCourse();
        this.term = currentExam.getTerm();
        this.examDate = Calendar.getInstance();
        this.examDate.setTimeInMillis(currentExam.GetExamDateAsCalendar().getTimeInMillis());
        this.notify = getNotify();
        this.graded = false;
    }


    public Calendar GetExamDateAsCalendar()
    {
        return this.examDate;
    }

    public int getGrade()
    {
        return grade;
    }

    public void setGrade(int grade)
    {
        this.grade = grade;
    }

    public void setTerm(Term term)
    {
        this.term = term;
    }

    public boolean getGraded()
    {
        return graded;
    }

    public void setGraded(boolean graded)
    {
        this.graded = graded;
    }

    public Term getTerm()
    {
        return term;
    }

    public int getNotify()
    {
        return notify;
    }

    public void setNotify(int notify)
    {
        this.notify = notify;
    }

    public long getExamDate()
    {
        return examDate.getTimeInMillis();
    }

    public void setExamDate(long examDate)
    {
        this.examDate.setTimeInMillis(examDate);
    }

    public int getPushId()
    {
        return pushId;
    }

    public void setCourse(Course course)
    {
        this.course = course;
    }

    @Exclude
    public Course getCourse()
    {
        return course;
    }

    public void setPushId(int pushId)
    {
        this.pushId = pushId;
    }

    @Override
    public int compareTo(@NonNull Exam exam)
    {
        return this.examDate.compareTo(exam.GetExamDateAsCalendar());
    }
}
