package com.moshesteinvortzel.assaftayouri.ihw;

/**
 * Created by assaftayouri on 04/03/2018.
 */

public class ClassHours
{
    public String ClassName;
    public String Hours;
    public String Day;

    public ClassHours(String className, String startHour,String endHour,String day)
    {
        ClassName = className;
        Hours = startHour+"-"+endHour;
        Day=day;
    }
}
