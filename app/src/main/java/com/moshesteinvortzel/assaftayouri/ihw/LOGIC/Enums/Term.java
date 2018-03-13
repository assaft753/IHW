package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums;

/**
 * Created by assaftayouri on 09/03/2018.
 */

public enum Term
{
    A, B, C;

    public static Term GetTermByIndex(int index)
    {
        switch (index)
        {
            case 0:
                return A;
            case 1:
                return B;
            case 2:
                return C;
            default:
                return A;
        }
    }
}
