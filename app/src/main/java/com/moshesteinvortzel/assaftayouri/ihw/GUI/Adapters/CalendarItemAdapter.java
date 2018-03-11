package com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moshesteinvortzel.assaftayouri.ihw.R;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Border;
import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

/**
 * Created by assaftayouri on 07/03/2018.
 */

public class CalendarItemAdapter extends SwipeAdapter implements View.OnLongClickListener
{

    public Context context;
    public int size=10;

    public CalendarItemAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public boolean onLongClick(View view)
    {
        Toast toast = Toast.makeText(this.context, "Deleted item at position ", Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }

    class CalendarItemViewHolder extends RecyclerView.ViewHolder
    {
        private RelativeLayout Container;
        private TextView TaskType;
        private TextView TaskName;
        private TextView Time;
        private TextView ClassName;

        public CalendarItemViewHolder(View itemView)
        {
            super(itemView);
            Container = itemView.findViewById(R.id.calendarItemContainer);
            TaskType = itemView.findViewById(R.id.taskType);
            TaskName = itemView.findViewById(R.id.taskName);
            Time = itemView.findViewById(R.id.time);
            ClassName = itemView.findViewById(R.id.className);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_item, viewGroup, true);
        ((RelativeLayout) v.findViewById(R.id.calendarItemContainer)).setOnLongClickListener(this);
        return new CalendarItemViewHolder(v);
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder viewHolder, int i)
    {
        ((CalendarItemViewHolder) viewHolder).Container.setBackground(new Border("7B1FA2", 10));
    }

    @Override
    public int getItemCount()
    {
        return size;
    }

    @Override
    public SwipeConfiguration onCreateSwipeConfiguration(Context context, int i)
    {
        return new SwipeConfiguration.Builder(context)
                .setLeftBackgroundColorResource(R.color.md_red_A700)
                .setRightBackgroundColorResource(R.color.md_green_500)
                .setDrawableResource(R.drawable.ic_delete_white_24dp)
                .setRightDrawableResource(R.drawable.ic_add_white_24dp)
                .setLeftSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NORMAL_SWIPE)
                .setRightSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NORMAL_SWIPE)
                .build();
    }

    @Override
    public void onSwipe(int i, int i1)
    {
        if (i1 == SWIPE_LEFT)
        {
            size--;
            notifyItemRemoved(i);

        }
        else
        {
            Toast toast = Toast.makeText(this.context, "Marked item as read at position " + i, Toast.LENGTH_SHORT);
            toast.show();
        }

    }


}
