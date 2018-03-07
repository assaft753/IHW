package com.moshesteinvortzel.assaftayouri.ihw;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by assaftayouri on 07/03/2018.
 */

public class CalendarItemAdapter extends RecyclerView.Adapter<CalendarItemAdapter.CalendarItemViewHolder>
{
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
            Container=itemView.findViewById(R.id.calendarItemContainer);
            TaskType=itemView.findViewById(R.id.taskType);
            TaskName=itemView.findViewById(R.id.taskName);
            Time=itemView.findViewById(R.id.time);
            ClassName=itemView.findViewById(R.id.className);
        }
    }

    @Override
    public CalendarItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        return new CalendarItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarItemViewHolder holder, int position)
    {
        holder.Container.setBackground(new Border("7B1FA2",10));
    }

    @Override
    public int getItemCount()
    {
        return 10;
    }


}
