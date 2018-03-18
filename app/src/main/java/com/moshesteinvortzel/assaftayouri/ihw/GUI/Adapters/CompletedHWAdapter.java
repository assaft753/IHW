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

/**
 * Created by assaftayouri on 13/03/2018.
 */

public class CompletedHWAdapter extends RecyclerView.Adapter<CompletedHWAdapter.CompletedHWViewHolder>
{
    private final String DATE_PATTERN = "E','dd MMM HH:mm a ";
    private ArrayList<HomeWork> completedList;
    private OnLongHWItemListener onLongHWItemListener;

    public CompletedHWAdapter(ArrayList<HomeWork> completedList, OnLongHWItemListener onLongHWItemListener)
    {
        this.completedList = completedList;
        this.onLongHWItemListener = onLongHWItemListener;
    }

    public class CompletedHWViewHolder extends ViewHolder
    {
        public TextView hwName;
        public ImageView hwImage;
        public TextView hwTime;
        public View view;
        public ViewGroup Foreground;
        public ViewGroup Background;

        public CompletedHWViewHolder(View itemView)
        {
            super(itemView);
            view = itemView;
            hwName = itemView.findViewById(R.id.hwName);
            hwImage = itemView.findViewById(R.id.hwPriority);
            hwTime = itemView.findViewById(R.id.hwTime);
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
    public CompletedHWViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_hw_item, parent, false);
        return new CompletedHWViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CompletedHWViewHolder holder, final int position)
    {
        HomeWork homeWork = completedList.get(position);
        holder.view.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                int index = (holder.getAdapterPosition() + 1) * - 1;
                System.out.println(index);
                onLongHWItemListener.OnLongHWItem(String.valueOf(index));
                return true;
            }
        });
        holder.hwName.setText(homeWork.getTaskName());
        Date date = homeWork.GetToDateAsObject().getTime();
        SimpleDateFormat ft = new SimpleDateFormat(DATE_PATTERN);
        holder.hwTime.setText(ft.format(date));
        holder.hwImage.setImageResource(homeWork.getPriority().GetDrawable());
        holder.hwTime.setTextColor(0xFF00C853);
        holder.getForeground().setBackgroundColor(homeWork.getCourse().getCourseColor());
        //holder.getForeground().setBackground(new Border(homeWork.getCourse().getCourseColor()));

    }

    @Override
    public int getItemCount()
    {
        return completedList.size();
    }
}


