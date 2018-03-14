package com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.ihw.ClassHours;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Border;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CourseDay;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by assaftayouri on 04/03/2018.
 */

public class ClassTimeListAdapter extends RecyclerView.Adapter<ClassTimeListAdapter.ClassTimeViewHolder>
{
    private Context Context;
    private ArrayList<CourseDay> classTimeList;

    public ClassTimeListAdapter(Context context, ArrayList<CourseDay> classTimeList)
    {
        this.Context = context;
        this.classTimeList = classTimeList;
    }

    public class ClassTimeViewHolder extends ViewHolder
    {
        public TextView ClassDay;
        public TextView classTime;
        public RelativeLayout viewBackground, viewForeground;

        public ClassTimeViewHolder(View itemView)
        {
            super(itemView);
            this.ClassDay = itemView.findViewById(R.id.classDay);
            this.classTime = itemView.findViewById(R.id.classTime);
            this.viewBackground = itemView.findViewById(R.id.deleteBackground);
            this.viewForeground = itemView.findViewById(R.id.viewForeground);
        }

        @Override
        public View getForeground()
        {
            return this.viewForeground;
        }

        @Override
        public View getBackground()
        {
            return this.viewBackground;
        }
    }

    @Override
    public ClassTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_hours_item, parent, false);
        return new ClassTimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClassTimeViewHolder holder, int position)
    {
        CourseDay courseDay = this.classTimeList.get(position);
        holder.ClassDay.setText(courseDay.getDayInWeek().toString());
        String timestr = GetTimeStr(courseDay.getStartHour(), courseDay.getStartMinute()) + " - " + GetTimeStr(courseDay.getEndHour(), courseDay.getEndMinute());
        holder.classTime.setText(timestr);
    }

    @Override
    public int getItemCount()
    {
        return this.classTimeList.size();
    }

    public void removeItem(int position)
    {
        this.classTimeList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(CourseDay courseDay)
    {
        if (! this.classTimeList.contains(courseDay))
        {
            this.classTimeList.add(courseDay);
            notifyItemInserted(this.classTimeList.size() - 1);
        }
    }

    private String GetTimeStr(int hour, int minute)
    {
        String minutestr = String.valueOf(minute);
        if (minute < 10)
        {
            minutestr = "0" + minutestr;
        }

        return hour + ":" + minutestr;
    }
}
