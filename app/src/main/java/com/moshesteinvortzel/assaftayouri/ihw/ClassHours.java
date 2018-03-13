package com.moshesteinvortzel.assaftayouri.ihw;

/**
 * Created by assaftayouri on 04/03/2018.
 */

public class ClassHours
{
    private String ClassName;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String Day;

    public ClassHours(String className, int startHour, int startMinute, int endHour, int endMinute, String day)
    {
        ClassName = className;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        Day = day;
    }
}
