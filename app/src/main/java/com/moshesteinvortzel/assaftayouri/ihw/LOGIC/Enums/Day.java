package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Enums;

/**
 * Created by assaftayouri on 09/03/2018.
 */

public enum Day
{
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

    public int GetDayNumber()
    {
        return this.ordinal() + 1;
    }
}
