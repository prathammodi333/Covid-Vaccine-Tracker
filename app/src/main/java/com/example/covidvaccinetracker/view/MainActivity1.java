/*
Developed by : Dev Nakum(20CE059) maharshil limbachiya(20CE048) and Henil mistry (20CE)54) (Sorting)
This page shows the Data of vaccine
*/

package com.example.covidvaccinetracker.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidvaccinetracker.R;
import com.example.covidvaccinetracker.adapter.VaccinationInfoAdapter;
import com.example.covidvaccinetracker.model.VaccineModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity1 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String baseURL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin";      //this is base URL for find vaccination center by pin-code
    private EditText areaPINcode;
    private Button forwardbtn;

    private TextView txt_errorMes;
    ProgressBar holdOnProgress;
    public ArrayList<VaccineModel> vaccination_centers;
    private RecyclerView resultRecyclerView;
    String areaPIN,avlDate;
    LinearLayoutManager manager;
    VaccinationInfoAdapter adapter;
    private ArrayList<String> SortedParameter = new ArrayList<>();
    private ArrayList<VaccineModel> lastList = new ArrayList<>();
    private String parameters[] = {"Age","Time","covaxin","covishield","Fees","Availability"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main1);

        mapViews();
        onClickSetup();
    }

    private void onClickSetup()
    {
        forwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holdOnProgress.setVisibility(View.VISIBLE);
                DialogFragment dp = new PickDate();
                dp.show(getSupportFragmentManager(),"Pick a date");
            }
        });
    }
    public void mapViews() {
        forwardbtn = findViewById(R.id.btn_getResult);
        holdOnProgress = findViewById(R.id.progress_circular);
        areaPINcode = findViewById(R.id.txt_enterPincode);
        resultRecyclerView = findViewById(R.id.recyclerView);
        txt_errorMes = findViewById(R.id.txt_errorMes);
        resultRecyclerView.setHasFixedSize(true);
        vaccination_centers = new ArrayList<VaccineModel>();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar k = Calendar.getInstance();
        k.set(Calendar.YEAR,year);      //set year in dialog fragment
        k.set(Calendar.MONTH,month);        //set month in dialog fragment
        k.set(Calendar.DAY_OF_MONTH,dayOfMonth);        //set date in dialog fragment

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        dateFormat.setTimeZone(k.getTimeZone());
        String d = dateFormat.format(k.getTime());
        setup(d);
    }

    private void setup(String d) {
        avlDate = d;
        fetchDateNow();
    } // Set ups date

    private void fetchDateNow() // for fetching data from API
    {
        vaccination_centers.clear();
        areaPIN = areaPINcode.getText().toString();

        if(areaPIN.length()==0)
        {
            areaPINcode.setError("Please enter pincode");
            return;
        }
        else if(areaPIN.length()!=6)
        {
            areaPINcode.setError("Pincode length must be 6 digits");
            return;
        }
        String url_api = baseURL + "?pincode=" + areaPIN +"&date=" + avlDate;       //full-fill api

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray sessonArray = object.getJSONArray("sessions");
                    for (int i = 0; i < sessonArray.length(); i++) {
                        JSONObject sesObject = sessonArray.getJSONObject(i);
                        VaccineModel vaccineModel = new VaccineModel();
                        vaccineModel.setVaccineCenter(sesObject.getString("name"));
                        vaccineModel.setVaccinationCenterAddress(sesObject.getString("address"));
                        vaccineModel.setVaccinationTimings(sesObject.getString("from"));
                        vaccineModel.setVaccineCenterTime(sesObject.getString("to"));
                        vaccineModel.setVaccineName(sesObject.getString("vaccine"));
                        vaccineModel.setVaccinationCharges(sesObject.getString("fee_type"));
                        vaccineModel.setVaccinationAge(sesObject.getString("min_age_limit"));
                        vaccineModel.setVaccineAvailable(sesObject.getString("available_capacity"));
                        vaccineModel.setDose1(sesObject.getString("available_capacity_dose1"));
                        vaccineModel.setDose2(sesObject.getString("available_capacity_dose2"));
                        vaccination_centers.add(vaccineModel);      //put all data in list
                    }
                    initRecyclerData();     // go to recycler view
                } catch (Exception e) {
                    holdOnProgress.setVisibility(View.INVISIBLE);
//                    Toast.makeText(MainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {        //when getting some kind of error
                holdOnProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(com.example.covidvaccinetracker.view.MainActivity1.this, "Some error occupied", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void initRecyclerData() {
        if(vaccination_centers.size()==0)
        {
            txt_errorMes.setVisibility(View.VISIBLE);
        }
        VaccinationInfoAdapter vaccinationInfoAdapter = new VaccinationInfoAdapter(getApplicationContext(),vaccination_centers);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        resultRecyclerView.setAdapter(vaccinationInfoAdapter);
        holdOnProgress.setVisibility(View.INVISIBLE);
    } // initializes recyclerview

    public void initRecyclerData(ArrayList<VaccineModel> list) {      // for filter
        if(list.size()==0)
        {
            txt_errorMes.setVisibility(View.VISIBLE);
        }
        VaccinationInfoAdapter vaccinationInfoAdapter = new VaccinationInfoAdapter(getApplicationContext(), list);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        resultRecyclerView.setAdapter(vaccinationInfoAdapter);
        holdOnProgress.setVisibility(View.INVISIBLE);
    } // initializes recyclerview data


    public boolean TrySortOn(String parameter)
    {
        boolean Proceed = true;
        for(int i=0;i<this.SortedParameter.size();i++)
        {
            if(this.SortedParameter.get(i) == parameter)
            {
                Proceed = false;
                Toast.makeText(this, "Already Sorted On This Parameter.", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return Proceed;
    } // showing sorted list

    public void Sort(View view)
    {
        //duplicating or creating reference=========================================================
        //creating new list...
        ArrayList<VaccineModel>  SortedList = new ArrayList<>();
        ArrayList<VaccineModel>  AnotherList = new ArrayList<>();
        //copying from old list...
        /*for(int i=0;i<this.centers.size();i++)
        {
            SortedList.add(this.centers.get(i));
        }*/
        SortedList.addAll(this.vaccination_centers);

        int BtnId = view.getId();

        switch(BtnId)
        {
            case R.id.btn_ageBtn: {
                if(TrySortOn("Age"))
                {
                    this.SortedParameter.add("Age");
                    if(this.lastList.size() > 0)
                    {
                        AnotherList.addAll(this.lastList);
                        SortAge(AnotherList);
                    }
                    else
                    {
                        SortAge(SortedList);
                    }
                }
            }
            break;

            case R.id.btn_feesBtn:{
                if(TrySortOn("Fees"))
                {
                    this.SortedParameter.add("Fees");
                    if(this.lastList.size() > 0)
                    {
                        AnotherList.clear();
                        AnotherList.addAll(this.lastList);
                        SortFees(AnotherList);
                    }
                    else
                    {
                        SortFees(SortedList);
                    }
                }
            }
            break;

            case R.id.btn_timeBtn:{

                if(TrySortOn("Time"))
                {
                    this.SortedParameter.add("Time");
                    if(this.lastList.size() > 0)
                    {
                        AnotherList.clear();
                        AnotherList.addAll(this.lastList);
                        SortTime(AnotherList);
                    }
                    else
                    {
                        SortTime(SortedList);
                    }
                }

            }
            break;

            case R.id.btn_CovaxinBtn:{
                if(TrySortOn("covaxin"))
                {
                    this.SortedParameter.add("covaxin");
                    if(this.lastList.size() > 0)
                    {
                        AnotherList.clear();
                        AnotherList.addAll(this.lastList);
                        SortVacc_cova(AnotherList);
                    }
                    else
                    {
                        SortVacc_cova(SortedList);
                    }
                }
            }
            break;

            case R.id.btn_CovishieldBtn:{
                if(TrySortOn("covishield"))
                {
                    this.SortedParameter.add("covishield");
                    if(this.lastList.size() > 0)
                    {
                        AnotherList.clear();
                        AnotherList.addAll(this.lastList);
                        SortVacc_cowi(AnotherList);
                    }
                    else
                    {
                        SortVacc_cowi(SortedList);
                    }
                }
            }
            break;

            case R.id.btn_availableBtn:{
                if(TrySortOn("Availability"))
                {
                    this.SortedParameter.add("Availability");
                    if(this.lastList.size() > 0)
                    {
                        AnotherList.addAll(this.lastList);
                        SortAvailability(AnotherList);
                    }
                    else
                    {
                        SortAvailability(SortedList);
                    }
                }
            }
            break;

            case R.id.btn_nullBtn: {
                //removing last list obj s...
                this.lastList.clear();

                //removing filters...
                this.SortedParameter.clear();

                //giving message accordingly...
                //if(count <= 0)
                //{
                //  Toast.makeText(this, "Performed Recently OR not required!", Toast.LENGTH_SHORT).show();
                //}
                //else
                //{
                Toast.makeText(this, "Cleared All Filters!", Toast.LENGTH_SHORT).show();
                //}

                //resetting whole list...
                ArrayList<VaccineModel> wholeList = new ArrayList<>();
                //rather than loop...use method...
                /*for(int i=0;i<this.centers.size();i++)
                {
                    wholeList.add(this.centers.get(i));
                }*/
                wholeList.addAll(this.vaccination_centers);
                initRecyclerData(wholeList);
            }

        }
    } // whole sorting logic

    private void SortTime(ArrayList<VaccineModel> list)
    {
        //           01234567890123 -> length is 14
        /*if TIME = "09:00:00 to 14:50:00"
         *opening time = 09:00:00
         * closing time = 14:50:00
         * for each time...
         * first entry is 09 / 14 respectively....
         * second entry is 00 / 50 respectively...
         */

//        String TimingDetails1 = null;
//        String TimingDetails2 = null;
        for(int i=0;i<list.size();i++)
        {

            for(int j=0;j<list.size();j++)
            {

                String TimingDetails1 = list.get(i).getVaccinationTimings()+" to "+list.get(i).getVaccineCenterTime();
                String TimingA1 = TimingDetails1.substring(0,2);
                String TimingA2 = TimingDetails1.substring(3,5);
                String TimingA3 = TimingDetails1.substring(6,8);
                String TimingA4 = TimingDetails1.substring(12,14);
                String TimingA5 = TimingDetails1.substring(15,17);
                String TimingA6 = TimingDetails1.substring(18,20);


                String TimingDetails2 = list.get(j).getVaccinationTimings()+" to "+list.get(j).getVaccineCenterTime();
                String TimingB1 = TimingDetails2.substring(0,2);
                String TimingB2 = TimingDetails2.substring(3,5);
                String TimingB3 = TimingDetails2.substring(6,8);
                String TimingB4 = TimingDetails2.substring(12,14);
                String TimingB5 = TimingDetails2.substring(15,17);
                String TimingB6 = TimingDetails2.substring(18,20);

                //checking opening time from here...
                //checking first entry...
                if(Integer.parseInt(TimingA1) < Integer.parseInt(TimingB1))
                {
                    VaccineModel RefObj = list.get(j);
                    list.set(j,list.get(i));
                    list.set(i,RefObj);

                }
                else if(Integer.parseInt(TimingA2) < Integer.parseInt(TimingB2) && Integer.parseInt(TimingA1) == Integer.parseInt(TimingB1))
                {
                    VaccineModel RefObj = list.get(j);
                    list.set(j,list.get(i));
                    list.set(i,RefObj);
                }
                else if(Integer.parseInt(TimingA3) < Integer.parseInt(TimingB3) && Integer.parseInt(TimingA2) == Integer.parseInt(TimingB2) && Integer.parseInt(TimingA1) == Integer.parseInt(TimingB1))
                {
                    VaccineModel RefObj = list.get(j);
                    list.set(j,list.get(i));
                    list.set(i,RefObj);
                }
                else if(Integer.parseInt(TimingA4) < Integer.parseInt(TimingB4) && Integer.parseInt(TimingA3) == Integer.parseInt(TimingB3) && Integer.parseInt(TimingA2) == Integer.parseInt(TimingB2) && Integer.parseInt(TimingA1) == Integer.parseInt(TimingB1))
                {
                    VaccineModel RefObj = list.get(j);
                    list.set(j,list.get(i));
                    list.set(i,RefObj);
                }
                else if(Integer.parseInt(TimingA5) < Integer.parseInt(TimingB5)  && Integer.parseInt(TimingA4) == Integer.parseInt(TimingB4) && Integer.parseInt(TimingA3) == Integer.parseInt(TimingB3) && Integer.parseInt(TimingA2) == Integer.parseInt(TimingB2) && Integer.parseInt(TimingA1) == Integer.parseInt(TimingB1))
                {
                    VaccineModel RefObj = list.get(j);
                    list.set(j,list.get(i));
                    list.set(i,RefObj);
                }
                else if(Integer.parseInt(TimingA6) < Integer.parseInt(TimingB6) && Integer.parseInt(TimingA5) == Integer.parseInt(TimingB5)  && Integer.parseInt(TimingA4) == Integer.parseInt(TimingB4) && Integer.parseInt(TimingA3) == Integer.parseInt(TimingB3) && Integer.parseInt(TimingA2) == Integer.parseInt(TimingB2) && Integer.parseInt(TimingA1) == Integer.parseInt(TimingB1))
                {
                    VaccineModel RefObj = list.get(j);
                    list.set(j,list.get(i));
                    list.set(i,RefObj);
                }
                else
                {
                    //do nothing...
                }
            }
        }

        Toast.makeText(this, "Time Sorted Successfully!", Toast.LENGTH_SHORT).show();
        this.UpdateLastList(list);
        this.initRecyclerData(this.lastList);
    } //sort by time

    private void Update(ArrayList<Integer> NeedToRemove,ArrayList<VaccineModel> list)
    {
        //removing logic vise...
        for(int i=0;i<NeedToRemove.size();i++)
        {
            list.remove(NeedToRemove.get(i).intValue());
            for(int j=i;j<NeedToRemove.size();j++)
            {
                NeedToRemove.set(j,(NeedToRemove.get(j).intValue())-1);
            }
        }

        this.UpdateLastList(list);
        this.initRecyclerData(this.lastList);
    } // updating list according to parameters

    private void SortVacc_cova(ArrayList<VaccineModel> list)
    {
        ArrayList<Integer> NeedToRemove = new ArrayList<>();

        String VaccDetails = null;
        for(int i=0;i<list.size();i++)
        {
            VaccDetails = list.get(i).getVaccineName().toLowerCase();
            if(VaccDetails.contains("covaxin") || VaccDetails.contains("both"))
            {
                //do nothing...
            }
            else
            {
                NeedToRemove.add(i);
            }
        }

        this.Update(NeedToRemove,list);
        Toast.makeText(this, "Co-Vaccine Parameter Sorted!", Toast.LENGTH_SHORT).show();
    } //sort by covaccine

    private void SortVacc_cowi(ArrayList<VaccineModel> list)
    {
        ArrayList<Integer> NeedToRemove = new ArrayList<>();

        String VaccDetails = null;
        for(int i=0;i<list.size();i++)
        {
            VaccDetails = list.get(i).getVaccineName().toLowerCase();
            if(VaccDetails.contains("covishield") || VaccDetails.contains("both"))
            {
                //do nothing...
            }
            else
            {
                NeedToRemove.add(i);
            }
        }

        this.Update(NeedToRemove,list);
        Toast.makeText(this, "Cowi-Shield Parameter Sorted!", Toast.LENGTH_SHORT).show();
    } // sort by cowishield

    private void SortAvailability(ArrayList<VaccineModel> list)
    {
        ArrayList<Integer> NeedToRemove = new ArrayList<>();

        String AvailabilityDetails = null;
        for(int i=0;i<list.size();i++)
        {
            AvailabilityDetails = list.get(i).getVaccineAvailable().toLowerCase();
            if(Integer.parseInt(AvailabilityDetails)>0)
            {
                //do nothing
            }
            else
            {
                NeedToRemove.add(i);
            }
            //Toast.makeText(this, AvailabilityDetails+" "+i, Toast.LENGTH_SHORT).show();
        }

        this.Update(NeedToRemove,list);

        Toast.makeText(this, "Availability Sorted Successfully!", Toast.LENGTH_SHORT).show();
    } // sort by availibility

    private void SortFees(ArrayList<VaccineModel> list)
    {
        ArrayList<Integer> NeedToRemove = new ArrayList<>();

        String FeeDetails = null;
        for(int i=0;i<list.size();i++)
        {
            FeeDetails = list.get(i).getVaccinationCharges().toLowerCase();
            if(FeeDetails.contains("free"))
            {
                //do nothing...
            }
            else
            {
                NeedToRemove.add(i);
            }
        }

        this.Update(NeedToRemove,list);
        //show message...
        Toast.makeText(this, "Fees Sorted Successfully!", Toast.LENGTH_SHORT).show();
    } // sort by fees

    private void SortAge(ArrayList<VaccineModel> list)
    {
        ArrayList<Integer> NeedToRemove = new ArrayList<>();

        String Age = null;
        String FormattedAge = null;
        for(int i=0;i<list.size();i++)
        {
            Age = list.get(i).getVaccinationAge();
            FormattedAge = Age.substring(0,Age.length()-1);
            if(Integer.parseInt(FormattedAge) < 40)
            {

            }
            else
            {
                NeedToRemove.add(i);
            }
        }

        this.Update(NeedToRemove,list);

        Toast.makeText(this, "Age Sorted Successfully!", Toast.LENGTH_SHORT).show();
    } // sort by age

    private void UpdateLastList(ArrayList<VaccineModel> list)
    {
        //removing old obj....
        //rather than loop...use method
        /*for(int i=0;i<this.lastList.size();i++)
        {
            this.lastList.remove(i);
        }*/
        this.lastList.clear();

        //updating list...
        //rather than loop use method...
        /*for(int i=0;i<list.size();i++)
        {
            this.lastList.add(list.get(i));
        }*/
        this.lastList.addAll(list);
    }

} // for clearing all filters
