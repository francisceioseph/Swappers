package br.edu.ifce.swappers.swappers.fragments.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Joamila on 15/06/2015.
 * Reused class: http://androidtrainningcenter.blogspot.com.br/2012/10/creating-datepicker-using.html
 */
public class DatePickerFragment extends DialogFragment{
    private DatePickerDialog.OnDateSetListener ondateSet;
    private int year;
    private int month;
    private int day;

    public DatePickerFragment(){

    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }
}

