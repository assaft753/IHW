package com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.TaskType;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CalendarHelper;
import com.moshesteinvortzel.assaftayouri.ihw.R;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Border;
import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by assaftayouri on 07/03/2018.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>
{
    private ArrayList<CalendarHelper> calendarHelpers;

    public class CalendarViewHolder extends RecyclerView.ViewHolder
    {
        public TextView taskName;
        public TextView className;
        public TextView taskTime;
        public TextView taskType;
        public View view;

        public CalendarViewHolder(View itemView)
        {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            className = itemView.findViewById(R.id.className);
            taskTime = itemView.findViewById(R.id.taskTime);
            taskType = itemView.findViewById(R.id.taskType);
            view = itemView;
        }
    }

    public void setCalendarHelpers(ArrayList<CalendarHelper> calendarHelpers)
    {
        this.calendarHelpers = calendarHelpers;
        notifyDataSetChanged();
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        return new CalendarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position)
    {
        CalendarHelper calendarHelper = calendarHelpers.get(position);
        String taskname;
        if (calendarHelper.getTaskType() == TaskType.Class)
        {
            holder.className.setVisibility(View.INVISIBLE);
            taskname = calendarHelper.getCourse().getCourseName();
        }
        else
        {
            holder.className.setVisibility(View.VISIBLE);
            holder.className.setText(calendarHelper.getCourse().getCourseName());
            taskname = calendarHelper.getTaskName();
        }
        holder.taskName.setText(taskname);
        holder.taskType.setText(calendarHelper.getTaskType().toString());
        holder.taskTime.setText(calendarHelper.GenerateTimeStr());
        holder.view.setBackgroundColor(calendarHelper.getCourse().getCourseColor());//new Border(calendarHelper.getCourse().getCourseColor()));
    }

    @Override
    public int getItemCount()
    {
        return calendarHelpers.size();
    }


}
