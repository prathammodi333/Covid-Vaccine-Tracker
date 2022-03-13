/*
    Developed by : Dev Nakum(20CE059)
    To choose date
*/
package com.example.covidvaccinetracker.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class PickDate extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);    //to show year
        int month = calendar.get(Calendar.MONTH);   //to show month
        int day = calendar.get(Calendar.DAY_OF_MONTH);  //to show date
        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);
    }
}