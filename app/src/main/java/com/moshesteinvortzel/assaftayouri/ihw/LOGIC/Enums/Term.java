package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums;

/**
 * Created by assaftayouri on 09/03/2018.
 */

public enum Term
{
    A, B, C;

    public static String[] GetOpt()
    {
        String[] strings = new String[Term.values().length];
        for (int i = 0; i < Term.values().length; i++)
        {
            strings[i] = Term.values()[i].toString();
        }
        return strings;
    }
}

