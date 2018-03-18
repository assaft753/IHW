package com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Student;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.TaskType;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CalendarHelper;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by assaftayouri on 07/03/2018.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>
{
    private static int id = 1;
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

    public void setCalendarHelpers(List<CalendarHelper> calendarHelpers)
    {
        this.calendarHelpers = (ArrayList<CalendarHelper>) calendarHelpers;
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
        holder.view.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child(User.Student.getUserID()).setValue(User.Student);
                return true;

            }
        });
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                id++;
            }
        });

    }


    @Override
    public int getItemCount()
    {
        return calendarHelpers.size();
    }


}
