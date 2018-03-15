package com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Border;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Oval;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Exam;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.HomeWork;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Enums.Priority;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnLongUngradedItemListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.ShowDialogExamListener;
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

    public UncompletedHWAdapter(ArrayList<HomeWork> UncompletedList)
    {
        this.UncompletedList = UncompletedList;
    }

    public class UncompletedHWViewHolder extends ViewHolder
    {
        private TextView hwName;
        private ImageView hwImage;
        private TextView hwTime;
        private TextView hwTimeLeft;
        public View view;
        private ViewGroup Foreground;
        private ViewGroup Background;

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
                //listener.OnLongUngradedItem(holder.getAdapterPosition());
                return true;
            }
        });
        holder.hwName.setText(homeWork.getTaskName());
        Date date = homeWork.getToDate().getTime();
        SimpleDateFormat ft = new SimpleDateFormat(DATE_PATTERN);
        holder.hwTime.setText(ft.format(date));
        holder.hwImage.setImageResource(homeWork.getPriority().GetDrawable());
        long difftime = homeWork.getToDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        long diffInMillies = Math.abs(difftime);
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (difftime < 0)
        {
            diff = diff * - 1;
            holder.hwTime.setTextColor(0xfffa315b);

        }
        else
        {
            holder.hwTime.setTextColor(0xff4f4f4f);
        }
        holder.hwTimeLeft.setText(String.valueOf(diff));
        holder.getForeground().setBackground(new Border(homeWork.getCourse().getCourseColor()));

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


