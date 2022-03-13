/*
Developed by : Harshil padasala(20CE064) and pratham modi(20CE056)
To set data from API(recycler view)
*/

package com.example.covidvaccinetracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidvaccinetracker.R;
import com.example.covidvaccinetracker.model.VaccineModel;

import java.util.List;

public class VaccinationInfoAdapter extends RecyclerView.Adapter<VaccinationInfoAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<VaccineModel> list_vaccine_center;
    public VaccinationInfoAdapter(Context mcontext, List<VaccineModel> list_vaccine_center) {
        this.layoutInflater = LayoutInflater.from(mcontext);
        this.list_vaccine_center = list_vaccine_center;
    }

    public VaccinationInfoAdapter(List<VaccineModel> list)
    {
        this.list_vaccine_center = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate( R.layout.vaccination_info_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccinationInfoAdapter.ViewHolder holder, int position) {
        holder.vaccinationCenter.setText(list_vaccine_center.get(position).getVaccineCenter());
        holder.vaccinationCenterAddr.setText(list_vaccine_center.get(position).getVaccinationCenterAddress());
        holder.vaccinationTiming.setText(list_vaccine_center.get(position).getVaccinationTimings() + " to " + list_vaccine_center.get(position).getVaccineCenterTime());
        holder.vaccineName.setText(list_vaccine_center.get(position).getVaccineName());
        holder.vaccinationAvailable.setText(list_vaccine_center.get(position).getVaccineAvailable());
        holder.vaccineCharges.setText(list_vaccine_center.get(position).getVaccinationCharges());
        holder.vaccinationAge.setText(list_vaccine_center.get(position).getVaccinationAge());
        holder.dose1.setText("Dose 1 : "+list_vaccine_center.get(position).getDose1());
        holder.dose2.setText("Dose 2 : "+list_vaccine_center.get(position).getDose2());
    }

    @Override
    public int getItemCount() {
        return list_vaccine_center.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView vaccinationCenter;
        TextView vaccinationCenterAddr;
        TextView vaccinationTiming;
        TextView vaccineName;
        TextView vaccineCharges;
        TextView vaccinationAge;
        TextView vaccinationAvailable;
        TextView dose1;
        TextView dose2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            vaccinationAge = itemView.findViewById(R.id.txtv_vaccinationAge);
            vaccinationAvailable = itemView.findViewById(R.id.txtv_isAvailable);
            vaccineCharges = itemView.findViewById(R.id.txtv_vaccineCharges);
            vaccineName = itemView.findViewById(R.id.txtv_vaccineName);
            vaccinationTiming = itemView.findViewById(R.id.txtv_vaccineTimings);
            vaccinationCenter = itemView.findViewById(R.id.txtv_vaccineCenter);
            vaccinationCenterAddr = itemView.findViewById(R.id.txtv_vaccineLocation);
            dose1 = itemView.findViewById(R.id.txtv_firstDose);
            dose2 = itemView.findViewById(R.id.txtv_secondDose);
        }
    }
}
