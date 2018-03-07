package com.moshesteinvortzel.assaftayouri.ihw;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by assaftayouri on 07/03/2018.
 */

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ClassItemViewHolder>
{

    public class ClassItemViewHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout viewForeground;
        LinearLayout viewBackground;

        public ClassItemViewHolder(View itemView)
        {
            super(itemView);
            viewForeground=itemView.findViewById(R.id.view_foreground);
            viewBackground=itemView.findViewById(R.id.view_background);
        }
    }

    @Override
    public ClassItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        return new ClassItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClassItemViewHolder holder, int position)
    {
        holder.viewForeground.setBackground(new Border("7B1FA2", 50));
    }

    @Override
    public int getItemCount()
    {
        return 20;
    }


}
