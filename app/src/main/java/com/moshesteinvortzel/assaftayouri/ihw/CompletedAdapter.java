package com.moshesteinvortzel.assaftayouri.ihw;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by assaftayouri on 06/03/2018.
 */

class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.HWViewHolder>
{
    private ArrayList<HomeWork> list;
    private Context context;

    public CompletedAdapter(ArrayList<HomeWork> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @Override
    public HWViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_item, parent, false);
        return new HWViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HWViewHolder holder, int position)
    {
        holder.ClassName.setText(list.get(position).HomeWorkName);
        holder.ClassName.setTextColor(Color.parseColor("#" + list.get(position).Color));
        holder.Time.setText(list.get(position).Time);
        holder.Time.setTextColor(Color.parseColor("#67d751"));
        holder.Priority.setImageResource(R.drawable.medium);
        holder.viewForeground.setBackground(new Border(list.get(position).Color, 20));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public void removeItem(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
    }

    public class HWViewHolder extends RecyclerView.ViewHolder
    {
        public TextView ClassName;
        public TextView Time;
        public ImageView Priority;
        public RelativeLayout viewBackground, viewForeground;


        public HWViewHolder(View itemView)
        {
            super(itemView);
            this.ClassName = itemView.findViewById(R.id.taskName);
            this.Time = itemView.findViewById(R.id.time);
            this.Priority = itemView.findViewById(R.id.priority);
            //viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.container);
        }
    }
}