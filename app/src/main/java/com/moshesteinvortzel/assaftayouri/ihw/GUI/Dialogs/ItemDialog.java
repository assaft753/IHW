package com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.AddExamDialogListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.security.PrivateKey;

/**
 * Created by assaftayouri on 07/03/2018.
 */

public class ItemDialog extends DialogFragment
{
    private String[] strs;
    private NumberPicker picker;
    private TextView titleText;
    private View view;
    AddExamDialogListener OKbtn;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        System.out.println("dialog created");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_picker, null);
        this.view = view;
        picker = view.findViewById(R.id.itemPicker);
        titleText = view.findViewById(R.id.pickerText);
        SetPickerOpt();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        OKbtn.OkValue(picker.getValue());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public void setStrs(String[] strings, AddExamDialogListener OKbtn)
    {
        this.strs = strings;
        this.OKbtn = OKbtn;
    }

    private void SetPickerOpt()
    {
        picker.setMinValue(0);
        picker.setMaxValue(strs.length - 1);
        picker.setDisplayedValues(strs);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        picker.setWrapSelectorWheel(true);
    }
}
