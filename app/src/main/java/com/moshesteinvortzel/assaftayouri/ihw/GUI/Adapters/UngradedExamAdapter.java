package com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Border;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Oval;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Exam;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by assaftayouri on 13/03/2018.
 */

public class UngradedExamAdapter extends RecyclerView.Adapter<UngradedExamAdapter.UngradedExamViewHolder>
{
    private ArrayList<Exam> ungradedList;

    public UngradedExamAdapter(ArrayList<Exam> ungradedList)
    {
        this.ungradedList = ungradedList;
    }

    public class UngradedExamViewHolder extends ViewHolder
    {
        public TextView examClassText;
        public TextView examPointsText;
        public TextView examTermText;
        public TextView examDateText;
        public TextView examUngradedText;
        private ViewGroup Foreground;
        private ViewGroup Background;

        public UngradedExamViewHolder(View itemView)
        {
            super(itemView);
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
    public UngradedExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ungraded_exam_item, parent, false);
        return new UngradedExamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UngradedExamViewHolder holder, int position)
    {
        Exam exam = ungradedList.get(position);
        holder.examClassText.setText(exam.getCourse().getCourseName());
        holder.examPointsText.setText(String.valueOf(exam.getCourse().getPoints()));
        holder.examTermText.setText(exam.getTerm().toString());
        Date date = exam.getExamDate().getTime();
        SimpleDateFormat ft = new SimpleDateFormat("E','dd MMM HH:mm a ");
        holder.examDateText.setText(ft.format(date));
        holder.getForeground().setBackground(new Border(exam.getCourse().getCourseColor(), 20));
        holder.examUngradedText.setBackground(new Oval(exam.getCourse().getCourseColor()));

    }

    @Override
    public int getItemCount()
    {
        return ungradedList.size();
    }

    public void removeItem(int position)
    {
        notifyItemRemoved(position);
    }

    public void addItem(Exam exam)
    {
        this.ungradedList.add(exam);
        notifyItemInserted(this.ungradedList.size() - 1);
    }
}


