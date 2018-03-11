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
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.List;

/**
 * Created by assaftayouri on 04/03/2018.
 */

public class ClassHourListAdapter extends RecyclerView.Adapter<ClassHourListAdapter.ClassHourViewHolder>
{
    private Context Context;
    private List<ClassHours> ClassHoursList;
    public ClassHourListAdapter(Context context, List<ClassHours> classHoursList)
    {
        this.Context=context;
        this.ClassHoursList=classHoursList;
    }

    public class ClassHourViewHolder extends RecyclerView.ViewHolder
    {
        public TextView ClassName;
        public TextView Hours;
        public TextView Day;
        public RelativeLayout viewBackground, viewForeground;

        public ClassHourViewHolder(View itemView)
        {
            super(itemView);
            this.ClassName=itemView.findViewById(R.id.taskName);
            this.Hours=itemView.findViewById(R.id.classHours);
            this.Day=itemView.findViewById(R.id.classDay);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }

    @Override
    public ClassHourViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_hours_item, parent, false);
        return new ClassHourViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClassHourViewHolder holder, int position)
    {
        ClassHours classHours=this.ClassHoursList.get(position);
        holder.viewForeground.setBackground(new Border("56",20));
        holder.ClassName.setText(classHours.ClassName);
        holder.Day.setText(classHours.Day);
        holder.Hours.setText(classHours.Hours);
    }

    @Override
    public int getItemCount()
    {
        return this.ClassHoursList.size();
    }

    public void removeItem(int position) {
        this.ClassHoursList.remove(position);
        notifyItemRemoved(position);
    }
}
