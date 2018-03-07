package com.moshesteinvortzel.assaftayouri.ihw;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExamsFragment extends android.support.v4.app.Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view= inflater.inflate(R.layout.exam_item, container, false);
        view.findViewById(R.id.ovalgrade).setBackground(new Oval("ss",6));
        ((TextView)view.findViewById(R.id.ovalgrade)).setTextColor(Color.parseColor("#7B1FA2"));
        view.findViewById(R.id.container).setBackground(new Border("7B1FA2",20));
        ((TextView)view.findViewById(R.id.ovalgrade)).setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                GradeDialog gradeDialog=new GradeDialog();
                gradeDialog.show(getFragmentManager(),"ss");
                return true;
            }
        });

        return view;
    }

}
