package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Border;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Oval;
import com.moshesteinvortzel.assaftayouri.ihw.R;

/**
 * Created by assaftayouri on 08/03/2018.
 */

public class GraderExamFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.graded_exam_item, container, false);
        view.findViewById(R.id.examGradeText).setBackground(new Oval("ss",6));
        ((TextView)view.findViewById(R.id.examGradeText)).setTextColor(Color.parseColor("#7B1FA2"));
        view.findViewById(R.id.gradedExamItemContainer).setBackground(new Border("7B1FA2",20));
        /*((TextView)view.findViewById(R.id.ovalgrade)).setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                GradeDialog gradeDialog=new GradeDialog();
                gradeDialog.show(getFragmentManager(),"ss");
                return true;
            }
        });*/

        return view;
    }
}
