package com.moshesteinvortzel.assaftayouri.ihw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;

public class HoursclassfragActivity extends AppCompatActivity implements RecyclerHourItemHelper.RecyclerItemTouchHelperListener
{
    private ArrayList<ClassHours> classHours;
    private ClassHourListAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hours_class);
        classHours = new ArrayList<ClassHours>();
        mAdapter = new ClassHourListAdapter(this, classHours);
        recyclerView = findViewById(R.id.hoursRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerHourItemHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        PrepareList();
    }

    private void PrepareList()
    {
        this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        //this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        //this.classHours.add(new ClassHours("abcd","18","19","Friday"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        if (viewHolder instanceof ClassHourListAdapter.ClassHourViewHolder)
        {
            mAdapter.removeItem(viewHolder.getAdapterPosition());
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }
}
