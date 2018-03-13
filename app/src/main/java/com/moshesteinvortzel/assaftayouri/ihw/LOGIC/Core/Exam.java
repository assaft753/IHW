package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core;

import android.support.annotation.NonNull;

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

    public Exam(Exam currentExam)
    {
        this.course = currentExam.getCourse();
        this.term = currentExam.getTerm();
        this.examDate = Calendar.getInstance();
        this.examDate.setTimeInMillis(currentExam.getExamDate().getTimeInMillis());
        this.notify = getNotify();
        this.graded = false;
    }

    public void setGrade(int grade)
    {
        this.grade = grade;
    }

    public void setTerm(Term term)
    {
        this.term = term;
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

    public Calendar getExamDate()
    {
        return examDate;
    }

    public void setExamDate(Calendar examDate)
    {
        this.examDate = examDate;
    }

    public int getPushId()
    {
        return pushId;
    }

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
        return this.examDate.compareTo(exam.getExamDate());
    }
}
