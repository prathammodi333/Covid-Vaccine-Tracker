//Developed by : Pratham Modi(20CE056)  fetch data from the url and set thee data

package com.example.covidvaccinetracker;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covidvaccinetracker.CountryActivity;
import com.example.covidvaccinetracker.api.ApiAdapter;
import com.example.covidvaccinetracker.api.CountryData;
import com.example.covidvaccinetracker.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private TextView totalConfirm,totalActive,totalRecovered,totalDeath,totalTests;
    private TextView todayConfirm,todayRecovered,todayDeath,date;
    private PieChart pieChart;
    private List<CountryData> list;
    String country = "India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        list = new ArrayList<>();
        if (getIntent().getStringExtra("country") != null)
            country = getIntent().getStringExtra("country");

        init();  //initialization all variable

        TextView cname = findViewById(R.id.cname);
        cname.setText(country);

        cname.setOnClickListener(v ->
        startActivity(new Intent(MainActivity2.this, CountryActivity.class)));
        ApiAdapter.getApiInterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                list.addAll(response.body());

                for (int i=0;i<list.size();i++)
                {
                    if(list.get(i).getCountry().equals(country))
                    {
                        int confirm = Integer.parseInt(list.get(i).getCases());
                        int active = Integer.parseInt(list.get(i).getActive());
                        int recovered = Integer.parseInt(list.get(i).getRecovered());
                        int death = Integer.parseInt(list.get(i).getDeaths());

                        totalActive.setText(NumberFormat.getInstance().format(active));
                        totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                        totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                        totalDeath.setText(NumberFormat.getInstance().format(death));
                        totalTests.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));

                        todayDeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                        todayConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                        todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));

                        setText(list.get(i).getUpdated());

                        pieChart.addPieSlice(new PieModel("Confirm",confirm,getResources().getColor(R.color.yellow)));
                        pieChart.addPieSlice(new PieModel("Active",active,getResources().getColor(R.color.blue_pie)));
                        pieChart.addPieSlice(new PieModel("Recovered",recovered,getResources().getColor(R.color.green_pie)));
                        pieChart.addPieSlice(new PieModel("Death",death,getResources().getColor(R.color.red_pie)));

                        pieChart.startAnimation();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                Toast.makeText(MainActivity2.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setText(String updated) {
        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        long milliSeconds = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        date.setText("Updated at "+format.format(calendar.getTime()));
    }

    private void init()
    {
        totalConfirm = findViewById(R.id.txt_totalConfirm);
        totalActive = findViewById(R.id.txt_totalActive);
        totalRecovered = findViewById(R.id.txt_totalRecovered);
        totalDeath = findViewById(R.id.txt_totalDeath);
        totalTests = findViewById(R.id.txt_totalTests);
        todayConfirm = findViewById(R.id.txt_todayConfirm);
        todayRecovered = findViewById(R.id.txt_todayRecovered);
        todayDeath = findViewById(R.id.txt_todayDeath);

        pieChart = findViewById(R.id.pieChart);
        date = findViewById(R.id.txt_date);

    }
}