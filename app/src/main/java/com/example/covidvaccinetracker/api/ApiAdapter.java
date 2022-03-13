//Developed by : Pratham Modi(20CE056)  for api adapter
package com.example.covidvaccinetracker.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiAdapter {
    private static Retrofit retrofit = null;

    public static ApiInterface getApiInterface() {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
