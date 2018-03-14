package com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Border;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Oval;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Exam;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnLongGradedItemListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.ShowDialogExamListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by assaftayouri on 13/03/2018.
 */

public class GradedExamAdapter extends RecyclerView.Adapter<GradedExamAdapter.GradedExamViewHolder>
{
    private final int BORDER_WIDTH = 20;
    private final String DATE_PATTERN = "E','dd MMM HH:mm a ";
    private ArrayList<Exam> gradedList;
    private OnLongGradedItemListener onLongGradedItemListener;

    public GradedExamAdapter(ArrayList<Exam> gradedList, OnLongGradedItemListener onLongGradedItemListener)
    {
        this.onLongGradedItemListener = onLongGradedItemListener;
        this.gradedList = gradedList;
    }

    public class GradedExamViewHolder extends ViewHolder
    {
        public TextView examClassText;
        public TextView examPointsText;
        public TextView examTermText;
        public TextView examDateText;
        public TextView examUngradedText;
        public View view;
        private ViewGroup Foreground;
        private ViewGroup Background;

        public GradedExamViewHolder(View itemView)
        {
            super(itemView);
            view = itemView;
            examClassText = itemView.findViewById(R.id.examClassText);
            examPointsText = itemView.findViewById(R.id.examPointsText);
            examTermText = itemView.findViewById(R.id.examTermText);
            examDateText = itemView.findViewById(R.id.examDateText);
            examUngradedText = itemView.findViewById(R.id.examUngradeText);
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
    public GradedExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.graded_exam_item, parent, false);
        return new GradedExamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GradedExamViewHolder holder, final int position)
    {
        Exam exam = gradedList.get(position);
        holder.view.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                System.out.println(holder.getAdapterPosition());
                onLongGradedItemListener.OnLongGradedItem(holder.getAdapterPosition());
                return true;
            }
        });
        holder.examClassText.setText(exam.getCourse().getCourseName());
        holder.examPointsText.setText(String.valueOf(exam.getCourse().getPoints()));
        holder.examTermText.setText(exam.getTerm().toString());
        Date date = exam.getExamDate().getTime();
        SimpleDateFormat ft = new SimpleDateFormat(DATE_PATTERN);
        holder.examDateText.setText(ft.format(date));
        holder.getForeground().setBackground(new Border(exam.getCourse().getCourseColor(), BORDER_WIDTH));
        holder.examUngradedText.setBackground(new Oval(exam.getCourse().getCourseColor()));
        holder.examUngradedText.setText(String.valueOf(exam.getGrade()));

    }

    @Override
    public int getItemCount()
    {
        return gradedList.size();
    }
}


