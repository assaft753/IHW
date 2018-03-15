package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums;

import android.support.v4.widget.DrawerLayout;

import com.moshesteinvortzel.assaftayouri.ihw.R;

/**
 * Created by assaftayouri on 09/03/2018.
 */

public enum Priority
{
    Low, Medium, High;

    public int GetDrawable()
    {
        switch (this)
        {
            case Low:
                return R.drawable.low;
            case Medium:
                return R.drawable.medium;
            case High:
                return R.drawable.high;
            default:
                return 0;
        }
    }

    public static String[] GetOpt()
    {
        String[] strings = new String[Priority.values().length];
        for (int i = 0; i < Priority.values().length; i++)
        {
            strings[i] = Priority.values()[i].toString();
        }
        return strings;
    }
}
