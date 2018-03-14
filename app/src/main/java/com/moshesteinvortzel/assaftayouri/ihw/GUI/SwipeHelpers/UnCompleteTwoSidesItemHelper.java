package com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

//import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.CompletedAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.ViewHolder;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.SwipeHelperListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

/**
 * Created by assaftayouri on 06/03/2018.
 */
public class UnCompleteTwoSidesItemHelper<T extends ViewHolder> extends ItemTouchHelper.SimpleCallback
{
    private SwipeHelperListener listener;

    public UnCompleteTwoSidesItemHelper(int dragDirs, int swipeDirs, SwipeHelperListener listener)
    {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState)
    {
        if (viewHolder != null)
        {
            final View foregroundView = ((T) viewHolder).getForeground();
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        final View foregroundView = ((T) viewHolder).getForeground();
        final View backgroundView = ((T) viewHolder).getBackground();

        if (dX > 0)
        {
            backgroundView.findViewById(R.id.deleteBackground).setVisibility(View.VISIBLE);
            backgroundView.findViewById(R.id.unCompleteBackground).setVisibility(View.GONE);
        }
        else
        {
            backgroundView.findViewById(R.id.deleteBackground).setVisibility(View.GONE);
            backgroundView.findViewById(R.id.unCompleteBackground).setVisibility(View.VISIBLE);
        }

        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        final View foregroundView = ((T) viewHolder).getForeground();
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        final View foregroundView = ((T) viewHolder).getForeground();

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {

        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection)
    {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

}
