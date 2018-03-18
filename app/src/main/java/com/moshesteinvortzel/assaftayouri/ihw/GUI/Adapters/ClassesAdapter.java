package com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnLongClassItemListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by assaftayouri on 07/03/2018.
 */

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ClassViewHolder>
{
    private ArrayList<Course> courses;
    private OnLongClassItemListener onLongClassItem;

    public ClassesAdapter(ArrayList<Course> courses, OnLongClassItemListener onLongClassItem)
    {
        this.onLongClassItem = onLongClassItem;
        this.courses = courses;
    }

    public class ClassViewHolder extends ViewHolder
    {
        public TextView className;
        public TextView classPoints;
        public TextView classDates;
        private RelativeLayout foreground;
        private RelativeLayout background;
        public View itemViewl;

        public ClassViewHolder(View itemView)
        {
            super(itemView);
            itemViewl = itemView;
            className = itemView.findViewById(R.id.className);
            classPoints = itemView.findViewById(R.id.classPoints);
            classDates = itemView.findViewById(R.id.classDates);
            foreground = itemView.findViewById(R.id.viewForeground);
            background = itemView.findViewById(R.id.deleteBackground);
        }

        @Override
        public View getForeground()
        {
            return this.foreground;
        }

        @Override
        public View getBackground()
        {
            return this.background;
        }
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        return new ClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClassViewHolder holder, final int position)
    {
        Course course = courses.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                onLongClassItem.OnLongClassItem(holder.getAdapterPosition());
                return true;
            }
        });
        //holder.getForeground().setBackground(new Border(course.getCourseColor()));
        holder.getForeground().setBackgroundColor(course.getCourseColor());
        holder.className.setText(course.getCourseName());
        holder.classPoints.setText(String.valueOf(course.getPoints()));
        String datestr = GetDateString(course.GetStartDateAsCalendar(), course.GetEndDateAsCalendar());
        holder.classDates.setText(datestr);
    }

    @Override
    public int getItemCount()
    {
        return courses.size();
    }

    private String GetDateString(Calendar from, Calendar to)
    {
        return MakeDateString(from) + " - " + MakeDateString(to);
    }

    private String MakeDateString(Calendar calendar)
    {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
    }

    public void removeItem(int position)
    {
        notifyItemRemoved(position);
    }

    public void addItem(Course course)
    {
        if (! this.courses.contains(course))
        {
            this.courses.add(course);
            notifyItemInserted(this.courses.size() - 1);
        }
    }
}
