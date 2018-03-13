package com.moshesteinvortzel.assaftayouri.ihw.GUI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.ClassTimeListAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.ClassHours;
//mport com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.RecyclerHourItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.ArrayList;

public class HoursclassfragActivity extends AppCompatActivity //implements RecyclerHourItemHelper.RecyclerItemTouchHelperListener
{
    private ArrayList<ClassHours> classHours;
    private ClassTimeListAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_add_class);
        classHours = new ArrayList<ClassHours>();
        //mAdapter = new ClassTimeListAdapter(this, classHours);
        recyclerView = findViewById(R.id.timeRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        //ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerHourItemHelper(0, ItemTouchHelper.LEFT, this);
        //new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        //PrepareList();
    }

    /*private void PrepareList()
    {
        /*this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        //this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        //this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        mAdapter.notifyDataSetChanged();*/
}/*

    /*@Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        /*if (viewHolder instanceof ClassTimeListAdapter.ClassHourViewHolder)
        {
            mAdapter.removeItem(viewHolder.getAdapterPosition());
        }*/

// }

//@Override
//public void onPointerCaptureChanged(boolean hasCapture)
//{

//}
//}
