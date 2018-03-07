package com.moshesteinvortzel.assaftayouri.ihw;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

/**
 * Created by assaftayouri on 06/03/2018.
 */
public class RecyclerCompleteHWItemHelper extends ItemTouchHelper.SimpleCallback
{
    private com.moshesteinvortzel.assaftayouri.ihw.RecyclerHourItemHelper.RecyclerItemTouchHelperListener listener;

    public RecyclerCompleteHWItemHelper(int dragDirs, int swipeDirs, com.moshesteinvortzel.assaftayouri.ihw.RecyclerHourItemHelper.RecyclerItemTouchHelperListener listener)
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
            final View foregroundView = ((CompletedAdapter.HWViewHolder) viewHolder).viewForeground;

            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        final View foregroundView = ((CompletedAdapter.HWViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        final View foregroundView = ((CompletedAdapter.HWViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        final View foregroundView = ((CompletedAdapter.HWViewHolder) viewHolder).viewForeground;

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

    public interface RecyclerItemTouchHelperListener
    {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
