package com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * Created by assaftayouri on 12/03/2018.
 */

public interface SwipeHelperListener
{
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
