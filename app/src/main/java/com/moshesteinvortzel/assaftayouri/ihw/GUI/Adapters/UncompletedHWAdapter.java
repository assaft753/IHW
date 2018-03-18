package com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.HomeWork;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnLongHWItemListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by assaftayouri on 13/03/2018.
 */

public class UncompletedHWAdapter extends RecyclerView.Adapter<UncompletedHWAdapter.UncompletedHWViewHolder>
{
    private final String DATE_PATTERN = "E','dd MMM HH:mm a ";
    private ArrayList<HomeWork> UncompletedList;
    private OnLongHWItemListener onLongHWItemListener;

    public UncompletedHWAdapter(ArrayList<HomeWork> UncompletedList, OnLongHWItemListener onLongHWItemListener)
    {
        this.UncompletedList = UncompletedList;
        this.onLongHWItemListener = onLongHWItemListener;
    }

    public class UncompletedHWViewHolder extends ViewHolder
    {
        public TextView hwName;
        public ImageView hwImage;
        public TextView hwTime;
        public TextView hwTimeLeft;
        public View view;
        public View daysLeftContainer;

        public ViewGroup Foreground;
        public ViewGroup Background;

        public UncompletedHWViewHolder(View itemView)
        {
            super(itemView);
            view = itemView;
            hwName = itemView.findViewById(R.id.hwName);
            hwImage = itemView.findViewById(R.id.hwPriority);
            hwTime = itemView.findViewById(R.id.hwTime);
            hwTimeLeft = itemView.findViewById(R.id.hwTimeLeft);
            Foreground = itemView.findViewById(R.id.viewForeground);
            Background = itemView.findViewById(R.id.Background);
            daysLeftContainer = itemView.findViewById(R.id.daysLeftContainer);

        }

        @Override
        public View getForeground()
        {
            return Foreground;
        }

        @Override
        public View getBackground()
        {
            return Background;
        }
    }


    @Override
    public UncompletedHWViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.uncompleted_hw_item, parent, false);
        return new UncompletedHWViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UncompletedHWViewHolder holder, final int position)
    {
        HomeWork homeWork = UncompletedList.get(position);
        holder.view.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                int index = holder.getAdapterPosition() + 1;
                onLongHWItemListener.OnLongHWItem(String.valueOf(index));
                return true;
            }
        });
        holder.daysLeftContainer.setVisibility(View.VISIBLE);
        holder.hwName.setText(homeWork.getTaskName());
        Date date = homeWork.GetToDateAsObject().getTime();
        SimpleDateFormat ft = new SimpleDateFormat(DATE_PATTERN);
        holder.hwTime.setText(ft.format(date));
        holder.hwImage.setImageResource(homeWork.getPriority().GetDrawable());
        long difftime = homeWork.GetToDateAsObject().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        long diffInMillies = Math.abs(difftime);
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (diff <= 3)
        {
            if (difftime < 0)
            {
                diff = diff * - 1;
            }
            holder.hwTime.setTextColor(0xFFEF5350);//fffa315b);
        }
        else
        {
            holder.hwTime.setTextColor(0xffffffff);//ff4f4f4f);
        }
        holder.hwTimeLeft.setText(String.valueOf(diff));
        holder.getForeground().setBackgroundColor(homeWork.getCourse().getCourseColor());//holder.getForeground().setBackground(new Border(homeWork.getCourse().getCourseColor()));

    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit)
    {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.DAYS);
    }

    @Override
    public int getItemCount()
    {
        return UncompletedList.size();
    }
}


